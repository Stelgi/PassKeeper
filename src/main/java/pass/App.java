package pass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * JavaFX App
 */
public class App extends Application {
    public static Connection connection;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main2"));
        stage.setScene(scene);
        stage.setTitle("PassKeeper v0.1.3");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        //Закрываем стейдж с авторизацией
        App.close();
        // Создаем новый стейдж и пишим ему параметры
        Stage a = new Stage();
        a.setScene(new Scene(loadFXML(fxml)));
        a.setResizable(false);
        a.setTitle("PassSaver version 0.0.1 by Stelgi");
        a.show();
    }

    private static void close() {
        scene.getWindow().hide();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Connection connectToDB(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}