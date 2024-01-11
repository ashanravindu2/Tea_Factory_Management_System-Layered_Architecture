package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.TeaTypeDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaTypeDTO;
import lk.captain.entity.TeaType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaTypeDAOImpl implements TeaTypeDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM tea_types WHERE teaTypeId = ?", id);
    }

    @Override
    public boolean save(TeaType dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO tea_types VALUES(?, ?, ?, ?)",
                dto.getTeaTypeId(), dto.getTeaTypeName(), dto.getTeaTypeDesc(), dto.getTeaPerPrice());
    }

    @Override
    public boolean update(TeaType dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE tea_types SET teaTypeName = ?, teaTypeDesc = ? ,teaPerPrice =? WHERE teaTypeId= ?",
                dto.getTeaTypeName(), dto.getTeaTypeDesc(), dto.getTeaPerPrice(), dto.getTeaTypeId());
    }

    @Override
    public TeaType search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM tea_types WHERE teaTypeId = ?", id);
        if (rst.next()) {
            return new TeaType(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<TeaType> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM tea_types");
        ArrayList<TeaType> teaTypes = new ArrayList<>();
        while (rst.next()) {
            teaTypes.add(new TeaType(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
        return teaTypes;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
