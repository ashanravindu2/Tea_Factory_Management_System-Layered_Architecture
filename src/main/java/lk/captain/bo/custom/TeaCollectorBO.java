package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.AddCustomerDTO;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.entity.TeaCollector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public interface TeaCollectorBO extends SuperBO {
     boolean save(TeaCollctorDTO dto) throws SQLException, ClassNotFoundException;
      ResultSet generateColecId() throws SQLException, ClassNotFoundException;
    ArrayList<TeaCollctorDTO> getAll() throws SQLException, ClassNotFoundException;
     boolean delete(String id) throws SQLException, ClassNotFoundException;
     boolean update(TeaCollctorDTO dto) throws SQLException, ClassNotFoundException;
     TeaCollctorDTO searchTeaColecId(String id) throws SQLException, ClassNotFoundException;
     int searchCount() throws SQLException, ClassNotFoundException;
}
