package Main.Models;

import Main.Interfaces.IGraphPrimitive;
import Main.Interfaces.IPropertyChangeble;
import Main.Modules.ModelNavigator.ModelTreeProvider;
import Main.Utils.Containers.NodeModelContainer;
import Main.Utils.Generators.PropertyItemGenerator;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.plaf.synth.Region;
import java.util.ArrayList;

import static javafx.geometry.Pos.BASELINE_LEFT;

public class Level implements IPropertyChangeble {

    private ArrayList<IGraphPrimitive> Walls;
    private String Name;

    public Level() {
        this.Name = "unknown level " + HouseProject.getInstance().getBuilding().getLevels().size();
        this.Walls = new ArrayList<IGraphPrimitive>();
//        for (int i = 0; i < 30; i += 2)
//            this.Walls.add(new BasicWall(i, i, i + 100, i));
    }

    public Level(String name) {
        this.Name = name;
        this.Walls = new ArrayList<IGraphPrimitive>();
//        for (int i = 0; i < 300; i += 10)
//            this.Walls.add(new BasicWall(i, i, i + 100, i));
    }


    public Node getLevelNode() {
        Pane pane = new Pane();
        for (IGraphPrimitive wall : Walls) {
            pane.getChildren().add(wall.GetDrowableElement());
        }
        return pane;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<IGraphPrimitive> getWalls() {
        return Walls;
    }

    public void setWalls(ArrayList<IGraphPrimitive> walls) {
        Walls = walls;
    }

    @Override
    public NodeModelContainer getTreePropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("name", this.Name, provider));
        return new NodeModelContainer(resultContainer, this, null);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        return null;
    }



    @Override
    public String getTreeItemName() {
        return Name;
    }

    @Override
    public void setAllProperties(IPropertyChangeble item) {
        Level level = (Level) item;
        this.Name = level.Name;

    }

    @Override
    public void getPropertiesFromNode(VBox vBox) {

        for (Node node : vBox.getChildren()) {
            VBox item = (VBox) node;

            HBox hBox = (HBox) item.getChildren().get(0);
            for (Node label : hBox.getChildren()) {
                if (label.getClass() == TextField.class) {
                    TextField textField = (TextField) label;
                    this.Name = textField.getText();
                }
            }
        }

    }

    @Override
    public String dataToString() {
        return "name = " + this.Name;
    }

}
