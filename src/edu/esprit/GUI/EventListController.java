/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.GUI;

import edu.esprit.models.Event;
import edu.esprit.utils.ServiceManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author azer
 */
public class EventListController implements Initializable {

    @FXML
    private TableView<?> personTable;
    @FXML
    private TableColumn<?, ?> titleColumn;
    @FXML
    private TableColumn<?, ?> categoryColumn;
    @FXML
    private TableColumn<?, ?> etpColumn;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private CheckBox myEvents;
    @FXML
    private TextField filterField;

    private ObservableList<Event> eventList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        eventList.addAll(ServiceManager.getInstance().getEventService().findAll());
        System.out.println(eventList);
    }

}
