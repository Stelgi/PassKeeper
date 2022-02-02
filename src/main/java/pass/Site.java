package pass;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class Site {
    private int id;
    private String name;
    private String url;
    private String pathImg;

    public Site(int id, String name, String url, String pathImg) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.pathImg = pathImg;
    }

    public Site(){

    }

    @FXML
    public static void refreshListView(ArrayList<Site> sites, ListView<Site> listViewSites) {
        listViewSites.getItems().clear();
        for(Site site:sites){
            listViewSites.getItems().add(site);
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String str) {
        this.pathImg = str;
    }
}
