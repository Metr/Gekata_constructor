package Main.Models;

import javax.swing.tree.TreeNode;

public class HouseProject {

    private static HouseProject houseProject;
    private Building building;


    public static synchronized HouseProject getInstance() {
        if (houseProject == null) {
            houseProject = new HouseProject();
            houseProject.building = new Building();
        }
        return houseProject;
    }



    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}


