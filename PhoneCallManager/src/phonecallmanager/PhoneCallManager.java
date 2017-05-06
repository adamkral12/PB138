/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecallmanager;

import core.Country;
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
            System.out.println(region.getId() + " " +
                    region.getName()+ " " + region.getPriceCallIncoming()+ " " +
                    region.getPriceCallOutcoming()+ " " +
                    region.getPriceMessageIncoming()+ " " +
                    region.getPriceMessageOutcoming() + " ");
        }
        
        System.out.println(rm.getById("NA").getName());
        
        System.out.println(rm.getHomeRegion().getName());
        
        System.out.println("Parsing countries");
        CountryManager cm = new CountryManager();
        CountryManager.reloadCountries();
        
        for(Country country : cm.getAll()) {
            System.out.println(country.getId() + " " +
                    country.getName() + " " + country.getPrefix() + " " +
                    country.getRegion().getId());
        }
        System.out.println("");
        
        for(Country country : cm.getByRegion(rm.getById("AS"))) {
            System.out.println(country.getId() + " " +
                    country.getName() + " " + country.getPrefix() + " " +
                    country.getRegion().getId());
        }
        System.out.println("");
        
        System.out.println(cm.getByPrefix("+421").get(0).getName());
        
        
        System.out.println("---TEST END---");
    }
}
