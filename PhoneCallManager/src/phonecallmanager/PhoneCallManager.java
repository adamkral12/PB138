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
        
        for(Region region: rm.getAll()) {
            System.out.println(region);
        }
        System.out.println("id = NA");
        System.out.println(rm.getById("NA"));
        System.out.println("Home region:");
        System.out.println(rm.getHomeRegion());
        
        System.out.println("Parsing countries");
        CountryManager cm = new CountryManager();
        CountryManager.reloadCountries();
        
        for(Country country : cm.getAll()) {
            System.out.println(country);
        }
        System.out.println("region AS");
        
        for(Country country : cm.getByRegion(rm.getById("AS"))) {
            System.out.println(country);
        }
        System.out.println("prefix 421");
        
        System.out.println(cm.getByPrefix("+421").get(0));
        
        System.out.println("Parsing calls:");
        CallManager cam = new CallManager();
        CallManager.reloadCalls();
        
        for(Call call : cam.getAll()) {
            System.out.println(call);
        }
        System.out.println("id = 1");
        System.out.println(cam.getById(1));
        
        System.out.println("IN");
        for(Call call : cam.getByDirection(Direction.IN)) {
            System.out.println(call);
        }
        
        System.out.println("callee 905678678");
        for(Call call : cam.getByCallee("905678678")) {
            System.out.println(call);
        }
        
        System.out.println("destination MEX");
        for(Call call : cam.getByDestination(cm.getById("MEX"))) {
            System.out.println(call);
        }
        
        System.out.println("Parsing messages:");
        MessageManager mm = new MessageManager();
        MessageManager.reloadMessages();
        
        for(Message message : mm.getAll()) {
            System.out.println(message);
        }
        System.out.println("id = 2");
        System.out.println(mm.getById(2));
        
        System.out.println("OUT");
        for(Message message : mm.getByDirection(Direction.OUT)) {
            System.out.println(message);
        }
        
        System.out.println("callee 905678678");
        for(Message message : mm.getByCallee("905678678")) {
            System.out.println(message);
        }
        
        System.out.println("destination MEX");
        for(Message message : mm.getByDestination(cm.getById("MEX"))) {
            System.out.println(message);
        }
        
        System.out.println("---TEST END---");
    }
}
