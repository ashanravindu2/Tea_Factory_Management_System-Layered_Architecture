package lk.captain.model;

import lk.captain.Db.DbConnection;
import lk.captain.dto.TeaTypeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaTypeModel {
    public boolean teatypeSave(TeaTypeDTO teaTypeDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tea_types VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, teaTypeDTO.getTeaTypeId());
        pstm.setString(2, teaTypeDTO.getTeaTypeName());
        pstm.setString(3, teaTypeDTO.getTeaTypeDesc());
        pstm.setDouble(4, teaTypeDTO.getTeaPerPrice());

        return pstm.executeUpdate() > 0;
    }

    public List<TeaTypeDTO> loadAllTeaTypes() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_types";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TeaTypeDTO> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new TeaTypeDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)

            );

            dtoList.add(dto);
        }

        return dtoList;
    }
    public boolean deleteTeaType(String teaTypeId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM tea_types WHERE teaTypeId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, teaTypeId);

        return pstm.executeUpdate() > 0;
    }
    public TeaTypeDTO serchOnTeaType(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_types WHERE teaTypeId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        TeaTypeDTO dto = null;

        if(resultSet.next()) {
            String teaTypeId = resultSet.getString(1);
            String teaTypeName = resultSet.getString(2);
            String teaTypeDesc = resultSet.getString(3);
            double teaPerPrice = resultSet.getDouble(4);

            dto = new TeaTypeDTO(teaTypeId, teaTypeName, teaTypeDesc, teaPerPrice);
        }

        return dto;
    }

    public boolean updateTeaType(TeaTypeDTO dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_types SET teaTypeName = ?, teaTypeDesc = ? ,teaPerPrice =? WHERE teaTypeId= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getTeaTypeName());
        pstm.setString(2, dto.getTeaTypeDesc());
        pstm.setDouble(3, dto.getTeaPerPrice());
        pstm.setString(4, dto.getTeaTypeId());

        return pstm.executeUpdate() > 0;
    }
}

