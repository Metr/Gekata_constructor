package Main.Models;

import Main.Interfaces.IGraphPrimitive;
import Main.Interfaces.IInterestSpot;
import Main.Interfaces.ISpecialSpot;
import Main.Utils.Containers.NodeModelContainer;
import javafx.scene.Node;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InterestPoint implements IInterestSpot, ISpecialSpot, IGraphPrimitive {

    private int x_pos;
    private int y_pos;
    private double radius;
    private String description;

    public InterestPoint(int x_pos, int y_pos, double radius) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.radius = radius;
    }

    @Override
    public Map<String, Object> GetDescription() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("description", description);
        return null;
    }

    @Override
    public void SetDescription(Map<String, Object> map) {
        if(map.containsKey("description"))
            description = map.get("description").toString();
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public int getX() {
        return x_pos;
    }

    @Override
    public int getY() {
        return y_pos;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public ArrayList<Point2D> GetPoints() {
        return null;
    }

    @Override
    public Node GetDrowableElement() {
        return null;
    }

    @Override
    public NodeModelContainer GetContainer() {
        return null;
    }

    @Override
    public String GetId() {
        return "aaaaaaaaaaaa";
    }
}
