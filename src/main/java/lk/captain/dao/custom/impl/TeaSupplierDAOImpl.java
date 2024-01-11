package lk.captain.dao.custom.impl;

import lk.captain.dao.SQLUtil;
import lk.captain.dao.custom.TeaSupplierDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.SupplierManageDTO;
import lk.captain.entity.TeaSupplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaSupplierDAOImpl implements TeaSupplierDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM suppliers WHERE supplierId = ?", id);
    }

    @Override
    public boolean save(TeaSupplier dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO suppliers(supplierId,suppName,suppAddres,suppTele,suppGen) VALUES (?,?,?,?,?)"
                ,dto.getSupplierId(),dto.getSuppName(),dto.getSuppAddres(),dto.getSuppTele(),dto.getSuppGen());
    }

    @Override
    public boolean update(TeaSupplier dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE suppliers SET suppName = ?,suppAddres =? ,suppTele=?,suppGen=? WHERE supplierId= ?"
                ,dto.getSuppName(),dto.getSuppAddres(),dto.getSuppTele(),dto.getSuppGen(),dto.getSupplierId());
    }

    @Override
    public TeaSupplier search(String id) throws SQLException, ClassNotFoundException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM suppliers WHERE supplierId = ?", id);
        if (rst.next()) {
            return new TeaSupplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public ResultSet generateId() throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("SELECT supplierId FROM suppliers ORDER BY supplierId DESC LIMIT 1");

    }

    @Override
    public ArrayList<TeaSupplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM suppliers");
        ArrayList<TeaSupplier> suppliers = new ArrayList<>();
        while (rst.next()) {
            suppliers.add(new TeaSupplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));

        }
        return suppliers;
    }

    @Override
    public int searchCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}

