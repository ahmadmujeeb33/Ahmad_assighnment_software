package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Title.fxml"));
        primaryStage.setTitle("Module 4 - Example 2");
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
//        scene.getStylesheets().add(Main.class.getResource("mystyle.css").toExternalForm());
        Controller controller = new Controller();
        //controller.something(primaryStage);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
