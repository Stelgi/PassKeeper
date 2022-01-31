package pass;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class NewSiteInfoDialogController {
    @FXML
    private ImageView imgT;

    @FXML
    private TextField passInput;

    @FXML
    private Label lbl;

    @FXML
    private void initialize(){
        System.out.println("hello");

    }

    public void fileUpload(){
        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file","*.ico"));
            File file = fc.showOpenDialog(null);
            Image img = new Image(file.toURI().toString());

            imgT.setImage(img);
            initialize();


            System.out.println(imgT.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
