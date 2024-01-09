package lk.captain.model;

import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.FuelBO;
import lk.captain.bo.custom.WoodBO;

import java.sql.ResultSet;
import java.sql.SQLException;



public class HomeModel {
    FuelBO fuelBO = (FuelBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.FUEL);
    WoodBO woodBO = (WoodBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WOOD);

    public double getAvlF() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = fuelBO.getAllAvalable();
        double liter = 0;

        if (resultSet.next()){
            liter=resultSet.getDouble(2);
        }
        return liter;
    }
    public double getAvlW() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = woodBO.getAllAvalable();
        double weight = 0;

        if (resultSet.next()){
            weight=resultSet.getDouble(2);
        }
       return weight;
    }


}
