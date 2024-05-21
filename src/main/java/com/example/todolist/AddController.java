package com.example.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddController {

    @FXML
    private DatePicker deadLineId;

    @FXML
    private TextArea detailsId;

    @FXML
    private TextField nameId;

    @FXML
    private Label welcomeText;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement;

    @FXML
    void reset() {
        nameId.setText(null);
        deadLineId.setValue(null);
        detailsId.setText(null);
    }

    @FXML
    void save(MouseEvent event) {
        connection = Dbconnect.getConnection();
        String name = nameId.getText();
        String deadLine = String.valueOf(deadLineId.getValue());
        String details = detailsId.getText();
        if (name.isEmpty() || deadLine.isEmpty() || details.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            reset();
        }
    }

    @FXML
    private void getQuery() {
        query = "INSERT INTO tasks(task, deadline, description) VALUES (?,?,?)";

    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameId.getText());
            preparedStatement.setString(2, String.valueOf(deadLineId.getValue()));
            preparedStatement.setString(3, detailsId.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}