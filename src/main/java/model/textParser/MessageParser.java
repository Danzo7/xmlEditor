package model.textParser;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class MessageParser {

    private static final List<TextParser> parsers = new ArrayList<>();
    static {
        parsers.add(new HyperLinkParser());
        parsers.add(new TextParser());
    }

    public static List<Node> parseMessage(String message) {
        List<Node> nodes = new ArrayList<Node>();

        String[] split = message.split(" ");
        for (String s : split) {
            for(TextParser parser : parsers){
                Node pNode = parser.parseText(s + " ");
                if(pNode == null){
                    continue;
                }else{
                    nodes.add(pNode);
                    break;
                }
            }
        }

        return nodes;
    }

    private Node parseSmeily(String s) {

        return null;
    }


}