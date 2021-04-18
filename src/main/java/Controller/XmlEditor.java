package Controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class XmlEditor implements Initializable {

    public Pane container;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MainWindow.generateCodeArea(container);
    }

}
