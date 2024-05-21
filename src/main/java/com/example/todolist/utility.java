package com.example.todolist;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class utility {

    public static void changeToScene(Class CName, Event EName, String SceneFileStart) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CName.getResource(SceneFileStart));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) EName.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
