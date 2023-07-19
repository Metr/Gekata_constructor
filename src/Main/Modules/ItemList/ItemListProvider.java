package Main.Modules.ItemList;


import Main.Models.BasicWall;
import Main.Models.HouseProject;
import Main.Models.Level;
import Main.Utils.Aggregators.LevelButtonsAggregator;
import Main.Utils.Aggregators.LevelItemButtonsAggregator;
import Main.Utils.Events.EventContextController;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

import static javafx.geometry.Pos.BASELINE_LEFT;

public class ItemListProvider implements PropertyChangeListener {

    HouseProject houseProject;

    VBox ItemContainer;
    ScrollPane ItemScrollList;

    VBox LevelContainer;
    ScrollPane LevelScrollList;

    private PropertyChangeSupport support;


    public ItemListProvider(VBox ItemContainer, ScrollPane ScrollGraphItemList, VBox LevelContainer, ScrollPane ScrollGraphLevelList) {
        houseProject = HouseProject.getInstance();
        this.ItemContainer = ItemContainer;
        this.ItemScrollList = ScrollGraphItemList;
        this.LevelContainer = LevelContainer;
        this.LevelScrollList = ScrollGraphLevelList;
        support = new PropertyChangeSupport(this);
        itemListInitialize();
        levelListInitialize();
    }

    public void CreateWall() {
        if(HouseProject.getInstance().getBuilding().getLevels().size() >= 0) {
            String selectedLevelName = EventContextController.getWorkbenchProvider().getSelectedLevelName();
            int index = HouseProject.getInstance().getBuilding().getIndexLevelWithName(selectedLevelName);
            BasicWall wall = new BasicWall(150, 150, 200, 200);
            HouseProject.getInstance().getBuilding().getLevels().get(index).getWalls().add(wall);
            BroadcastReRenderCommand();
        }
    }

    public void CreateWayPoint() {
        System.out.println("way point created");

        BroadcastReRenderCommand();
    }

    public void CreateInterestPoint() {
        System.out.println("interest point created");

        BroadcastReRenderCommand();
    }

    public void CreateLevel() {
        String str = "unknown level " + HouseProject.getInstance().getBuilding().getLevels().size();
        houseProject.getBuilding().addLevel(new Level(str));
        BroadcastDrawableLevel(str);
        BroadcastReRenderCommand();
        reRender();
    }



    public void SelectedLevelChanged(String levelName) {
        BroadcastDrawableLevel(levelName);
        BroadcastReRenderCommand();
    }


    ////////////////////////////////////////////

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

/////////////////////////////////////////////////

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()) {
            case "reRender":
                reRender();
                break;


            default:
                break;
        }
    }

    public void BroadcastReRenderCommand()
    {
        support.firePropertyChange("reRender", null, null);
    }

    public void BroadcastDrawableLevel(String value) {
        support.firePropertyChange("levelIndex", "old_value", value);
    }

    //////////////////////////////////////////////

    private void itemListInitialize() {

        Path path = Path.of(System.getProperty("user.dir"), "src", "Main", "Resourses", "Icons", "line.png");

        LevelItemButtonsAggregator newItemsAggregator = LevelItemButtonsAggregator.getInstance();

        newItemsAggregator.registerNewButtonItem("Wall", actionEvent -> this.CreateWall(), path.toString());
        newItemsAggregator.registerNewButtonItem("Interest Point", actionEvent -> this.CreateInterestPoint(), path.toString());
        newItemsAggregator.registerNewButtonItem("Way Point", actionEvent -> this.CreateWayPoint(), path.toString());

        for (Map.Entry<String, EventHandler<ActionEvent>> item : newItemsAggregator.getItems().entrySet()) {
            InputStream is = null;
            try {
                is = new FileInputStream(newItemsAggregator.getPathToIcon(item.getKey()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image image = new Image(is);

            Button button = new Button(item.getKey(), new ImageView(image));

            button.setMaxWidth(Double.MAX_VALUE);
            button.setMinWidth(ItemScrollList.getWidth());
            button.setAlignment(BASELINE_LEFT);
            button.setOnAction(item.getValue());

            VBox vBox = new VBox(0, button);
            vBox.setMaxWidth(Double.MAX_VALUE);
            vBox.setMinWidth(ItemScrollList.getWidth());

            ItemScrollList.setFitToWidth(true);

            ItemContainer.getChildren().add(vBox);
        }
    }

    private void levelListInitialize(){
        LevelButtonsAggregator newItemsAggregator = LevelButtonsAggregator.getInstance();
        newItemsAggregator.registerNewButtonItem("Add level", actionEvent -> this.CreateLevel());

        for(Level level : HouseProject.getInstance().getBuilding().getLevels()){
            newItemsAggregator.registerNewButtonItem(level.getName(), actionEvent -> this.SelectedLevelChanged(level.getName()));
        }

        for (Map.Entry<String, EventHandler<javafx.event.ActionEvent>> item : newItemsAggregator.getItems().entrySet()) {

            Button button = new Button(item.getKey());

            button.setMaxWidth(Double.MAX_VALUE);
            button.setMinWidth(LevelScrollList.getWidth());
            button.setAlignment(BASELINE_LEFT);
            button.setOnAction(item.getValue());

            VBox vBox = new VBox(0, button);
            vBox.setMaxWidth(Double.MAX_VALUE);
            vBox.setMinWidth(LevelScrollList.getWidth());

            LevelScrollList.setFitToWidth(true);

            LevelContainer.getChildren().add(vBox);
        }
    }



    private void itemListReRender(){
        itemListInitialize();
    }

    private void levelListRerender(){
        LevelButtonsAggregator newItemsAggregator = LevelButtonsAggregator.getInstance();
        newItemsAggregator.getItems().clear();

        for(Level level : HouseProject.getInstance().getBuilding().getLevels()){
            newItemsAggregator.registerNewButtonItem(level.getName(), actionEvent -> this.SelectedLevelChanged(level.getName()));
        }

        Button button = new Button("Add level");
        newItemsAggregator.registerNewButtonItem("Add level", actionEvent -> this.CreateLevel());

        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinWidth(LevelScrollList.getWidth());
        button.setAlignment(BASELINE_LEFT);
        button.setOnAction(LevelButtonsAggregator.getInstance().getItems().get("Add level"));

        VBox vBox = new VBox(0, button);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setMinWidth(LevelScrollList.getWidth());
        LevelScrollList.setFitToWidth(true);
        LevelContainer.getChildren().add(vBox);

        for (Map.Entry<String, EventHandler<javafx.event.ActionEvent>> item : newItemsAggregator.getItems().entrySet()) {
            if(!item.getKey().equalsIgnoreCase("Add Level")) {
                button = new Button(item.getKey());

                button.setMaxWidth(Double.MAX_VALUE);
                button.setMinWidth(LevelScrollList.getWidth());
                button.setAlignment(BASELINE_LEFT);
                button.setOnAction(item.getValue());

                vBox = new VBox(0, button);
                vBox.setMaxWidth(Double.MAX_VALUE);
                vBox.setMinWidth(LevelScrollList.getWidth());

                LevelScrollList.setFitToWidth(true);

                LevelContainer.getChildren().add(vBox);
            }
        }
    }




    private void reRender()
    {
        ItemContainer.getChildren().clear();
        LevelContainer.getChildren().clear();
        LevelButtonsAggregator.getInstance().getItems().clear();
        LevelItemButtonsAggregator.getInstance().getItems().clear();
        itemListReRender();
        levelListRerender();
    }
}
