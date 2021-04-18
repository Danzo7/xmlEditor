package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static launcher.Main.*;
import static model.xmlFilePlaceholder.placeHolder;
import static model.xmlPatterns.computeHighlighting;

public class MainWindow implements Initializable {
    public AnchorPane codeArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generateCodeArea(codeArea);
    }


    static void generateCodeArea(Pane container) {
        CodeArea codeArea = new CodeArea();
        codeArea.setPrefWidth(container.getWidth());
        codeArea.setPrefHeight(container.getHeight());
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
        codeArea.replaceText(0, 0, placeHolder);
        container.getChildren().setAll(codeArea);
        sizeChanged.onReceive((event)->{
            codeArea.setPrefWidth(WIDTH);
            codeArea.setPrefHeight(HEIGHT);
        });
    }





    public void saveDocument(ActionEvent actionEvent) {
    }

    public void openDocument(ActionEvent actionEvent) {
    }

    public void newDocument(ActionEvent actionEvent) {
    }

    public void saveAsDocument(ActionEvent actionEvent) {
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
