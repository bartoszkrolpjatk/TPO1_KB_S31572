package zad2.gui;

import javax.swing.JFrame;

class MainFrame extends JFrame {

    MainFrame(MainPanel mainPanel) {
        add(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
