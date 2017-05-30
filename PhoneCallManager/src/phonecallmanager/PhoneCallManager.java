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
import core.SortParameter;
import gui.PhoneCallManagerFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import managers.LoadDataManager;
import managers.MessageManager;
import managers.PriceManager;
import managers.SortManager;

/**
 * The base class of the application
 * @author xkral3, xvalko, xmikova
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

                PhoneCallManagerFrame appFrame = new PhoneCallManagerFrame();
                appFrame.setVisible(true);
            }
        });
    }

    @Override
    public void init() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                createScene();
            }
        });
    }

    private void createScene() {
        Testing.testing();
    }

    
    
   
}
