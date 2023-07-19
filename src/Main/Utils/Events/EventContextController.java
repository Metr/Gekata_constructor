package Main.Utils.Events;

import Main.Modules.ItemList.ItemListProvider;
import Main.Modules.ModelNavigator.ModelTreeProvider;
import Main.Modules.Workbench.WorkbenchProvider;
import Main.Utils.Aggregators.LevelButtonsAggregator;

public class EventContextController {

    private static EventContextController EventsController;

    private static WorkbenchProvider workbenchProvider;
    private static ItemListProvider itemListProvider;
    private static ModelTreeProvider modelTreeProvider;

    public static synchronized EventContextController getInstance() {
        if (EventsController == null) {
            EventsController = new EventContextController();

        }
        return EventsController;
    }

    public void setProviders(WorkbenchProvider _workbenchProvider, ItemListProvider _itemListProvider, ModelTreeProvider _modelTreeProvider)    {
        workbenchProvider = _workbenchProvider;
        itemListProvider = _itemListProvider;
        modelTreeProvider = _modelTreeProvider;

        itemListProvider.addPropertyChangeListener(workbenchProvider);
        itemListProvider.addPropertyChangeListener(modelTreeProvider);

        workbenchProvider.addPropertyChangeListener(itemListProvider);
        workbenchProvider.addPropertyChangeListener(modelTreeProvider);

        modelTreeProvider.addPropertyChangeListener(workbenchProvider);
        modelTreeProvider.addPropertyChangeListener(itemListProvider);
    }

   public static void RenderAll(){
        workbenchProvider.BroadcastReRenderCommand();
        itemListProvider.BroadcastReRenderCommand();
   }

    public static WorkbenchProvider getWorkbenchProvider() {
        return workbenchProvider;
    }

    public static ItemListProvider getItemListProvider() {
        return itemListProvider;
    }

    public static ModelTreeProvider getModelTreeProvider() {
        return modelTreeProvider;
    }
}
