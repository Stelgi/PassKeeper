package pass;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class NewSiteInfoDialogController {
    @FXML
    private ImageView iv;

    public void fileUpload(){
        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", ".txt"));
            File file = fc.showOpenDialog(null);
            BufferedImage di = ImageIO.read(file);

        }catch (Exception e){

        }

    }
}
