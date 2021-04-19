package Controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.XMLWorker;
import org.apache.ant.compress.taskdefs.Ar;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static launcher.Main.*;
import static model.xmlFilePlaceholder.placeHolder;
import static model.xmlPatterns.computeHighlighting;
import static model.xmlValidator.dtdValidation;

public class MainWindow implements Initializable {

    public JFXTabPane tabpane;
    ArrayList<XMLWorker> xml=new ArrayList<>();
    ArrayList<CodeArea> codeAreas=new ArrayList<>();
    int CURRENT_TAB=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
tabpane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
   CURRENT_TAB= (int) newValue;
    System.out.println(CURRENT_TAB);
    System.err.println("changed");

});
    }


    void generateCodeArea(Pane container,String content) {
        CodeArea codeArea = new CodeArea();
        codeArea.prefWidthProperty().bind(container.widthProperty());
        codeArea.prefHeightProperty().bind(container.heightProperty());
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
          codeArea.replaceText(0, 0, content);
        container.getChildren().setAll(codeArea);
       sizeChanged.onReceive((event)->{
           codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        });
       codeAreas.add(codeArea);
    }






    public void saveDocument(ActionEvent actionEvent) throws IOException {
        if(xml!=null)
            Files.write(Path.of(xml.get(CURRENT_TAB).name), codeAreas.get(CURRENT_TAB).getText().getBytes());
    }

    public void openDocument(ActionEvent actionEvent) throws IOException {
        String selectedFile = fileChooser.showOpenDialog(mainStage).getAbsolutePath();
        xml.add(new XMLWorker(selectedFile));
        Tab tab = new Tab();
        tab.setText(xml.get(xml.size()-1).name);
        tab.setContent(new AnchorPane());
        tabpane.getTabs().add(tab);
        tabpane.getSelectionModel().select(tab);
        generateCodeArea((Pane) tabpane.getTabs().get(xml.size()-1).getContent(),xml.get(xml.size()-1).content);

    }

    public void newDocument(ActionEvent actionEvent) throws IOException {

    }

    public void saveAsDocument(ActionEvent actionEvent) throws IOException {

    }

    public void exitProgram(ActionEvent actionEvent) {
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
