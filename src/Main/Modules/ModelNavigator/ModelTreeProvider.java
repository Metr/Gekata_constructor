package Main.Modules.ModelNavigator;

import Main.Interfaces.IPropertyChangeble;
import Main.Models.BasicWall;
import Main.Models.HouseProject;
import Main.Models.Level;
import Main.Utils.Containers.NodeModelContainer;
import com.sun.source.tree.Tree;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

public class ModelTreeProvider implements PropertyChangeListener {

    private TreeView TreeView;
    private VBox PropertyView;

    private IPropertyChangeble ChangeableItem;
    private PropertyChangeSupport support;

    private NodeModelContainer nodeModelContainer;
    private boolean isObjectChanged = false;

    public ModelTreeProvider(TreeView treeView, VBox propertyView) {
        this.TreeView = treeView;
        this.PropertyView = propertyView;
        support = new PropertyChangeSupport(this);
        modelTreeInitialize();
    }

    private void modelTreeInitialize() {
        TreeItemContainer<String> root = new TreeItemContainer<String>
                (HouseProject.getInstance().getBuilding().getName(), HouseProject.getInstance().getBuilding());

        // по этажам
        //for (Map.Entry lev : HouseProject.getInstance().getBuilding().getLevels().entrySet()) {
        for (Level level : HouseProject.getInstance().getBuilding().getLevels()) {
            TreeItemContainer<String> tmpLevelItem = new TreeItemContainer<String>(level.getName(), level);

            // по содержимому этажей
            for (int i = 0; i < level.getWalls().size(); i++) {
                BasicWall wall = (BasicWall) level.getWalls().get(i);
                tmpLevelItem.getChildren().add(new TreeItemContainer<String>(wall.getTreeItemName(), wall));
            }

            //установили результат
            root.getChildren().add(tmpLevelItem);
        }
        TreeView.setRoot(root);


        MultipleSelectionModel<TreeItem<String>> selectionModel = TreeView.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        selectionModel.selectedItemProperty().addListener(
                (changed, oldValue, newValue) -> this.TreeViewSelectionChanged(changed, oldValue, newValue)
        );
    }



    public void TreeViewSelectionChanged(ObservableValue<? extends TreeItem<String>> changed, TreeItem<String> oldValue, TreeItem<String> newValue) {
        if (newValue != null) {
            SelectionModel<TreeItem<String>> selectionModel = TreeView.getSelectionModel();
            TreeItemContainer<IPropertyChangeble> newSelectedItem = (TreeItemContainer<IPropertyChangeble>) selectionModel.getSelectedItem();
            nodeModelContainer = newSelectedItem.getModel().getTreePropertyNode(this);
            PropertyView.getChildren().clear();
            PropertyView.getChildren().add(nodeModelContainer.getPropertyNode());
            if (oldValue == null)
                oldValue = newValue;
            if (isObjectChanged) {
                oldValue.setValue(ChangeableItem.getTreeItemName());
                BroadcastReRenderCommand();
                isObjectChanged = false;
            }
        }
    }

    public void onTreeItemChange() {
        try {
            nodeModelContainer.getPropertyModel().getPropertiesFromNode((VBox) nodeModelContainer.getPropertyNode());
            IPropertyChangeble item = nodeModelContainer.getPropertyModel();
            item.getPropertiesFromNode((VBox) nodeModelContainer.getPropertyNode());
            ChangeableItem = item;
            isObjectChanged = true;
            BroadcastReRenderCommand();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onWorkbenchItemChange(){
        nodeModelContainer.getPropertyModel().getPropertiesFromNode((VBox) PropertyView.getChildren().get(0));
        BroadcastReRenderCommand();
        //System.out.println("wwwww");

    }


    public void setNewLevelIndex(String value) {
        support.firePropertyChange("levelIndex", "old_value", value);
    }


    ////////////////////////////////////////////////////////////////

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()) {
            case "reRender":
                if(propertyChangeEvent.getNewValue() != null && propertyChangeEvent.getNewValue().getClass() == NodeModelContainer.class) {
                    NodeModelContainer container = (NodeModelContainer) propertyChangeEvent.getNewValue();
                    ChangeableItem = container.getPropertyModel();
                    PropertyView.getChildren().clear();
                    PropertyView.getChildren().add(ChangeableItem.getTreePropertyNode(this).getPropertyNode());
                }
                modelTreeInitialize();
                break;
            case "newSelectedItemOnWorkbench":
                NodeModelContainer container = (NodeModelContainer) propertyChangeEvent.getNewValue();
                nodeModelContainer = container;
                ChangeableItem = container.getPropertyModel();
                PropertyView.getChildren().clear();
                PropertyView.getChildren().add(ChangeableItem.getWorkbenchPropertyNode(this).getPropertyNode());

                break;

            default:
                break;
        }
    }

    private void BroadcastReRenderCommand()
    {
        support.firePropertyChange("reRender", null, null);
    }

    /////////////////////////////////////////////////////////////////////////////

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public NodeModelContainer getNodeModelContainer() {
        return nodeModelContainer;
    }

    public void setNodeModelContainer(NodeModelContainer nodeModelContainer ) {
        this.nodeModelContainer = nodeModelContainer;
        //this.ChangeableItem = this.nodeModelContainer.getPropertyModel();
        //isObjectChanged = true;
        //onItemChange();
    }
}
