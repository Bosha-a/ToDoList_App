package com.example.todolist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class registerController {

    @FXML
    private Label LoginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    public void setCancelButtonAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSignUpAction(ActionEvent event) {
        try {
            Connection connection = Dbconnect.getConnection();

            String insertSql = "INSERT INTO login (username, password) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setString(1, usernameTextField.getText());
            insertStatement.setString(2, passwordTextField.getText());
            int rowsAffected = insertStatement.executeUpdate();

            utility.changeToScene(getClass(), event, "tasksTable.fxml");
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
