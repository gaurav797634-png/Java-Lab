package com.shopsphere.service;
import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.User;
import com.shopsphere.util.PasswordUtil;
import java.util.*;
import java.util.regex.Pattern;
public class UserService {
    private final UserDAO userDAO = new UserDAO();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    public User registerCustomer(String name, String email, String phone, String password) {
        if (name == null || name.trim().length() < 2) throw new IllegalArgumentException("Full name must be at least 2 characters.");
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) throw new IllegalArgumentException("Invalid email address.");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("Password must be at least 6 characters.");
        String normEmail = email.trim().toLowerCase();
        if (userDAO.emailExists(normEmail)) throw new IllegalArgumentException("Account with this email already exists.");
        User u = new User();
        u.setName(name.trim()); u.setEmail(normEmail); u.setPhone(phone != null ? phone.trim() : null);
        u.setPasswordHash(PasswordUtil.hashPassword(password)); u.setRole(User.Role.CUSTOMER); u.setStatus(User.Status.ACTIVE);
        if (userDAO.create(u)) return u;
        throw new IllegalStateException("Failed to register user.");
    }
    public User authenticate(String email, String plainPassword) {
        if (email == null || plainPassword == null || email.trim().isEmpty()) return null;
        User u = userDAO.findByEmail(email.trim().toLowerCase());
        if (u == null) return null;
        if (u.getStatus() != User.Status.ACTIVE) throw new IllegalStateException("Account suspended.");
        return PasswordUtil.checkPassword(plainPassword, u.getPasswordHash()) ? u : null;
    }
    public User getUserById(Long id) { return userDAO.findById(id); }
    public boolean updateProfile(Long userId, String name, String phone) { User u = userDAO.findById(userId); if (u == null) return false; u.setName(name.trim()); u.setPhone(phone); return userDAO.updateProfile(u); }
    public boolean changePassword(Long userId, String current, String next) { User u = userDAO.findById(userId); if (u == null) return false; if (!PasswordUtil.checkPassword(current, u.getPasswordHash())) throw new IllegalArgumentException("Current password incorrect."); return userDAO.updatePassword(userId, PasswordUtil.hashPassword(next)); }
    public List<User> getAllUsers() { return userDAO.findAll(); }
    public boolean toggleUserStatus(Long userId) { User u = userDAO.findById(userId); if (u == null) return false; return userDAO.updateStatus(userId, u.getStatus() == User.Status.ACTIVE ? User.Status.SUSPENDED : User.Status.ACTIVE); }
    public int countCustomers() { return userDAO.countTotalUsers(); }
}
