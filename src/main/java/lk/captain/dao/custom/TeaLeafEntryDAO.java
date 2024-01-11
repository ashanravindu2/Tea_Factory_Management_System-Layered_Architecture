package lk.captain.dao.custom;

import lk.captain.dao.CrudDAO;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaLeafEntryDTO;
import lk.captain.entity.TeaLeaf;
import lk.captain.entity.TeaLeafEntry;

import java.sql.*;

public interface TeaLeafEntryDAO extends CrudDAO<TeaLeafEntry> {

     double getNetTotal(String date) throws SQLException, ClassNotFoundException;
     double getNetValue(String date1, String date2,String suppId) throws SQLException, ClassNotFoundException;
     TeaLeafEntry seacrhTeaLeaf(String id, String date) throws SQLException, ClassNotFoundException;
     boolean deleteTeaLeaf(String suppId,String date) throws SQLException, ClassNotFoundException;
     double getReducedTotal(String date) throws SQLException, ClassNotFoundException;
}
