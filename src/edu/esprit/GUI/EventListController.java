/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.GUI;

import edu.esprit.models.Event;
import edu.esprit.utils.ServiceManager;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author azer
 */
public class EventListController implements Initializable {

    @FXML
    private TableView<Event> personTable;
    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, String> categoryColumn;
    @FXML
    private TableColumn<Event, String> etpColumn;
    @FXML
    private TableColumn<Event, Date> dateColumn;
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
        personTable.setItems(eventList);
        this.titleColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
    }

}
