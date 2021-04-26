package launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application {
public static Stage mainStage;
    public static   FileChooser fileChooser=null;

public void init() {
     fileChooser= new FileChooser();
    fileChooser.getExtensionFilters().setAll(
            new FileChooser.ExtensionFilter("XML Files", "*.xml"),
            new FileChooser.ExtensionFilter("DTD Files", "*.dtd"),
            new FileChooser.ExtensionFilter("XSD Files", "*.xsd")
    );

    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/fxml/PAGES/xmlTextArea.fxml"));
        Scene scene = new Scene(page);
        primaryStage.setTitle("XML Editor");
        primaryStage.getIcons().add(new Image("/images/icon.png"));

        scene.getStylesheets().add(Main.class.getResource("/styles/xml-styling.css").toExternalForm());
        primaryStage.setScene(scene);
        mainStage=primaryStage;
        primaryStage.show();
    }
}
