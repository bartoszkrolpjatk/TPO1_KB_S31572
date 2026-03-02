package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        var dir = Path.of(dirName);
        try (var resultFileChannel = FileChannel.open(Path.of(resultFileName), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            var visitor = new MyFileVisitor(resultFileChannel);
            Files.walkFileTree(dir, visitor);
        }  catch (IOException e) {
            System.err.printf("Exception: %s", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static class MyFileVisitor extends SimpleFileVisitor<Path> {

        private final ByteBuffer buffer = ByteBuffer.allocate(1024);
        private final CharsetDecoder decoder = Charset.forName("Cp1250").newDecoder();
        private final CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        private final FileChannel out;

        private MyFileVisitor(FileChannel out) {
            this.out = out;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try (var fileChannel = FileChannel.open(file, StandardOpenOption.READ)) {
                while (fileChannel.read(buffer) != -1) {
                    buffer.flip();
                    var charBuffer = encoder.encode(decoder.decode(buffer));
                    out.write(charBuffer);
                    buffer.clear();
                }
            }
            return super.visitFile(file, attrs);
        }
    }
}
