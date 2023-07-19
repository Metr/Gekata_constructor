package Main.Interfaces;

import Main.Modules.ModelNavigator.ModelTreeProvider;
import Main.Utils.Containers.NodeModelContainer;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.Map;

public interface IPropertyChangeble {

    NodeModelContainer getTreePropertyNode(ModelTreeProvider provider);

    NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider);

    String getTreeItemName();

    void setAllProperties(IPropertyChangeble item);

    void getPropertiesFromNode(VBox vBox);

    String dataToString();

}
