package dr;

import Controller.XmlEditor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.stageLoader;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    public void init() throws IOException {

    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/dr/FXML/PAGES/xmlEditor.fxml"));
        Scene scene = new Scene(page);
        scene.getStylesheets().add(Main.class.getResource("/dr/CSS/xml-styling.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
//



    }
}
