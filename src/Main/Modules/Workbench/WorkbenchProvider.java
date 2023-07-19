package Main.Modules.Workbench;

import Main.Interfaces.IGraphPrimitive;
import Main.Models.HouseProject;
import Main.Utils.Containers.NodeModelContainer;
import Main.Utils.MouseGestures;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class WorkbenchProvider implements PropertyChangeListener {

    private HouseProject houseProject;
    private Pane MainCanvas;
    private PropertyChangeSupport support;

    private String selectedLevelName;
    private NodeModelContainer selectedItem;

    public WorkbenchProvider(Pane canvas) {
        houseProject = HouseProject.getInstance();
        this.MainCanvas = canvas;
        if(houseProject.getBuilding().getLevels().size() > 0)
            this.selectedLevelName = houseProject.getBuilding().getLevels().get(0).getName();

        support = new PropertyChangeSupport(this);
        workbenchInitialize();
    }

    public void DrawLevel(String key) {
        int index = HouseProject.getInstance().getBuilding().getIndexLevelWithName(key);
        selectedLevelName = key;
        if (index >= 0) {
            MainCanvas.getChildren().clear();
            MainCanvas.getChildren().add(HouseProject.getInstance().getBuilding().getLevels().get(index).getLevelNode());
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()){
            case "levelIndex":
                DrawLevel(propertyChangeEvent.getNewValue().toString());
                break;
            case "reRender":
                DrawLevel(selectedLevelName);
                break;

            default:
                break;
        }
    }

    public void BroadcastReRenderCommand(){
        support.firePropertyChange("reRender", null, selectedItem);
    }

    public void BroadcastSelectedItem(){
        support.firePropertyChange("newSelectedItemOnWorkbench", null, selectedItem);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private void workbenchInitialize(){

    }


    public NodeModelContainer getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(NodeModelContainer selectedItem) {
        this.selectedItem = selectedItem;
        BroadcastSelectedItem();
    }


    public String getSelectedLevelName() {
        return selectedLevelName;
    }

    public void setSelectedLevelName(String selectedLevelName) {
        this.selectedLevelName = selectedLevelName;
    }
}
