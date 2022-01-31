package pass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;


public class NewSiteInfoDialogController {
    @FXML
    private ImageView imgT;

    @FXML
    private TextField loginInput;

    @FXML
    private javafx.scene.control.Label lblBtn;

    @FXML
    private Label lbl;

    @FXML
    private void initialize(){
        System.out.println("NewSiteInfoDialogController initialized");
    }


    public void fileUpload(){
        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file","*.png"));
            File file = fc.showOpenDialog(null);


            Image img = new Image(file.toURI().toString());
            if(img.isError()){
                JOptionPane.showMessageDialog(null, "Ошибка загрузки файла!\nПопробуйте еще раз загрузить " + file.getName() + "\nИли попробуйте загрузить другой файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                throw new Exception("Error on download file");
            }
            else{
                lblBtn.setText("");
                imgT.setImage(img);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onButtonClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY){
            if(isAdded()){
                ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
            }
        }
        else{
            System.out.println("Nothing");
        }
    }

    @FXML
    private boolean isAdded(){
        try {
            Connection connection = (App.connection != null) ? App.connection : App.connectToDB();
            String sql = "INSERT INTO sites(name, url) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, loginInput.getText());
            preparedStatement.setString(2, Math.random() + "");
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    public ImageView getImgT() {
        return imgT;
    }

    public TextField getLoginInput() {
        return loginInput;
    }

}
