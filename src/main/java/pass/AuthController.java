package pass;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;



public class AuthController {
    @FXML
    private TextField logField;
    @FXML
    private TextField passField;

/*    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }*/

    @FXML
    private void toEnter() throws IOException, SQLException, InterruptedException {
        //if(authUser()) {
            App.setRoot("main2");
        //}
       // else{
        //    System.err.println("No valid pass");
        //}
    }

    private boolean authUser() throws SQLException, InterruptedException {
        App.connectToDB();
        ConnectionThread cn = new ConnectionThread();
        cn.start();
        //if( cn.getPass("logField") == BCrypt.hashpw(passField.getText(), BCrypt.gensalt()))
        if(cn.getPass(logField.getText()).equals(passField.getText()))
            return true;
        else return false;

    }
}
