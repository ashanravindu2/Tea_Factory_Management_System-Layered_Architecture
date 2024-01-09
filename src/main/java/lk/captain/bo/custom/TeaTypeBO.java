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
    public boolean teatypeSave(TeaTypeDTO teaTypeDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<TeaTypeDTO> loadAllTeaTypes() throws SQLException, ClassNotFoundException;

    public boolean deleteTeaType(String teaTypeId) throws SQLException, ClassNotFoundException;

    public TeaTypeDTO serchOnTeaType(String id) throws SQLException, ClassNotFoundException;

    public boolean updateTeaType(TeaTypeDTO dto) throws SQLException, ClassNotFoundException;
}
