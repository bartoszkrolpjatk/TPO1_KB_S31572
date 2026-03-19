package zad2.gui;

import zad2.gui.side.SidePanel;
import zad2.gui.wiki.WikipediaPanel;

import javax.swing.JPanel;
import java.awt.BorderLayout;

class MainPanel extends JPanel {

    MainPanel(SidePanel sidePanel) {
        setLayout(new BorderLayout());
        add(new WikipediaPanel(), BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
    }
}
