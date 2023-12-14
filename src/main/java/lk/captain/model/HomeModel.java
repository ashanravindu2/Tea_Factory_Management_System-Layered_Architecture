package lk.captain.model;

import java.sql.ResultSet;
import java.sql.SQLException;



public class HomeModel {

    FuelManageModel fuelManageModel = new FuelManageModel();
    WoodManageModel woodManageModel = new WoodManageModel();

    public double getAvlF() throws SQLException {
        ResultSet resultSet = fuelManageModel.getAllAvalable();
        double liter = 0;

        if (resultSet.next()){
            liter=resultSet.getDouble(2);
        }
        return liter;
    }
    public double getAvlW() throws SQLException {
        ResultSet resultSet = woodManageModel.getAllAvalable();
        double weight = 0;

        if (resultSet.next()){
            weight=resultSet.getDouble(2);
        }
       return weight;
    }


}
