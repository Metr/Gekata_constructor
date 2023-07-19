package Main.Models;

import Main.Interfaces.IPropertyChangeble;
import Main.Modules.ModelNavigator.ModelTreeProvider;
import Main.Utils.Containers.NodeModelContainer;
import Main.Utils.Generators.PropertyItemGenerator;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Building implements IPropertyChangeble {

    private String name;
    private String adress;
    private String google_adress;
    private String yandex_adress;
    private String osm_adress;

    private ArrayList<Level> Levels;

    public Building(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public Building() {
        this.name = "default_building_name";
        this.adress = "default_adress";
        this.google_adress = "g_a";
        this.yandex_adress = "y_a";
        this.osm_adress = "o_a";

        this.Levels = new ArrayList<Level>();

        //addLevel("level_1");
        //addLevel("level_2");
    }

    public Building(String name, String adress, String google_adress, String yandex_adress, String osm_adress) {
        this.name = name;
        this.adress = adress;
        this.google_adress = google_adress;
        this.yandex_adress = yandex_adress;
        this.osm_adress = osm_adress;
    }

    @Override
    public NodeModelContainer getTreePropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();

        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("name", this.name, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("adress", this.adress, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("google adress", this.google_adress, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("yandex adress", this.yandex_adress, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("osm adress", this.osm_adress, provider));

        return new NodeModelContainer(resultContainer, this, null);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        return null;
    }



    @Override
    public String getTreeItemName() {
        return getName();
    }

    @Override
    public void setAllProperties(IPropertyChangeble item) {
        Building building = (Building) item;

        this.setName(building.name);
        this.setAdress(building.adress);
        this.setGoogle_adress(building.google_adress);
        this.setYandex_adress(building.yandex_adress);
        this.setOsm_adress(building.osm_adress);

    }

    public void getPropertiesFromNode(VBox vBox) {
        //по свойствам
        for (Node node : vBox.getChildren()) {
            VBox item = (VBox) node;
            //по контейнерам лейбл+текстбокс
            HBox hBox = (HBox) item.getChildren().get(0);
            for (Node subNode : hBox.getChildren()) {
                if (subNode.getClass() == TextField.class) {
                    TextField text = (TextField) subNode;
                    switch (text.getId()){
                        case "name":
                            this.name = text.getText();
                            break;
                        case "adress":
                            this.adress = text.getText();
                            break;
                        case "google adress":
                            this.google_adress = text.getText();
                            break;
                        case "yandex adress":
                            this.yandex_adress = text.getText();
                            break;
                        case "osm_adress":
                            this.osm_adress = text.getText();
                            break;
                    }
                    break;
                }
            }
        }

    }


    public int getIndexLevelWithName(String levelName){
        int dontExist = -1;

        for(int i = 0; i < Levels.size(); i++)
            if(Levels.get(i).getName() == levelName)
                return i;

        return dontExist;
    }


    @Override
    public String dataToString() {
        return "name = " + this.name + ", adress = " + this.adress;
    }

    public void addLevel(Level level){
        this.Levels.add(level);
    }

    public void addLevel(String key){
        this.Levels.add(new Level(key));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGoogle_adress() {
        return google_adress;
    }

    public void setGoogle_adress(String google_adress) {
        this.google_adress = google_adress;
    }

    public String getYandex_adress() {
        return yandex_adress;
    }

    public void setYandex_adress(String yandex_adress) {
        this.yandex_adress = yandex_adress;
    }

    public String getOsm_adress() {
        return osm_adress;
    }

    public void setOsm_adress(String osm_adress) {
        this.osm_adress = osm_adress;
    }

    public ArrayList<Level> getLevels() {
        return Levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        Levels = levels;
    }

}
