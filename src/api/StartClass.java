package api;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class StartClass extends JFrame{
    private static Logger log = Logger.getLogger(StartClass.class.getName());


    public static void main(String[] args) throws IOException, URISyntaxException {
        new Window();
    }
}
