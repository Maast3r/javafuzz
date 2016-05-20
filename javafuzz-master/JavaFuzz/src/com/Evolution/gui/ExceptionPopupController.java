package com.Evolution.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the exception display popup
 * Created by goistjt on 5/11/2016.
 */
class ExceptionPopupController implements Initializable {

    private Throwable exception;

    @FXML
    private Label exceptionLabel;

    /**
     * Set the current player hand after initializing the controller
     *
     * @param e the exception being raised
     */
    ExceptionPopupController(Throwable e) {
        this.exception = e;
    }

    /**
     * Initialize the attack controller and popup
     *
     * @param fxmlFileLocation fxml file this controller is for (attack_popup.fxml)
     * @param resources        resources available in the package
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert this.exceptionLabel != null : "fx:id=\"infoLabel\" was not injected: check your FXML file 'exception_popup" +
                ".fxml'.";
        this.exceptionLabel.setText(exception.getMessage());
    }

}