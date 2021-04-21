package launcher;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
public static double WIDTH=400,HEIGHT=400;
    public static async sizeChanged=new async();
public static Stage mainStage;
    public static   FileChooser fileChooser=null;
    private static void setSize(double w,double h){
        WIDTH=w;HEIGHT=h;
        sizeChanged.dispatchEvent();
    }

public void init() throws IOException {
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
        scene.getStylesheets().add(Main.class.getResource("/styles/xml-styling.css").toExternalForm());
        primaryStage.setScene(scene);
        mainStage=primaryStage;
        primaryStage.show();
     ;
//
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                setSize( primaryStage.getWidth() , primaryStage.getHeight());

        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);


    }
}
