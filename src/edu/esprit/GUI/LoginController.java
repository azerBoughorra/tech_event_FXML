/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.GUI;

import edu.esprit.models.User;
import edu.esprit.services.implementation.UserService;
import edu.esprit.utils.ServiceManager;
import edu.esprit.utils.UserManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yjaballi
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
 @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField Login;
    @FXML
    private TextField Pasword;
    TextField l = new TextField("test");
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        
        UserService us = new UserService();
               try {  
                   User u = ServiceManager.getInstance().getUserService().login(Login.getText(), Pasword.getText());
                   System.out.println(ServiceManager.getInstance().getSessionService().findAll());
                   if(u !=  null)
                   {
                       UserManager.setUser(u);
                   Parent root= FXMLLoader.load(getClass().getResource("Home.fxml"));
                   Login.getScene().setRoot(root);
                   }else{
                       System.out.println("utilisateur introuvable");
                   }
                   
               }catch (IOException ex) {
                   ex.printStackTrace();
                   System.out.println("failed");
                   //Logger.getLogger(FXMLajoutController.class.getName()).log(Level.SEVERE, null, ex);
               }
        //Logger.getLogger(FXMLajoutController.class.getName()).log(Level.SEVERE, null, ex);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //container.getChildren().add(l);
        // TODO
    }   
    
}
