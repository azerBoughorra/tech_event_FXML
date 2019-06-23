/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.GUI;

import edu.esprit.utils.UserManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author yjaballi
 */
public class HomeController implements Initializable {

    @FXML
    private AnchorPane homeContainer;
    @FXML
    private VBox menu;
    @FXML
    private AnchorPane screen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        menu.getStyleClass().add("menu");
        Button bProfile = new Button("Profile");
        bProfile.getStyleClass().add("menu-button");
        bProfile.setMaxWidth(Double.MAX_VALUE);
        bProfile.setMinHeight(50);

        menu.getChildren().add(bProfile);

        bProfile.setOnAction(e -> {
            screen.getChildren().clear();
            AnchorPane content = null;
            try {
                content = FXMLLoader.load(getClass().getResource("UserDetails.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            screen.getChildren().add(content);
        });

        Button bEventList = new Button("Liste des evennements");
        bEventList.getStyleClass().add("menu-button");
        bEventList.setMaxWidth(Double.MAX_VALUE);
        bEventList.setMinHeight(50);

        menu.getChildren().add(bEventList);

        bEventList.setOnAction(e -> {
            screen.getChildren().clear();
            AnchorPane content = null;
            try {
                content = FXMLLoader.load(getClass().getResource("EventList.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            screen.getChildren().add(content);
        });

        Button bPlanning = new Button("Ma calendrier");
        bPlanning.getStyleClass().add("menu-button");
        bPlanning.setMaxWidth(Double.MAX_VALUE);
        bPlanning.setMinHeight(50);

        menu.getChildren().add(bPlanning);
        bPlanning.setOnAction(e -> {
            screen.getChildren().clear();
            AnchorPane content = null;
            try {
                content = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            screen.getChildren().add(content);
        });

        if (UserManager.getUser().getRole().getId() == 1) {
            Button bReclammation = new Button("Reclamations");
            bReclammation.getStyleClass().add("menu-button");
            bReclammation.setMaxWidth(Double.MAX_VALUE);
            bReclammation.setMinHeight(50);
            menu.getChildren().add(bReclammation);
            bReclammation.setOnAction(e -> {
                screen.getChildren().clear();
                AnchorPane content = null;
                try {
                    content = FXMLLoader.load(getClass().getResource("Reports.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                screen.getChildren().add(content);
            });

        } else if (UserManager.getUser().getRole().getId() == 3) {
            Button bAddEvent = new Button("Creer evennement");
            bAddEvent.getStyleClass().add("menu-button");
            bAddEvent.setMaxWidth(Double.MAX_VALUE);
            bAddEvent.setMinHeight(50);
            menu.getChildren().add(bAddEvent);
            bAddEvent.setOnAction(e -> {
                screen.getChildren().clear();
                AnchorPane content = null;
                try {
                    content = FXMLLoader.load(getClass().getResource("AddEvent.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                screen.getChildren().add(content);
            });
        }

        AnchorPane content = null;
        try {
            content = FXMLLoader.load(getClass().getResource("EventList.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        screen.getChildren().add(content);

        // TODO
    }

}
