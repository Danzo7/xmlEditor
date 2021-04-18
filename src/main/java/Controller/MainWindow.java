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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static launcher.Main.*;
import static model.xmlFilePlaceholder.placeHolder;
import static model.xmlPatterns.computeHighlighting;
import static model.xmlValidator.dtdValidation;

public class MainWindow implements Initializable {
    public AnchorPane xmlArea;
    ArrayList<XMLWorker> xml=null;
    CodeArea codeArea;
    int CURRENT_TAB=-1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
generateCodeArea(xmlArea,placeHolder);
    }


    void generateCodeArea(Pane container,String content) {
        codeArea = new CodeArea();
        codeArea.prefWidthProperty().bind(container.widthProperty());
        codeArea.prefHeightProperty().bind(container.heightProperty());


        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
        //  codeArea.replaceText(0, 0, this.xml.content);
          codeArea.replaceText(0, 0, content);
        container.getChildren().setAll(codeArea);
       sizeChanged.onReceive((event)->{
           codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        });
    }






    public void saveDocument(ActionEvent actionEvent) throws IOException {
        if(xml!=null)
            Files.write(Path.of(xml.get(CURRENT_TAB).name),codeArea.getText().getBytes());
    }

    public void openDocument(ActionEvent actionEvent) throws IOException {
        String selectedFile = fileChooser.showOpenDialog(mainStage).getAbsolutePath();
        xml.add(new XMLWorker(selectedFile));
        CURRENT_TAB++;
        generateCodeArea(xmlArea,xml.get(CURRENT_TAB).content);

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
