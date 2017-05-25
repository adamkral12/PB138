/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecallmanager;

import core.Call;
import core.Country;
import core.Direction;
import core.Message;
import core.Region;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import managers.CountryManager;
import managers.RegionManager;
import managers.CallManager;
import managers.MessageManager;

/**
 *
 * @author xkral3
 */
public class PhoneCallManager extends JApplet {

    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }

                JFrame frame = new JFrame("JavaFX 2 in Swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JApplet applet = new PhoneCallManager();
                applet.init();

                frame.setContentPane(applet.getContentPane());

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                applet.start();
            }
        });
    }

    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                createScene();
            }
        });
    }

    private void createScene() {
        temporaryTesting();

        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        fxContainer.setScene(new Scene(root));
    }

    private void temporaryTesting() {
        System.out.println("---TEST START--");
        System.out.println("Parsing regions:");

        RegionManager rm = new RegionManager();
        RegionManager.reloadRegions();

        for (Region region : RegionManager.getAll()) {
            System.out.println(region);
        }
        System.out.println("id = NA");
        System.out.println(RegionManager.getById("NA"));
        System.out.println("Home region:");
        System.out.println(RegionManager.getHomeRegion());

        System.out.println("Parsing countries");
        CountryManager cm = new CountryManager();
        CountryManager.reloadCountries();

        for (Country country : CountryManager.getAll()) {
            System.out.println(country);
        }
        System.out.println("region AS");

        for (Country country : CountryManager.getByRegion(RegionManager.getById("AS"))) {
            System.out.println(country);
        }
        System.out.println("prefix 421");

        System.out.println(CountryManager.getByPrefix("+421").get(0));

        System.out.println("Parsing calls:");
        CallManager cam = new CallManager();
        CallManager.reloadCalls();

        for (Call call : CallManager.getAll()) {
            System.out.println(call);
        }
        System.out.println("id = 1");
        System.out.println(CallManager.getById(1));

        System.out.println("IN");
        for (Call call : CallManager.getByDirection(Direction.IN)) {
            System.out.println(call);
        }

        System.out.println("callee 905678678");
        for (Call call : CallManager.getByCallee("905678678")) {
            System.out.println(call);
        }

        System.out.println("destination MEX");
        for (Call call : CallManager.getByDestination(CountryManager.getById("MEX"))) {
            System.out.println(call);
        }

        System.out.println("Parsing messages:");
        MessageManager mm = new MessageManager();
        MessageManager.reloadMessages();

        for (Message message : MessageManager.getAll()) {
            System.out.println(message);
        }
        System.out.println("id = 2");
        System.out.println(MessageManager.getById(2));

        System.out.println("OUT");
        for (Message message : MessageManager.getByDirection(Direction.OUT)) {
            System.out.println(message);
        }

        System.out.println("callee 905678678");
        for (Message message : MessageManager.getByCallee("905678678")) {
            System.out.println(message);
        }

        System.out.println("destination MEX");
        for (Message message : MessageManager.getByDestination(CountryManager.getById("MEX"))) {
            System.out.println(message);
        }

        System.out.println("---TEST END---");
    }
}
