package model.textParser;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author <a href="mailto:marcel.vogel@proemion.com">mv1015</a>
 *
 */
public class HyperLinkParser extends TextParser {

    /* (non-Javadoc)
     * @see de.kogs.xmpp.client.TextParsing.TextParser#parseText(java.lang.String)
     */
    @Override
    public Node parseText(String text) {

        try {
            URL url = new URL(text);
            Hyperlink hyperlink = new Hyperlink(text);
            hyperlink.setOnAction((event) -> {
                openWebpage(url);
            });
            return hyperlink;
        } catch (MalformedURLException e) {
        }

        return null;
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}