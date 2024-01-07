package lk.captain.model;

import lk.captain.db.DbConnection;
import lk.captain.dto.TeaPowderDTO;
import lk.captain.dto.TeaPowderGetDTO;
import lk.captain.dto.tm.CartTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeaPowderModel {

    public static boolean placeOrder(TeaPowderDTO dto) throws SQLException {
        boolean result = false;
        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSave = saveOrder(dto.getTmList());

            if (isSave) {
                boolean isWareHouseUpdate = wareHouseUpdate(dto.getTmList());
                if (isWareHouseUpdate) {
                    connection.commit();
                    result = true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    public static boolean saveOrder(List<CartTM> tmList) throws SQLException {
        for (CartTM cartTM : tmList) {
            if (!saveOrder(cartTM)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveOrder(CartTM cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO teepowder VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, cartTm.getDate());
        pstm.setDouble(2, cartTm.getUseTea());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateTeaPowder(List<CartTM> tmList) throws SQLException {
        for (CartTM cartTM : tmList) {
            if (!updateTeaPowder(cartTM)) {
                return false;
            }
        }
        return true;
    }

    public static boolean updateTeaPowder(CartTM cartTM) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE teepowder SET useTea=useTea+ ? WHERE date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1, cartTM.getUseTea());
        pstm.setString(2, cartTM.getDate());
        return pstm.executeUpdate() > 0;
    }


    public double searchDate(String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT useTea FROM teepowder WHERE date= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDate(1, Date.valueOf(String.valueOf(date)));
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            double useTea = resultSet.getDouble("useTea");
            return useTea;
        }

        return 0;
    }

    public static boolean updateTeaStock(TeaPowderDTO dto) throws SQLException {
        boolean result = false;
        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isUpdates = updateTeaPowder(dto.getTmList());

            if (isUpdates) {
                boolean isWareHouseUpdate = wareHouseUpdate(dto.getTmList());
                if (isWareHouseUpdate) {
                    connection.commit();
                    result = true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }


    public static boolean wareHouseUpdate(CartTM cartTM) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String warehousseId = "W001";

        String sql = "UPDATE warehouse SET stockCount=stockCount+ ? WHERE wareHouseId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1, cartTM.getUseTea());
        pstm.setString(2, warehousseId);
        return pstm.executeUpdate() > 0;

    }

    public static boolean wareHouseUpdate(List<CartTM> tmList) throws SQLException {
        for (CartTM cartTM : tmList) {
            if (!wareHouseUpdate(cartTM)) {
                return false;
            }
        }
        return true;
    }

    public List<TeaPowderGetDTO> load() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM teepowder";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TeaPowderGetDTO> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new TeaPowderGetDTO(
                    resultSet.getString(1),
                    resultSet.getDouble(2)
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deletTeaStock(String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM teepowder WHERE date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date);

        return pstm.executeUpdate() > 0;
    }


}