package launcher;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


public class Main extends Application {
public static double WIDTH=400,HEIGHT=400;
public static async sizeChanged=new async();
    private static void setSize(double w,double h){
        WIDTH=w;HEIGHT=h;
        sizeChanged.dispatchEvent();

    }

public void init()   {

    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/fxml/PAGES/xmlTextArea.fxml"));
        Scene scene = new Scene(page);
        scene.getStylesheets().add(Main.class.getResource("/styles/xml-styling.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
//
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                setSize( primaryStage.getWidth() , primaryStage.getHeight());

        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);


    }
}
