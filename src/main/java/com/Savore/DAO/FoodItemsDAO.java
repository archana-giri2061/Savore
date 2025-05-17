package com.Savore.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.Savore.config.DbConfig;
import com.Savore.model.FoodItems;

/**
 * DAO class for managing CRUD operations on food_items.
 * 
 * author: 23048573_ArchanaGiri
 */
public class FoodItemsDAO {

    private Connection connection;

    public FoodItemsDAO() throws ClassNotFoundException, SQLException {
        connection = DbConfig.getDbConnection();
    }

    /**
     * Retrieves all food items from the database.
     */
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
            System.out.println("Fetched all food items.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    /**
     * Inserts a new food item into the database.
     */
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
            System.out.println("Inserted food item: " + item.getFoodName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates basic fields of a food item excluding the image.
     */
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
            System.out.println("Updated food item (no image): " + item.getFoodId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a food item and its related order items.
     */
    public void deleteFoodItem(int foodId) throws SQLException, ClassNotFoundException {
        Connection conn = DbConfig.getDbConnection();

        try {
            // Delete from order_items first (foreign key dependency)
            String deleteOrderItems = "DELETE FROM order_items WHERE food_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteOrderItems)) {
                ps.setInt(1, foodId);
                ps.executeUpdate();
            }

            // Delete from food_items
            String deleteFood = "DELETE FROM food_items WHERE food_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteFood)) {
                ps.setInt(1, foodId);
                ps.executeUpdate();
                System.out.println("Deleted food item: " + foodId);
            }

        } finally {
            conn.close();
        }
    }

    /**
     * Retrieves a food item by its ID.
     */
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

    /**
     * Updates a food item, including the image.
     */
    public void updateFood(FoodItems food) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE food_items SET food_name=?, description=?, price=?, country=?, image_url=?, availability=? WHERE food_id=?";
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
            System.out.println("Food item updated with image: " + food.getFoodId());
        }
    }

    /**
     * Retrieves all food items from a specific country (case-insensitive).
     */
    public List<FoodItems> getFoodByCountry(String country) {
        List<FoodItems> foodList = new ArrayList<>();
        String sql = "SELECT * FROM food_items WHERE country LIKE ? AND availability = 'AVAILABLE'";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + country + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FoodItems food = new FoodItems();
                    food.setFoodId(rs.getInt("food_id"));
                    food.setFoodName(rs.getString("food_name"));
                    food.setDescription(rs.getString("description"));
                    food.setPrice(rs.getDouble("price"));
                    food.setCountry(rs.getString("country"));
                    food.setImageUrl(rs.getString("image_url"));
                    food.setAvailability(rs.getString("availability"));
                    foodList.add(food);
                }
                System.out.println("Foods fetched for country: " + country);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    /**
     * Retrieves one available food item per country.
     */
    public List<FoodItems> getOneFoodPerCountry() {
        List<FoodItems> foodList = new ArrayList<>();
        String sql = """
            SELECT fi.*
            FROM food_items fi
            INNER JOIN (
                SELECT country, MIN(food_id) AS min_id
                FROM food_items
                WHERE availability = 'Available'
                GROUP BY country
            ) grouped
            ON fi.country = grouped.country AND fi.food_id = grouped.min_id
        """;

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FoodItems food = new FoodItems();
                food.setFoodId(rs.getInt("food_id"));
                food.setFoodName(rs.getString("food_name"));
                food.setDescription(rs.getString("description"));
                food.setPrice(rs.getDouble("price"));
                food.setCountry(rs.getString("country"));
                food.setImageUrl(rs.getString("image_url"));
                food.setAvailability(rs.getString("availability"));
                foodList.add(food);
            }
            System.out.println("Loaded one food per country.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foodList;
    }

    /**
     * Searches for food items by name and/or country.
     */
    public List<FoodItems> searchFoods(String searchTerm, String country) throws SQLException, ClassNotFoundException {
        List<FoodItems> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM food_items WHERE 1=1");

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" AND LOWER(food_name) LIKE ?");
        }
        if (country != null && !country.trim().isEmpty()) {
            sql.append(" AND LOWER(country) = ?");
        }

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                stmt.setString(index++, "%" + searchTerm.trim().toLowerCase() + "%");
            }
            if (country != null && !country.trim().isEmpty()) {
                stmt.setString(index, country.trim().toLowerCase());
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new FoodItems(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("country"),
                        rs.getString("image_url"),
                        rs.getString("availability")
                ));
            }

            System.out.println("Search completed: " + list.size() + " items found.");
        }

        return list;
    }
}
