package zad2.exception;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.function.Function;

public class ErrorDialogProvider {

    public static <T> Function<Throwable, T> errorFunction(String info) {
        return e -> {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "%s. Cause: %s".formatted(info, e.getMessage()),
                    "Error",
                    JOptionPane.ERROR_MESSAGE)
            );
            return null;
        };
    }
}
