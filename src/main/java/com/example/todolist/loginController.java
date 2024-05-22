package com.example.todolist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class loginController {
    @FXML
    private Label LoginMessageLable;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button cancelButton;

    public void setCancelButtonAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button loginButton;

    @FXML
    private void setLoginButtonOnAction(ActionEvent event) throws Exception {

        try {
            Connection connection = Dbconnect.getConnection();

            String sql = "SELECT * FROM login WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usernameTextField.getText());
            statement.setString(2, passwordTextField.getText());

            ResultSet resultSet = statement.executeQuery();
            if (usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank()) {
                LoginMessageLable.setText("Please type the username and password");
            } else if (resultSet.next()) {
                LoginMessageLable.setText("Login Success");
                utility.changeToScene(getClass(), event, "tasksTable.fxml");
            } else {
                // Failed login: show an error message
                LoginMessageLable.setText("Login Failed");
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void openSignUpPage(ActionEvent event) {
        // Load the SignUp.fxml file
        System.out.println("jdasfogvjdfv;gjfdao;pjgsv");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
