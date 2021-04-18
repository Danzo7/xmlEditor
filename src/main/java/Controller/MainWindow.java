package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.XMLWorker;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static launcher.Main.*;
import static model.xmlFilePlaceholder.placeHolder;
import static model.xmlPatterns.computeHighlighting;
import static model.xmlValidator.dtdValidation;

public class MainWindow implements Initializable {
    public AnchorPane xmlArea;
    XMLWorker xml=null;
    CodeArea codeArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    void generateCodeArea(Pane container) {
        codeArea = new CodeArea();
        codeArea.prefWidthProperty().bind(container.widthProperty());
        codeArea.prefHeightProperty().bind(container.heightProperty());


        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
        codeArea.replaceText(0, 0, this.xml.content);
        container.getChildren().setAll(codeArea);
       sizeChanged.onReceive((event)->{
           codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        });
    }






    public void saveDocument(ActionEvent actionEvent) throws IOException {
        System.out.println(codeArea.getText().getBytes());
        if(xml!=null)
            Files.write(Path.of(xml.name),codeArea.getText().getBytes());
    }

    public void openDocument(ActionEvent actionEvent) throws IOException {
        String selectedFile = fileChooser.showOpenDialog(mainStage).getAbsolutePath();
        xml=new XMLWorker(selectedFile);
        generateCodeArea(xmlArea);

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
