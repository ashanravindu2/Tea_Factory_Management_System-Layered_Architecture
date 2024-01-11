package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.FuelDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.entity.Fuel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuelDAOImpl implements FuelDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
          return SQLUtil.execute( "UPDATE matirials SET bCategory = NULL, bLeter = NULL WHERE barrelId = ? ", id);

    }

    @Override
    public boolean save(Fuel dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO matirials(barrelId,bCategory,bLeter) VALUES (?,?,?)",
                dto.getBarrelId(),
                dto.getBCategory(),
                dto.getBLeter());
    }

    @Override
    public boolean update(Fuel dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE matirials SET bCategory = ?, bLeter = ? WHERE barrelId= ?"
                ,dto.getBCategory(),
                dto.getBLeter(),
                dto.getBarrelId());

    }

    @Override
    public Fuel search(String id) throws SQLException, ClassNotFoundException {
       ResultSet resultSet= SQLUtil.execute("select matirials.barrelId,bCategory,bLeter FROM matirials where barrelId= ? ",id);
         if(resultSet.next()){
              return new Fuel(
                     resultSet.getString(1),
                     resultSet.getString(2),
                     resultSet.getDouble(3)
              );
         }
            return null;

    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT barrelId FROM matirials ORDER BY barrelId DESC LIMIT 1");
    }

    @Override
    public ArrayList<Fuel> getAll() throws SQLException, ClassNotFoundException {

       ResultSet resultSet= SQLUtil.execute( "SELECT matirials.barrelId, bCategory, bLeter\n" +
                    "FROM matirials\n" +
                    "WHERE  bCategory IS NOT NULL\n" +
                    "  AND bLeter IS NOT NULL;");
        ArrayList<Fuel> allFuel = new ArrayList<>();
        while (resultSet.next()) {
            Fuel fuel = new Fuel(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
            allFuel.add(fuel);
        }
        return allFuel;


    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public boolean usedUpdateFuel(String id, double liter) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE matirials SET bLeter = bLeter-? WHERE barrelId= ?",liter,id);
    }

    @Override
    public ResultSet getAllAvalable() throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("select COUNT(bCategory)AS COUNTS,SUM(bLeter) AS LETER FROM matirials WHERE bCategory IS NOT NULL  AND bLeter IS NOT NULL");
    }


}
