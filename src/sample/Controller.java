package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn filename;
    @FXML
    private TableColumn ActualClass;
    @FXML
    private TableColumn spamProbability;

    @FXML
    private TextField Accuracy;

    @FXML
    private Button enterBtn;

    @FXML
    private TextField Percision;

    private static double accuracy = 0;
    private static double percision = 0;
    Stage stage1;

    @FXML
    public void initialize() throws FileNotFoundException {
        if(filename !=null){
            filename.setCellValueFactory(new PropertyValueFactory<>("filename"));
            ActualClass.setCellValueFactory(new PropertyValueFactory<>("ActualClass"));
            spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
            tableView.setItems(DataSource.getInformation());
            Accuracy.setText(Double.toString(accuracy));
            Percision.setText(Double.toString(percision));
        }


    }

    /**
     * calculating the accuracy and percision of the data
     * @param numOfFiles
     * @param numTruePositives
     * @param numFalsePositives
     * @param numTrueNegatives
     */
    public static void DetermineAccuracy(double numOfFiles, double numTruePositives,double numFalsePositives,double numTrueNegatives){

        accuracy = (numTruePositives + numTrueNegatives) / numOfFiles;
        percision = numTruePositives/  (numFalsePositives + numTruePositives);


    }

    /**
     * The directory is choosen and where the scenes will switch
     * @param event
     * @throws IOException
     */
    @FXML
    public void btnOnPress(ActionEvent event) throws IOException {
        Main main = new Main();
        Stage Window = (Stage) enterBtn.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(Window);
        DataSource dataSource = new DataSource();
        dataSource.MainDirec(mainDirectory);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Window.setScene(new Scene(root, 500, 400));

    }

}
