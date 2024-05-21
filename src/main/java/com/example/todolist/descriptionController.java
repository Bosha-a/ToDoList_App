package com.example.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class descriptionController {

    @FXML
    private Label deadlineLabel;

    @FXML
    private Label taskLabel;

    @FXML
    private TextArea detailsTextArea;

    public void setTextField(String name, String deadline, String details) {
        taskLabel.setText(name);
        deadlineLabel.setText(deadline);
        detailsTextArea.setText(details);
    }
}