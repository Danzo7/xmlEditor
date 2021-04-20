package Controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.XMLWorker;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static launcher.Main.*;

public class MainWindow implements Initializable {

    public JFXTabPane tabpane;
    public AnchorPane TabContainer;
    public Label Indicator;
    public Label information;
    public MenuItem exitMenuItem;
    ArrayList<XMLWorker> xml=new ArrayList<>();
    ArrayList<CodeArea> codeAreas=new ArrayList<>();
    int CURRENT_TAB=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TabContainer.setStyle("-fx-background-color:#212121;");
        Indicator.setText("Please Open a XML file : ");
        try {             openDocument(null);} catch (IOException e) {             e.printStackTrace();         }
        System.out.println(TabContainer.getWidth()+" and "+TabContainer.getHeight());

tabpane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
   CURRENT_TAB= (int) newValue;
    information.setText(xml.get(CURRENT_TAB).infoString);
    Indicator.setText(xml.get(CURRENT_TAB).errorString);

});

    }


    void generateCodeArea(BorderPane container,String content) {
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            xml.get(CURRENT_TAB).content=codeArea.getText();
            try {
                codeArea.setStyleSpans(0, xml.get(CURRENT_TAB).computeHighlighting2(newText));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Indicator.setText(xml.get(CURRENT_TAB).errorString);
            information.setText(xml.get(CURRENT_TAB).infoString);


        });
        codeArea.replaceText(0, 0, content);

            Indicator.setText(xml.get(CURRENT_TAB).errorString);
        information.setText(xml.get(CURRENT_TAB).infoString);


        container.setCenter(codeArea);

       sizeChanged.onReceive((event)->{


        });
       codeAreas.add(codeArea);
    }








    public void openDocument(ActionEvent actionEvent) throws IOException {
        String selectedFile;
        if(actionEvent==null)
            selectedFile = "C:\\Users\\aymen\\OneDrive\\Desktop\\xmlfile.xml";
        else
         selectedFile = fileChooser.showOpenDialog(mainStage).getAbsolutePath();

        xml.add(new XMLWorker(selectedFile));
        Tab tab = new Tab();
        tab.setText(xml.get(xml.size()-1).name);
        BorderPane p =new BorderPane();
        tab.setContent(p);
        tabpane.getTabs().add(tab);
        tabpane.getSelectionModel().select(tab);
        generateCodeArea((BorderPane) tabpane.getTabs().get(xml.size()-1).getContent(),xml.get(xml.size()-1).content);

    }

    public void newDocument(ActionEvent actionEvent) throws IOException {
        //saveDocument(null);
        if(xml.size()>0)
            System.out.println(xml.get(CURRENT_TAB).validate(2));
    }
    public void saveDocument(ActionEvent actionEvent) throws IOException {
        if(xml.size()>0)
            Files.write(Path.of(xml.get(CURRENT_TAB).name), xml.get(CURRENT_TAB).content.getBytes());
    }

    public void saveAsDocument(ActionEvent actionEvent) throws IOException {
        if(xml.size()>0) {
            File file = fileChooser.showSaveDialog(mainStage);
            if (file != null) {
         /*   FileWriter fileWriter=new FileWriter(file);
            fileWriter.write(xml.get(CURRENT_TAB).content);
            fileWriter.close();*/
                Files.write(Path.of(file.getAbsolutePath()), xml.get(CURRENT_TAB).content.getBytes());
            }
        }
    }

    public void exitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void undo(ActionEvent actionEvent) {
    }

    public void redo(Event event) {
    }

    public void cut(ActionEvent actionEvent) {
    }

    public void copy(ActionEvent actionEvent) {
    }

    public void paste(ActionEvent actionEvent) {
    }

    public void selectAll(ActionEvent actionEvent) {
    }

    public void saveOutput(ActionEvent actionEvent) {
    }

    public void showSettings(ActionEvent actionEvent) {
    }

    public void start(ActionEvent actionEvent) {
    }

    public void stop(ActionEvent actionEvent) {
    }

    public void showOnlineReference(ActionEvent actionEvent) {
    }

    public void showAboutWindow(ActionEvent actionEvent) {
    }
}
