package pass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class NewAccInfoDialogController {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField loginInput;
    @FXML
    private TextField passInput;

    private Connection conn;
    private int curListViewSitesIndex;

    @FXML
    private void initialize(){

    }

    public void onClick(MouseEvent mouseEvent) {
        try{
            conn = App.connectToDB();
            String sql = "INSERT INTO accounts(idSite, login, pass) VALUES(" + (curListViewSitesIndex+1) + ", '" + loginInput.getText() + "', '" + passInput.getText() + "');";
            int rows = conn.createStatement().executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Успешно добавлено " + rows + " строка", "Добавлено", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        btnAdd.getScene().getWindow().hide();
    }

    public void start(ListView<Site> listViewSites) {
        curListViewSitesIndex = listViewSites.getSelectionModel().getSelectedIndex();
    }
}