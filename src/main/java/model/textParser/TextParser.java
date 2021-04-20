package model.textParser;


import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * @author <a href="mailto:marcel.vogel@proemion.com">mv1015</a>
 *
 */
public class TextParser {

    public Node parseText(String text) {
        return new Text(text);
    }

}