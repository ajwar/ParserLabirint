package api;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class JTextAreaOutputStream extends OutputStream {
    private final JTextPane destination;
    Style RED;
    Style GREEN;
    Style NORMAL;

    public JTextAreaOutputStream(JTextPane infoText) {
        if (infoText == null)
            throw new IllegalArgumentException("Destination is null");

        this.destination = infoText;
        RED = destination.addStyle("red", null);
        GREEN = destination.addStyle("green", null);
        NORMAL = destination.addStyle("normal", null);

        StyleConstants.setForeground(RED, Color.red);
        StyleConstants.setForeground(GREEN, Color.green);
        StyleConstants.setForeground(NORMAL, Color.white);

    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        final String text = new String(buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                if (Window.infoText.getText().length() > 2000) {
                    Window.infoText.setText("");
                }
                appendString(text);

            }

            public void appendString(String str) {


                StyledDocument document = (StyledDocument) destination.getDocument();
                try {
                    if (str.indexOf("[green]") != -1) {
                        document.insertString(document.getLength(), str.replaceAll("\\[green\\]", ""), GREEN);
                    } else if (str.indexOf("[red]") != -1) {
                        document.insertString(document.getLength(), str.replaceAll("\\[red\\]", ""), RED);
                    } else {
                        document.insertString(document.getLength(), str, NORMAL);
                    }

                } catch (BadLocationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

}