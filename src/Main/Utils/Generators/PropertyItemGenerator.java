package Main.Utils.Generators;

import Main.Modules.ModelNavigator.ModelTreeProvider;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PropertyItemGenerator {

    public static VBox generateTreePropertyControl(String label, String startValue, ModelTreeProvider provider)
    {
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem;
        propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);

        Label l = new Label(label);
        l.setMaxWidth(100);
        l.setMinWidth(100);
        l.setAlignment(Pos.CENTER_LEFT);
        propertyItem.getChildren().add(l);

        TextField f = new TextField(startValue);
        f.setMaxWidth(200);
        f.setMinWidth(200);
        f.setAlignment(Pos.CENTER_LEFT);
        f.setId(label);
        f.setOnKeyReleased(event -> {
            provider.onTreeItemChange();
        });

        propertyItem.getChildren().add(f);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }


    public static VBox generateWorkbenchPropertyControl(String label, String startValue, ModelTreeProvider provider)
    {
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem;
        propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);

        Label l = new Label(label);
        l.setMaxWidth(100);
        l.setMinWidth(100);
        l.setAlignment(Pos.CENTER_LEFT);
        propertyItem.getChildren().add(l);


        TextField f = new TextField(startValue);
        f.setMaxWidth(200);
        f.setMinWidth(200);
        f.setAlignment(Pos.CENTER_LEFT);
        f.setId(label);
        f.setOnKeyReleased(event -> {
            provider.onWorkbenchItemChange();
        });

        propertyItem.getChildren().add(f);

        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }


}
