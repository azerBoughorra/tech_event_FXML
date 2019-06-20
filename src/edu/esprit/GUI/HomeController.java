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

/**
 * FXML Controller class
 *
 * @author yjaballi
 */
public class HomeController implements Initializable {

    @FXML
    private AnchorPane homeContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Button  b = new Button(UserManager.getUser().getRole().getDescription());
        b.setOnAction(e->{
        Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("form.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
                   homeContainer.getScene().setRoot(root);
        });
        
        
        homeContainer.getChildren().add(b);
        // TODO
    }    
    
}
