package api;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by Кирилл on 14.12.2015.
 */
public class GUITools {
    static final Color MIDNIGHT_BLUE = new Color(44, 62, 80);
    static final Color WET_ASPHALT = new Color(52, 73, 94);
    static final Font TREBUCHET_MS_MEDIUM = new Font("Trebuchet MS", Font.PLAIN, 14);
    static final Font TREBUCHET_MS_SMALL= new Font("Trebuchet MS", Font.PLAIN, 12);
    private static Component getInfoTextScrollPane() {
        Window.infoText = new JTextPane();
        Window.infoText.setAutoscrolls(false);
        Window.infoText.setEditable(false);
        Window.infoText.setBackground(WET_ASPHALT.brighter());
        Window.infoText.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(236, 240, 241)), "Консоль вывода", TitledBorder.LEFT,
                TitledBorder.LEFT, TREBUCHET_MS_SMALL, Color.WHITE));
        Window.infoText.setMargin(new Insets(10, 10, 10, 10));

        Window.infoText.setFont(TREBUCHET_MS_MEDIUM);

        (new DefaultContextMenu()).add(Window.infoText);

        JScrollPane infoTextScrollPane = new JScrollPane(Window.infoText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JTextAreaOutputStream out = new JTextAreaOutputStream(Window.infoText);
        System.setOut(new PrintStream(out));
        infoTextScrollPane.setPreferredSize(new Dimension(400, 450));
        return infoTextScrollPane;
    }

    public  JPanel getPanel() throws IOException {
		/* Main panel */
        JPanel panel = new JPanel();
		/* Left sub panel */
        JPanel infoTextPanel = new JPanel();
		/* Right sub panel */
		/* Wrap for Right sub panel */
        JPanel wrapPanel = new JPanel();

		/* Styles */
        panel.setLayout(new BorderLayout());
        panel.setBackground(MIDNIGHT_BLUE);
        infoTextPanel.setBackground(WET_ASPHALT.brighter());
        infoTextPanel.add(getInfoTextScrollPane());
        wrapPanel.setLayout(new GridLayout(3, 1));
        wrapPanel.setOpaque(false);
        panel.add(infoTextPanel, BorderLayout.WEST);

        return panel;
    }
}
