package Main;

import Main.Interfaces.IPropertyChangeble;
import Main.Models.BasicWall;
import Main.Models.HouseProject;
import Main.Models.Level;
import Main.Modules.ItemList.ItemListProvider;
import Main.Modules.ModelNavigator.ModelTreeProvider;
import Main.Modules.ModelNavigator.TreeItemContainer;
import Main.Modules.Workbench.WorkbenchProvider;
import Main.Utils.Aggregators.LevelButtonsAggregator;
import Main.Utils.Aggregators.LevelItemButtonsAggregator;
import Main.Utils.Events.EndWindowInitEvent;
import Main.Utils.Events.EndWindowInitListener;
import Main.Utils.Events.EventContextController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.event.EventListenerList;
import javax.swing.tree.TreeNode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static javafx.geometry.Pos.BASELINE_LEFT;

public class Controller {

    @FXML
    public GridPane MainContainer;

    @FXML
    public TreeView ModelTreeControl;

    @FXML
    public VBox ItemPropertyControl;

    @FXML
    public VBox LevelItemList;

    @FXML
    public ScrollPane ScrollGraphLevelList;

    @FXML
    private Pane MainCanvas;

    @FXML
    private SplitPane MainHorisontalSplitPane;

    @FXML
    private AnchorPane item_1;

    @FXML
    private SplitPane MainVerticalSplitPane;

    @FXML
    private AnchorPane item_2;

    @FXML
    private VBox GraphElementsControl;

    @FXML
    private ScrollPane ScrollGraphItemList;

    @FXML
    private Button button1;

    WorkbenchProvider workbenchProvider;
    ItemListProvider itemListProvider;
    ModelTreeProvider modelTreeProvider;

    public Controller() {
        System.out.println("start");
    }

    @FXML
    private void click(ActionEvent event) {

    }


    public void EndWindowInitProcedure() {
        workbenchProvider = new WorkbenchProvider(MainCanvas);
        itemListProvider = new ItemListProvider(GraphElementsControl, ScrollGraphItemList, LevelItemList, ScrollGraphLevelList );
        modelTreeProvider = new ModelTreeProvider(ModelTreeControl, ItemPropertyControl);

        EventContextController contextController = new EventContextController();
        contextController.setProviders(workbenchProvider, itemListProvider, modelTreeProvider);

    }


}

