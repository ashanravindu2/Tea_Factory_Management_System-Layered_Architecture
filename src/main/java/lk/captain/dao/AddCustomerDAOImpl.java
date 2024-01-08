package lk.captain.dao;

import lk.captain.db.DbConnection;
import lk.captain.dto.AddCustomerDTO;
import lk.captain.entity.AddCustomer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddCustomerDAOImpl implements AddCustomerDAO{

    @Override
    public boolean delete(String cusIds) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cusId = ?", cusIds);
    }

    @Override
    public boolean save(AddCustomer dto) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("INSERT INTO customer (cusId,cusName,cusTele,cusAddress) VALUES(?, ?, ?, ?)",dto.getCusId(),dto.getCusName(),dto.getCusTele(),dto.getCusAddress());
    }

    @Override
    public boolean updateCustomer(AddCustomer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET cusName=?,cusTele=?,cusAddress=? WHERE cusId=?",dto.getCusName(),dto.getCusTele(),dto.getCusAddress(),dto.getCusId());
    }

    @Override
    public AddCustomer searchCusId(String id) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE cusId=?", id);

        if (rst.next()) {
            return new AddCustomer(
            rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    @Override
    public String generateCusId() throws SQLException, ClassNotFoundException {
        try {
                ResultSet resultSet=SQLUtil.execute("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1");
                boolean isExist = resultSet.next();

                if(isExist){
                    String oldCusId = resultSet.getString(1);
                    oldCusId =oldCusId.substring(1,oldCusId.length());
                    int intId =Integer.parseInt(oldCusId);
                    intId =intId+1;

                    if (intId <10){
                        return "C00" +intId;
                    } else if (intId <100) {
                        return "C0"+intId;

                    }else {
                        return "C"+intId;
                    }
                }else {
                    return "C001";
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public ArrayList<AddCustomer> getAllCus() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM customer");
        ArrayList<AddCustomer> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            AddCustomer addCustomer = new AddCustomer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
         dtoList.add(addCustomer);
        }
        return dtoList;
    }

}
