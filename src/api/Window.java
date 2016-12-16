package api;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Window extends JFrame {
    static JTextField jtfUneditableTextArticle;
    public static JTextPane infoText;
    public static JFileChooser fileopen = new JFileChooser();

    public Window() throws IOException, URISyntaxException {

        super("Парсер книг с Labirint.ru");
        final PhantomJSDriver driver = new PhantomJSDriver(Utils.getCapabilities());
        JButton jButtonArticle = new JButton("Выбрать файл");
        this.setBackground(Color.CYAN);
        setContentPane(new GUITools().getPanel());
        setLayout(new FlowLayout());

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 3 - getSize().width / 2, dim.height / 3 - getSize().height / 2);
//
        jtfUneditableTextArticle = new JTextField("Выберите файл  и парсинг начнется автоматически");
        jtfUneditableTextArticle.setEditable(false);
        add(jtfUneditableTextArticle);
        add(jButtonArticle);
        //
//
        setSize(500, 550);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Parse.doParse(driver);
                            driver.quit();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        } catch (InvalidFormatException e1) {
                            e1.printStackTrace();
                        }
                    }
                }.start();

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        jButtonArticle.addMouseListener(mouseListener);
    }
}
