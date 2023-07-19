package Main.Interfaces;

import Main.Utils.Containers.NodeModelContainer;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface IGraphPrimitive {

    ArrayList<Point2D> GetPoints();

    Node GetDrowableElement();

    NodeModelContainer GetContainer();

    String GetId();

}
