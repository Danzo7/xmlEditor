package dr;

import com.sun.javafx.application.LauncherImpl;

public class Launcher {
    public static void main(String[]args){
        System.setProperty("prism.text", "t2k");
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("prism.allowhidpi", "false");
        LauncherImpl.launchApplication(Main.class,null, args);

    }
}
