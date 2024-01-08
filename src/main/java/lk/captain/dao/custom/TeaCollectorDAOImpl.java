package lk.captain.dao.custom;

import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.entity.AddCustomer;
import lk.captain.entity.TeaCollector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaCollectorDAOImpl implements TeaCollectorDAO{

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute( "DELETE FROM collector WHERE teaColecId = ?", id);
    }

    @Override
    public boolean save(TeaCollector dto) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute("INSERT INTO collector (teaColecId,Name,Address,Telephone,Gender) VALUES(?, ?, ?, ?, ?)",dto.getTeaColecId(),dto.getName(),dto.getAddress(),dto.getTelephone(),dto.getGender()) ;
    }

    @Override
    public boolean update(TeaCollector dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE collector SET Name = ?,Address =? ,Telephone=?,Gender=? WHERE teaColecId= ?",dto.getName(),dto.getAddress(),dto.getTelephone(),dto.getGender(),dto.getTeaColecId());
    }

    @Override
    public TeaCollector search(String id) throws SQLException, ClassNotFoundException {
            ResultSet rst = SQLUtil.execute("SELECT * FROM collector WHERE teaColecId = ?", id);

            if (rst.next()) {
                return new TeaCollector(
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
    public String generateId() throws SQLException, ClassNotFoundException {
    try {
                ResultSet resultSet=SQLUtil.execute("SELECT teaColecId FROM collector ORDER BY teaColecId DESC LIMIT 1");
                boolean isExist = resultSet.next();

                if(isExist){
                    String oldSupId = resultSet.getString(1);
                    oldSupId =oldSupId.substring(1,oldSupId.length());
                    int intId =Integer.parseInt(oldSupId);
                    intId =intId+1;

                    if (intId <10){
                        return "K00" +intId;
                    } else if (intId <100) {
                        return "K0"+intId;

                    }else {
                        return "S"+intId;
                    }
                }else {
                    return "K001";
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public ArrayList<TeaCollector> getAll() throws SQLException, ClassNotFoundException {
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM collector");
            ArrayList<TeaCollector> dtoList = new ArrayList<>();
            while (resultSet.next()) {
                TeaCollector teaCollector = new TeaCollector(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                dtoList.add(teaCollector);

            }
            return dtoList;
        }

    public int searchCount() throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.execute("SELECT COUNT(teaColecId) FROM collector");
        int count = 0;
        if(result.next()){
            count=result.getInt(1);
        }
        return count;
    }
}
