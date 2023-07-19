package Main.Models;

import Main.Interfaces.IGraphPrimitive;
import Main.Interfaces.ISpecialSpot;
import Main.Utils.Containers.NodeModelContainer;
import javafx.scene.Node;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class WayPoint implements ISpecialSpot, IGraphPrimitive {

    private int x_pos;
    private int y_pos;
    private double radius;
    private int goto_level;


    public WayPoint(int x_pos, int y_pos, double radius, int goto_level) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.radius = radius;
        this.goto_level = goto_level;
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

    public int getGoto_level() {
        return goto_level;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setGoto_level(int goto_level) {
        this.goto_level = goto_level;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
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
        return "bbbbbbbbbbbbbbbbbbbbbbbbbb";
    }
}
