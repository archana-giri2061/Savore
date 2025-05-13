
package com.Savore.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.Savore.config.DbConfig;
import com.Savore.model.FoodItems;

public class FoodItemsDAO {
    
    private Connection connection;

    public FoodItemsDAO() throws ClassNotFoundException, SQLException {
        connection = DbConfig.getDbConnection();
    }

    public List<FoodItems> getAllFoodItems() {
        List<FoodItems> foodList = new ArrayList<>();
        String sql = "SELECT food_id, food_name, description, price, country, image_url, availability FROM food_items";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FoodItems item = new FoodItems();
                item.setFoodId(rs.getInt("food_id"));
                item.setFoodName(rs.getString("food_name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setCountry(rs.getString("country"));
                item.setImageUrl(rs.getString("image_url"));
                item.setAvailability(rs.getString("availability"));
                foodList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    public void insertFoodItem(FoodItems item) {
        String sql = "INSERT INTO food_items (food_name, description, price, country, image_url, availability) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, item.getFoodName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getCountry());
            ps.setString(5, item.getImageUrl());
            ps.setString(6, item.getAvailability());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFoodItem(FoodItems item) {
        String sql = "UPDATE food_items SET food_name = ?, description = ?, price = ?, country = ?, availability = ? WHERE food_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, item.getFoodName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getCountry());
            ps.setString(5, item.getAvailability());
            ps.setInt(6, item.getFoodId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFoodItem(int foodId) {
        String sql = "DELETE FROM food_items WHERE food_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FoodItems getFoodById(int foodId) {
        FoodItems item = null;
        String sql = "SELECT * FROM food_items WHERE food_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = new FoodItems();
                item.setFoodId(rs.getInt("food_id"));
                item.setFoodName(rs.getString("food_name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setCountry(rs.getString("country"));
                item.setImageUrl(rs.getString("image_url"));
                item.setAvailability(rs.getString("availability"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
    public void updateFood(FoodItems food) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE food SET food_name=?, description=?, price=?, country=?, image_url=?, availability=? WHERE food_id=?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, food.getFoodName());
            stmt.setString(2, food.getDescription());
            stmt.setDouble(3, food.getPrice());
            stmt.setString(4, food.getCountry());
            stmt.setString(5, food.getImageUrl());
            stmt.setString(6, food.getAvailability());
            stmt.setInt(7, food.getFoodId());
            stmt.executeUpdate();
        }
    }

}
