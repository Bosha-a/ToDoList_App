package com.example.todolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class taskTableController implements Initializable {

    @FXML
    private TableColumn<MyTask, String> taskCol;

    @FXML
    private TableColumn<MyTask, String> deadlinCol;

    @FXML
    private TableColumn<MyTask, String> descriptionCol;

    @FXML
    private Label tasksNumber;

    @FXML
    private TableView<MyTask> taskTable;

    ObservableList<MyTask> TaskList = FXCollections.observableArrayList();

    String query = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    MyTask myTask = null;

    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
    }

    @FXML
    void addTask(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AddTask.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(taskTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void refresh(MouseEvent event) {
        loadDate();
    }

    @FXML
    void deleteSelectedItem(MouseEvent event) {
        myTask = taskTable.getSelectionModel().getSelectedItem();
        if (myTask != null) {
            int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
            // Delete from the database
            deleteTaskFromDatabase(myTask);
            // Remove from the table view
            taskTable.getItems().remove(selectedIndex);
        } else {
            // No row selected, show an alert or handle accordingly
            // For example, show a message dialog
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
        }
    }

    @FXML
    void showDescription(MouseEvent event) {
        myTask = taskTable.getSelectionModel().getSelectedItem();
        if (myTask != null) {
            showTaskFromDatabase(myTask);
        } else {
            // No row selected, show an alert or handle accordingly
            // For example, show a message dialog
            JOptionPane.showMessageDialog(null, "Please select a row .");
        }
    }

    private void loadDate() {
        connection = Dbconnect.getConnection();

        setData();
        setTasksNum();
        taskCol.setCellValueFactory(new PropertyValueFactory<>("task"));
        deadlinCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("details"));
    }

    private void setData() {
        try {
            TaskList.clear();
            query = "SELECT `task`, `deadline` ,`description` FROM `tasks`";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                TaskList.add(new MyTask(
                        resultSet.getString("task"),
                        resultSet.getDate("deadline"),
                        resultSet.getString("description")));
            }
            taskTable.setItems(TaskList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteTaskFromDatabase(MyTask task) {
        try {
            query = "DELETE FROM tasks WHERE task = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, task.getTask());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showTaskFromDatabase(MyTask task) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Description.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(taskTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        descriptionController descriptionController = loader.getController();
        descriptionController.setTextField(task.getTask(), task.getDeadline().toString(), task.getDetails());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    private void setTasksNum() {
        connection = Dbconnect.getConnection();
        int count = 0;
        query = "SELECT COUNT(ID) FROM tasks";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("COUNT(ID)");
            }
            tasksNumber.setText(String.valueOf(count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
