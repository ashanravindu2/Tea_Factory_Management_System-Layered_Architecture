package lk.captain.model;

import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.FuelBO;

import java.sql.ResultSet;
import java.sql.SQLException;



public class HomeModel {
    FuelBO fuelBO = (FuelBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.FUEL);
    WoodManageModel woodManageModel = new WoodManageModel();

    public double getAvlF() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = fuelBO.getAllAvalable();
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
