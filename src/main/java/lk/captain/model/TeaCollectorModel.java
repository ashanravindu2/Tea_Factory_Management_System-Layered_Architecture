package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.TeaCollctorDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaCollectorModel {

    public boolean teacollctorManage(TeaCollctorDTO teaCollctorDTO)throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO collector (teaColecId,Name,Address,Telephone,Gender) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,teaCollctorDTO.getTeaColecId());
        pstm.setString(2,teaCollctorDTO.getName());
        pstm.setString(3,teaCollctorDTO.getAddress());
        pstm.setString(4,teaCollctorDTO.getTelephone());
        pstm.setString(5,teaCollctorDTO.getGender());

        return pstm.executeUpdate() > 0;
    }

    public String generateColecId()throws SQLException{
        Connection connection =DbConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT teaColecId FROM collector ORDER BY teaColecId DESC LIMIT 1");
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

    public List<TeaCollctorDTO> getAllCollector() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM collector";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<TeaCollctorDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String teaColecId = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Telephone = resultSet.getString(4);
            String Gender =resultSet.getString(5);

            var dto = new TeaCollctorDTO(teaColecId, Name, Address, Telephone,Gender);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public boolean deletecollector(String colecId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM collector WHERE teaColecId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, colecId);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateteaColector(TeaCollctorDTO dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE collector SET Name = ?,Address =? ,Telephone=?,Gender=? WHERE teaColecId= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTelephone());
        pstm.setString(4, dto.getGender());
        pstm.setString(5, dto.getTeaColecId());


        return pstm.executeUpdate() > 0;
    }
    public TeaCollctorDTO searchTeaColecId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM collector WHERE teaColecId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        TeaCollctorDTO dto = null;

        if(resultSet.next()) {

            String teaColecId = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Telephone = resultSet.getString(4);
            String Gender =resultSet.getString(5);

            dto = new TeaCollctorDTO(teaColecId, Name, Address, Telephone,Gender);
        }

        return dto;
    }
    public int searchCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(teaColecId) FROM collector";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet result = pstm.executeQuery();
        int count = 0;
                if(result.next()){
                    count=result.getInt(1);
                }
        return count;
    }

}
