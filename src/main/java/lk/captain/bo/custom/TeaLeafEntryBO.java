package lk.captain.bo.custom;

import lk.captain.bo.SuperBO;
import lk.captain.dao.SQLUtil;
import lk.captain.db.DbConnection;
import lk.captain.dto.TeaLeafEntryDTO;
import lk.captain.entity.TeaLeafEntry;

import java.sql.*;
import java.util.ArrayList;

public interface TeaLeafEntryBO extends SuperBO {
    public boolean updateTeaLeaf(TeaLeafEntryDTO dto) throws SQLException, ClassNotFoundException;

    public boolean tealeafManage(TeaLeafEntryDTO teaLeafEntryDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<TeaLeafEntryDTO> getAll() throws SQLException, ClassNotFoundException;

    public double getNetTotal(String date) throws SQLException, ClassNotFoundException;

    public TeaLeafEntryDTO seacrhTeaLeaf(String id, String date) throws SQLException, ClassNotFoundException;

    public boolean deleteTeaLeaf(String suppId,String date) throws SQLException, ClassNotFoundException;

    public double getReducedTotal(String date) throws SQLException, ClassNotFoundException;

    public double getNetValue(String date1, String date2, String suppId) throws SQLException, ClassNotFoundException;
}