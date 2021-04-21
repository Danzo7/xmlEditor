package Controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.XMLWorker;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static launcher.Main.fileChooser;
import static launcher.Main.mainStage;

public class MainWindow implements Initializable {

    public JFXTabPane tabbedPan;
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
        Indicator.setText("Choose a file. ");
        information.setText("IDLE");
tabbedPan.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
   CURRENT_TAB= (int) newValue;
   if(CURRENT_TAB>=0) {
       information.setText(xml.get(CURRENT_TAB).infoString);
       Indicator.setText(xml.get(CURRENT_TAB).errorString);
   }
   else{
       Indicator.setText("Choose a file. ");
    information.setText("IDLE");}


});

    }


    void generateCodeArea(BorderPane container,String content) {
        CodeArea codeArea = new CodeArea();
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
            xml.get(CURRENT_TAB).setContent(codeArea.getText());
            try {
                codeArea.setStyleSpans(0, xml.get(CURRENT_TAB).computeHighlighting2(newText));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Indicator.setText(xml.get(CURRENT_TAB).errorString);
            information.setText(xml.get(CURRENT_TAB).infoString);


        });
        codeArea.replaceText(0, 0, content);
        container.setCenter(codeArea);
        Indicator.setText(xml.get(CURRENT_TAB).errorString);
        information.setText(xml.get(CURRENT_TAB).infoString);
       codeAreas.add(codeArea);
    }








    public void openDocument() throws IOException {
        String selectedFile;

            try{
         selectedFile = fileChooser.showOpenDialog(mainStage).getAbsolutePath();
                xml.add(new XMLWorker(selectedFile));
                Tab tab = new Tab();
                tab.setText(xml.get(xml.size()-1).name);
                BorderPane p =new BorderPane();
                tab.setContent(p);
                tabbedPan.getTabs().add(tab);
                tabbedPan.getSelectionModel().select(tab);
                generateCodeArea(p,xml.get(CURRENT_TAB).getContent());
                xml.get(CURRENT_TAB).isSaved=true;

            }
            catch(NullPointerException e){
                Indicator.setText("No file is selected.");
            }

    }


    public void newDocument() {

    }
    public void saveDocument() throws IOException {
        if(xml.size()>0) {
            if(xml.get(CURRENT_TAB).isSaved){
            Indicator.setText("Already saved.");
            }
            else{
            Files.write(Path.of(xml.get(CURRENT_TAB).name), xml.get(CURRENT_TAB).getContent().getBytes());
            xml.get(CURRENT_TAB).saving();
            Indicator.setText("saved.");
            }
        }}

    public void saveAsDocument() throws IOException {

        if(xml.size()>0) {
            String selectedFile;
            try{
                selectedFile = fileChooser.showOpenDialog(mainStage).getAbsolutePath();
                Files.write(Path.of(selectedFile), xml.get(CURRENT_TAB).getContent().getBytes());
                xml.get(CURRENT_TAB).saving();
                Indicator.setText("Saved successfully in : "+selectedFile+".");

            }
            catch(NullPointerException e){
                Indicator.setText("No file is selected.");
            }

        }
    }

    public void exitProgram() {
        Platform.exit();
    }

    public void undo() {
    }

    public void redo() {
    }

    public void cut() {
        if(codeAreas.size()>0){
            ClipboardContent content = new ClipboardContent();
            content.putString(codeAreas.get(CURRENT_TAB).getSelectedText());
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);
            codeAreas.get(CURRENT_TAB).replaceText(codeAreas.get(CURRENT_TAB).getSelection(),"");

        }
    }

    public void copy() {
        if(codeAreas.size()>0){
            ClipboardContent content = new ClipboardContent();
            content.putString(codeAreas.get(CURRENT_TAB).getSelectedText());
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);
        }

    }

    public void paste() {
        if(codeAreas.size()>0){
            Clipboard clipboard = Clipboard.getSystemClipboard();
            codeAreas.get(CURRENT_TAB).replaceText(codeAreas.get(CURRENT_TAB).getSelection(),clipboard.getString());
        }
    }

    public void selectAll() {
    }

    public void saveOutput() {
    }

    public void showSettings() {
    }

    public void showOnlineReference() {
    }

    public void showAboutWindow() {
    }

    public void closeTab() {
        if(xml.size()>0){
            if(xml.get(CURRENT_TAB).isSaved){
                xml.remove(CURRENT_TAB);
                codeAreas.remove(CURRENT_TAB);
                tabbedPan.getTabs().remove(CURRENT_TAB);

            }
            else{
                Indicator.setText("File is not save please save before close.");
            }}
    }
}
