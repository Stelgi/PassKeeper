package pass;

import java.sql.*;

public class ConnectionThread extends Thread{

    @Override
    public void run() {
        super.run();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPass(String logCur) {
        String retPass = null;
        try {
            Statement st = App.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT `hash` FROM `users` WHERE login = '" + logCur + "';");
            retPass = rs.getString("hash");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retPass;
    }
}
