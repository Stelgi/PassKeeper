package pass;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class SingleTransition {
    private PauseTransition singlePressPause;

    SingleTransition(String text){
        singlePressPause = new PauseTransition(Duration.millis(300));
        singlePressPause.setOnFinished(e -> {
            System.out.println("Succes copy - " + text);
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            clip.setContents(new StringSelection(text), null);
        });
    }

    public void play(){
        singlePressPause.play();
    }

    public void stop() {
        singlePressPause.stop();
    }

}
