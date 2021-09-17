package pass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class MainController {
    private Connection connection;
    private ArrayList<Site> sites = new ArrayList<>();
    private ArrayList<Site> currentSites;
    private int temp;
    @FXML
    private VBox sitesLayout = new VBox();
    @FXML
    private ListView<Site> listViewSites = new ListView<>();
    @FXML
    private TableView<AccountSite> mainTableView;
    @FXML
    private Label topLabel;

    private TableColumn<AccountSite, Integer> idColumn = new TableColumn<AccountSite, Integer>("ID");
    private TableColumn<AccountSite, Integer> idSiteColumn = new TableColumn<AccountSite, Integer>("IDSite");
    private TableColumn<AccountSite, String> loginColumn = new TableColumn<AccountSite, String>("Login");
    private TableColumn<AccountSite, String> passColumn = new TableColumn<AccountSite, String>("Password");
    private TableColumn<AccountSite, String> nameColumn2 = new TableColumn<AccountSite, String>("URL");

    private static int i = 0;
    private static int z = 0;
    private long first = -999;
    private long lastTime;

    public MainController(){
        connection = App.connectToDB();
    }

    @FXML
    private void initialize() {
        if(connectDB()){
            try{
                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM `sites`;");
                while(rs.next()){
                    sites.add(new Site());
                    sites.get(i).setId(rs.getInt(1));
                    sites.get(i).setName(rs.getString(2));
                    sites.get(i).setUrl(rs.getString(3));
                    i++;
                }
                currentSites = new ArrayList<Site>(sites);
                listViewSites.setCellFactory(param -> new ListViewSitesCell());
                Site.refreshListView(currentSites, listViewSites);


                listViewSites.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        initTableView(listViewSites.getSelectionModel().getSelectedIndex());
                    }
                });
//            Label lb = new Label("Gesa");
//            lb.setStyle("-fx-border-color: black; -fx-border-width: 1");
//            lb.setPrefWidth(180.5);
//            lb.setAlignment(Pos.BASELINE_CENTER);
//            sitesLayout.getChildren().addAll(lb);

            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else{
            System.err.println("Fail to connect with DB");
        }

    }

    @FXML
    private void initTableView(int a){
        temp = a;
        if(mainTableView.getColumns().isEmpty()) {
            idColumn.setCellValueFactory(new PropertyValueFactory<AccountSite, Integer>("id"));
            idColumn.setPrefWidth(70);
            loginColumn.setCellValueFactory(new PropertyValueFactory<AccountSite, String>("login"));
            loginColumn.setPrefWidth(120);
//            loginColumn.setCellFactory(p -> new TableCell<>(){
//                @Override
//                protected void updateItem(String s, boolean b) {
//                    super.updateItem(s, b);
//                    if(b){
//                        setGraphic(null);
//                        setText(null);
//                    }
//                    else{
//                        setText(s);
//                        setOnMouseClicked(e -> {
//                            System.out.println(s);
//                        });
//
//                    }
//                }
//            });
            loginColumn.setCellFactory(accountSiteStringTableColumn -> new AccCell());


            passColumn.setCellValueFactory(new PropertyValueFactory<AccountSite, String>("pass"));
            passColumn.setPrefWidth(120);
            passColumn.setEditable(true);
            passColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//            passColumn.setOnEditCancel(accountSiteStringCellEditEvent -> );
            passColumn.setOnEditCommit((TableColumn.CellEditEvent<AccountSite, String> t) -> {
                ((AccountSite) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPass(t.getNewValue());
                String tempA = t.getNewValue();
                String sql = "UPDATE `accounts` SET 'pass' = '" + tempA + "' WHERE `idSite` = " + (listViewSites.getSelectionModel().getSelectedIndex()+1) + " AND `id` = " + t.getRowValue().getId() + ";";
                try{
                    Statement s = connection.createStatement();
                    int rows = connection.createStatement().executeUpdate(sql);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }


            });
            mainTableView.setEditable(true);
            mainTableView.getColumns().addAll(idColumn, loginColumn, passColumn);

        }
        mainTableView.setItems(getAccountSite(a));
        mainTableView.getSelectionModel().setCellSelectionEnabled(true);
    }

    private ObservableList<AccountSite> getAccountSite(int a) {
        ObservableList<AccountSite> tempListAccountSite;
        ArrayList<AccountSite> tempArrayAccountSite = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM `accounts`" +
                    " WHERE idSite = '" + ++a + "';");
            while(rs.next()){
                tempArrayAccountSite.add( new AccountSite(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            tempListAccountSite = FXCollections.observableArrayList(tempArrayAccountSite);
            return tempListAccountSite;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private boolean connectDB() {
        try {
            if (!App.connection.isClosed()) {
                return true;
            }
            else System.out.println("Closed");
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static class ListViewSitesCell extends ListCell<Site>{

        private Label s = new Label("");
        private HBox a;

        @Override
        protected void updateItem(Site o, boolean b) {
            super.updateItem(o, b);
            if (b) {
                setGraphic(null);
            } else {
                if(a == null) {
                    initGraph(o);
                }
                setGraphic(a);
            }
        }

        private void initGraph(Site o){
            s.setText(o.getName());
            a = new HBox(s);
            a.setAlignment(Pos.CENTER);
        }
    }

    private class AccCell extends TableCell<AccountSite, String> {
        @Override
        protected void updateItem(String s, boolean b) {
            super.updateItem(s, b);
            if(b){
                setGraphic(null);
                setText(null);
                setStyle(null);
            }
            else{
                setText(s);
                SingleTransition a = new SingleTransition(getText());

                setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 1) {
                        //singlePressPause.play();
                        a.play();
                    } else if (mouseEvent.getClickCount() == 2) {
                        //singlePressPause.stop();
                        a.stop();
                    }
                });
            }
        }

    }

    @FXML
    private void addAccount(){
        try{
            if(listViewSites.getSelectionModel().getSelectedIndex() < 0){
                throw new NullPointerException();
            }
            FXMLLoader fx = new FXMLLoader(getClass().getResource("newAccInfoDialog.fxml"));
            Parent parent = fx.load();
            NewAccInfoDialogController nd = fx.getController();
            nd.start(listViewSites);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            initTableView(temp);
        }catch (NullPointerException e){
            System.out.println("Choose any site");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void delAccount(ActionEvent actionEvent) {
        try{
            if(mainTableView.getSelectionModel().getSelectedIndex() < 0){
                throw new NullPointerException();
            }
            int i = mainTableView.getItems().get(mainTableView.getSelectionModel().getSelectedIndex()).getId();

            Statement st = App.connectToDB().createStatement();
            String sql = "DELETE FROM accounts WHERE id = " + i + ";";
            int rows = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Успешно удалена " + rows + " строка", "Удалено", JOptionPane.INFORMATION_MESSAGE);
            initTableView(temp);
        }catch (NullPointerException e){
            System.out.println("Choose any cell");
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    @FXML
    public void addSiteObject(ActionEvent actionEvent) {
        try{
            FXMLLoader fx = new FXMLLoader(getClass().getResource("newSiteInfoDialog.fxml"));
            Parent parent = fx.load();
            NewSiteInfoDialogController nd = fx.getController();
            nd.fileUpload();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}