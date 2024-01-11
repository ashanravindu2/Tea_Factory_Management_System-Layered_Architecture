package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaTypeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TeaTypeBO extends SuperBO {

    boolean teatypeSave(TeaTypeDTO teaTypeDTO) throws SQLException, ClassNotFoundException;
    ArrayList<TeaTypeDTO> loadAllTeaTypes() throws SQLException, ClassNotFoundException;
    boolean deleteTeaType(String teaTypeId) throws SQLException, ClassNotFoundException;
    TeaTypeDTO serchOnTeaType(String id) throws SQLException, ClassNotFoundException;
    boolean updateTeaType(TeaTypeDTO dto) throws SQLException, ClassNotFoundException;
}
