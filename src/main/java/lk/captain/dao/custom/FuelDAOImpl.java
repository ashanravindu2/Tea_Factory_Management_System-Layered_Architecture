package lk.captain.dao.custom;

import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.entity.Fuel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuelDAOImpl implements FuelDAO{
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
        return null;
    }

    @Override
    public ArrayList<Fuel> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
