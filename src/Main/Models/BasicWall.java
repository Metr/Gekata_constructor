package Main.Models;

import Main.Interfaces.IGraphPrimitive;
import Main.Interfaces.IPropertyChangeble;
import Main.Modules.ModelNavigator.ModelTreeProvider;
import Main.Modules.Workbench.WorkbenchProvider;
import Main.Utils.Containers.NodeModelContainer;
import Main.Utils.Events.EventContextController;
import Main.Utils.Generators.PropertyItemGenerator;
import Main.Utils.MouseGestures;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BasicWall implements IGraphPrimitive, IPropertyChangeble {

    private Point2D startPoint;
    private Point2D endPoint;

    private Line simpleWall;

    private String lastChange;
    private boolean isBeengChanged = false;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private Circle startPointShape, endPointShape;

    private static GregorianCalendar date;

    {
        date = new GregorianCalendar();
    }

    public BasicWall(Point2D startPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
    }

    public BasicWall(double x1, double y1, double x2, double y2) {
        this.startPoint = new Point2D.Double(x1, y1);
        this.endPoint = new Point2D.Double(x2, y2);
        this.startPointShape = new Circle(startPoint.getX(), startPoint.getY(), 0);
        this.endPointShape = new Circle(endPoint.getX(), endPoint.getY(), 0);
        this.simpleWall = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
        this.lastChange = "last change: " + date.getTime().toString().substring(0, 19);
    }


    public void setStartPoint(Point2D startPoint) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.startPoint = startPoint;
        this.startPointShape = new Circle(startPoint.getX(), startPoint.getY(), 0);
        this.simpleWall = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void setStartPoint(double x, double y) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.startPoint = new Point2D.Double(x, y);
        this.simpleWall = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void setEndPoint(Point2D endPoint) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.endPoint = endPoint;
        this.endPointShape = new Circle(endPoint.getX(), endPoint.getY(), 0);
        this.simpleWall = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void setEndPoint(double x, double y) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.startPoint = new Point2D.Double(x, y);
        this.simpleWall = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }


    public double getStartX() {
        return startPoint.getX();
    }

    public double getStartY() {
        return startPoint.getY();
    }

    public double getEndX() {
        return endPoint.getX();
    }

    public double getEndY() {
        return endPoint.getY();
    }

    public void setLine(Line newLine) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.simpleWall = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }


    @Override
    public ArrayList<Point2D> GetPoints() {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        list.add(startPoint);
        list.add(endPoint);
        return list;
    }

    @Override
    public Node GetDrowableElement() {
        Group parent = new Group();
        simpleWall.setOnMousePressed(OnMousePressedEventHandler);
        simpleWall.setOnMouseDragged(OnMouseDraggedEventHandler);
        simpleWall.setOnMouseReleased(OnMouseReleasedEventHandler);
        simpleWall.setStrokeWidth(3);

        //if (EventContextController.getWorkbenchProvider().getSelectedItem().getGraphModel().GetId() == this.simpleWall.getId()) {
            startPointShape.setRadius(5);
            startPointShape.setFill(Color.STEELBLUE);
            startPointShape.setOnMousePressed(OnMousePressedEventHandler);
            startPointShape.setOnMouseDragged(OnMouseDraggedEventHandler);
            startPointShape.setOnMouseReleased(OnMouseReleasedEventHandler);

            endPointShape.setRadius(5);
            endPointShape.setFill(Color.STEELBLUE);
            endPointShape.setOnMousePressed(OnMousePressedEventHandler);
            endPointShape.setOnMouseDragged(OnMouseDraggedEventHandler);
            endPointShape.setOnMouseReleased(OnMouseReleasedEventHandler);
//        }


        parent.getChildren().add(simpleWall);
        parent.getChildren().add(startPointShape);
        parent.getChildren().add(endPointShape);
        return parent;
    }

    @Override
    public NodeModelContainer GetContainer() {
        simpleWall.setOnMousePressed(OnMousePressedEventHandler);
        simpleWall.setOnMouseDragged(OnMouseDraggedEventHandler);
        simpleWall.setOnMouseReleased(OnMouseReleasedEventHandler);
        simpleWall.setStrokeWidth(3);
        NodeModelContainer container = new NodeModelContainer(
                this.getTreePropertyNode(EventContextController.getModelTreeProvider()).getPropertyNode(),
                this, this);
        return container;
    }

    @Override
    public String GetId() {
        return simpleWall.getId();
    }

    @Override
    public NodeModelContainer getTreePropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("point_1 X", String.valueOf(this.getStartX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("point_1 Y", String.valueOf(this.getStartY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("point_2 X", String.valueOf(this.getEndX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyControl("point_2 Y", String.valueOf(this.getEndY()), provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_1 X", String.valueOf(this.getStartX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_1 Y", String.valueOf(this.getStartY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_2 X", String.valueOf(this.getEndX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_2 Y", String.valueOf(this.getEndY()), provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public String getTreeItemName() {
        if (isBeengChanged) {
            date = new GregorianCalendar();
            lastChange = "last change: " + date.getTime().toString().substring(0, 19);
            return "Wall (" + lastChange + ")";
        } else return "Wall";
    }

    @Override
    public void setAllProperties(IPropertyChangeble item) {
        BasicWall wall = (BasicWall) item;

        this.setStartPoint(wall.startPoint);
        this.setEndPoint(wall.endPoint);

    }

    @Override
    public void getPropertiesFromNode(VBox vBox) {
        isBeengChanged = true;

        double x1 = startPoint.getX(), y1 = startPoint.getY(), x2 = endPoint.getX(), y2 = endPoint.getY();

        //по свойствам
        for (Node node : vBox.getChildren()) {
            VBox item = (VBox) node;
            //по контейнерам лейбл+текстбокс
            HBox hBox = (HBox) item.getChildren().get(0);
            for (Node subNode : hBox.getChildren()) {
                if (subNode.getClass() == TextField.class) {
                    TextField text = (TextField) subNode;
                    switch (text.getId()) {
                        case "point_1 X":
                            x1 = Double.valueOf(text.getText());
                            break;
                        case "point_1 Y":
                            y1 = Double.valueOf(text.getText());
                            break;
                        case "point_2 X":
                            x2 = Double.valueOf(text.getText());
                            break;
                        case "point_2 Y":
                            y2 = Double.valueOf(text.getText());
                            break;
                    }
                    break;
                }
            }
        }
        this.setStartPoint(new Point2D.Double(x1, y1));
        this.setEndPoint(new Point2D.Double(x2, y2));

    }

    @Override
    public String dataToString() {
        return "x1=" + startPoint.getX() + " y1=" + startPoint.getY() + ", x2=" + endPoint.getX() + " y2=" + endPoint.getY();
    }


    private EventHandler<MouseEvent> OnMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            if (t.getSource() instanceof Circle) {
                Circle circle = ((Circle) (t.getSource()));
                orgTranslateX = circle.getCenterX();
                orgTranslateY = circle.getCenterY();
            }
            if (t.getSource() instanceof Line) {
                Node p = ((Node) (t.getSource()));
                orgTranslateX = p.getTranslateX();
                orgTranslateY = p.getTranslateY();
            }
        }
    };

    private EventHandler<MouseEvent> OnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            if (t.getSource() instanceof Circle) {
                Circle p = ((Circle) (t.getSource()));
                p.setCenterX(newTranslateX);
                p.setCenterY(newTranslateY);
            } else {
                Node p = ((Node) (t.getSource()));
                p.setTranslateX(newTranslateX);
                p.setTranslateY(newTranslateY);
            }

        }
    };

    private EventHandler<MouseEvent> OnMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            if (t.getSource() instanceof Circle) {
                Circle p = ((Circle) (t.getSource()));
                p.setCenterX(newTranslateX);
                p.setCenterY(newTranslateY);
            }
            if (t.getSource() instanceof Line) {
                {
                    setStartPoint(new Point2D.Double(getStartX() + offsetX, getStartY() + offsetY));
                    setEndPoint(new Point2D.Double(getEndX() + offsetX, getEndY() + offsetY));
                    NodeModelContainer container = GetContainer();
                    EventContextController.getWorkbenchProvider().setSelectedItem(container);

                }
            }
            EventContextController.RenderAll();
        }
    };


}



