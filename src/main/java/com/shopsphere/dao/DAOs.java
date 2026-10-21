package com.shopsphere.dao;

import com.shopsphere.model.*;
import com.shopsphere.util.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* =====================================================================
 * Consolidated DAOs. One public class (DAOs = container) + nested static
 * classes for UserDAO, ProductDAO, etc. Call as DAOs.Product or
 * new DAOs.ProductDAO(). All queries preserved from original files.
 * ===================================================================== */public final class DAOs {

    /* ------------------------------- USER ------------------------------ */
    public static class UserDAO {
        public User findByEmail(String email) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE email=?")) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
        public User findById(int id) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE user_id=?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
        public int insert(User u) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO users(name,email,password,mobile,role,status) VALUES(?,?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, u.getName()); ps.setString(2, u.getEmail());
                ps.setString(3, u.getPassword()); ps.setString(4, u.getMobile());
                ps.setString(5, u.getRole() == null ? "CUSTOMER" : u.getRole());
                ps.setBoolean(6, true);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : -1; }
            }
        }
        public List<User> findAll() throws SQLException {
            List<User> list = new ArrayList<>();
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT * FROM users ORDER BY user_id DESC");
                 ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            return list;
        }
        private User map(ResultSet rs) throws SQLException {
            User u = new User();
            u.setUserId(rs.getInt("user_id")); u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email")); u.setPassword(rs.getString("password"));
            u.setMobile(rs.getString("mobile")); u.setRole(rs.getString("role"));
            u.setStatus(rs.getBoolean("status")); u.setCreatedAt(rs.getTimestamp("created_at"));
            return u;
        }
    }

    /* ------------------------------ PRODUCT ---------------------------- */
    public static class ProductDAO {
        public List<Product> search(String kw, Integer cat, String sort) throws SQLException {
            StringBuilder sql = new StringBuilder(
                "SELECT p.*, c.name AS cat_name FROM products p JOIN categories c ON p.category_id=c.category_id WHERE p.status=1");
            List<Object> params = new ArrayList<>();
            if (kw != null && !kw.isBlank()) {
                sql.append(" AND (p.name LIKE ? OR p.brand LIKE ? OR p.description LIKE ?)");
                String like = "%" + kw.trim() + "%"; params.add(like); params.add(like); params.add(like);
            }
            if (cat != null && cat > 0) { sql.append(" AND p.category_id=?"); params.add(cat); }
            String order = "p.product_id DESC";
            if ("price_asc".equals(sort)) order = "p.price ASC";
            else if ("price_desc".equals(sort)) order = "p.price DESC";
            else if ("name_asc".equals(sort)) order = "p.name ASC";
            else if ("discount".equals(sort)) order = "p.discount DESC";
            sql.append(" ORDER BY ").append(order);
            List<Product> list = new ArrayList<>();
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
                try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            }
            return list;
        }
        public Product findById(int id) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "SELECT p.*, c.name AS cat_name FROM products p JOIN categories c ON p.category_id=c.category_id WHERE p.product_id=?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
        public List<Product> findAll() throws SQLException { return query(null, null, null); }
        public List<Product> findLowStock(int threshold) throws SQLException {
            List<Product> list = new ArrayList<>();
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "SELECT p.*, c.name AS cat_name FROM products p JOIN categories c ON p.category_id=c.category_id WHERE p.stock<=? ORDER BY p.stock")) {
                ps.setInt(1, threshold);
                try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            }
            return list;
        }
        private List<Product> query(String kw, Integer cat, String sort) throws SQLException {
            List<Product> list = new ArrayList<>();
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "SELECT p.*, c.name AS cat_name FROM products p JOIN categories c ON p.category_id=c.category_id ORDER BY p.product_id DESC");
                 ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            return list;
        }
        public int insert(Product p) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO products(category_id,name,brand,description,price,discount,stock,image_url,status) VALUES(?,?,?,?,?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, p.getCategoryId()); ps.setString(2, p.getName());
                ps.setString(3, p.getBrand()); ps.setString(4, p.getDescription());
                ps.setDouble(5, p.getPrice()); ps.setDouble(6, p.getDiscount());
                ps.setInt(7, p.getStock()); ps.setString(8, p.getImageUrl());
                ps.setBoolean(9, p.isStatus());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : -1; }
            }
        }
        public void update(Product p) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "UPDATE products SET category_id=?,name=?,brand=?,description=?,price=?,discount=?,stock=?,image_url=?,status=? WHERE product_id=?")) {
                ps.setInt(1, p.getCategoryId()); ps.setString(2, p.getName());
                ps.setString(3, p.getBrand()); ps.setString(4, p.getDescription());
                ps.setDouble(5, p.getPrice()); ps.setDouble(6, p.getDiscount());
                ps.setInt(7, p.getStock()); ps.setString(8, p.getImageUrl());
                ps.setBoolean(9, p.isStatus()); ps.setInt(10, p.getProductId());
                ps.executeUpdate();
            }
        }
        public void updateStock(int productId, int delta) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("UPDATE products SET stock=stock-? WHERE product_id=?")) {
                ps.setInt(1, delta); ps.setInt(2, productId); ps.executeUpdate();
            }
        }
        public void delete(int id) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("DELETE FROM products WHERE product_id=?")) {
                ps.setInt(1, id); ps.executeUpdate();
            }
        }
        public int countAll() throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM products");
                 ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getInt(1) : 0; }
        }
        private Product map(ResultSet rs) throws SQLException {
            Product p = new Product();
            p.setProductId(rs.getInt("product_id")); p.setCategoryId(rs.getInt("category_id"));
            try { p.setCategoryName(rs.getString("cat_name")); } catch (SQLException ignored) {}
            p.setName(rs.getString("name")); p.setBrand(rs.getString("brand"));
            p.setDescription(rs.getString("description")); p.setPrice(rs.getDouble("price"));
            p.setDiscount(rs.getDouble("discount")); p.setStock(rs.getInt("stock"));
            p.setImageUrl(rs.getString("image_url")); p.setStatus(rs.getBoolean("status"));
            p.setCreatedAt(rs.getTimestamp("created_at")); return p;
        }
    }

    /* ----------------------------- CATEGORY ---------------------------- */
    public static class CategoryDAO {
        public List<Category> findAll() throws SQLException { return list("WHERE status=1 ORDER BY name"); }
        public List<Category> findAllIncludingInactive() throws SQLException { return list("ORDER BY name"); }
        private List<Category> list(String where) throws SQLException {
            List<Category> list = new ArrayList<>();
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT * FROM categories " + where);
                 ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            return list;
        }
        public Category findById(int id) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT * FROM categories WHERE category_id=?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
        public int insert(Category cat) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO categories(name,description,status) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, cat.getName()); ps.setString(2, cat.getDescription()); ps.setBoolean(3, cat.isStatus());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : -1; }
            }
        }
        public void update(Category cat) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(
                     "UPDATE categories SET name=?,description=?,status=? WHERE category_id=?")) {
                ps.setString(1, cat.getName()); ps.setString(2, cat.getDescription());
                ps.setBoolean(3, cat.isStatus()); ps.setInt(4, cat.getCategoryId()); ps.executeUpdate();
            }
        }
        public void delete(int id) throws SQLException {
            try (Connection c = Utils.dataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement("DELETE FROM categories WHERE category_id=?")) {
                ps.setInt(1, id); ps.executeUpdate();
            }
        }
        private Category map(ResultSet rs) throws SQLException {
            Category cat = new Category();
            cat.setCategoryId(rs.getInt("category_id")); cat.setName(rs.getString("name"));
            cat.setDescription(rs.getString("description")); cat.setStatus(rs.getBoolean("status"));
            return cat;
        }
    }

    /* ------------------------------- CART ------------------------------
