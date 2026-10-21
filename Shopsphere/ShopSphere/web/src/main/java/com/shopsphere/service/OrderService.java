package com.shopsphere.service;
import com.shopsphere.dao.OrderDAO;
import com.shopsphere.model.Order;
import java.math.BigDecimal;
import java.util.List;
public class OrderService {
    private final OrderDAO dao = new OrderDAO();
    public Order getOrderById(Long id) { return dao.findById(id); }
    public Order getOrderByNumber(String num) { return dao.findByOrderNumber(num); }
    public List<Order> getOrdersByUser(Long userId) { return dao.findByUserId(userId); }
    public List<Order> getAllOrdersForAdmin() { return dao.findAllForAdmin(); }
    public boolean updateOrderStatus(Long orderId, Order.OrderStatus status) { return dao.updateOrderStatus(orderId, status); }
    public boolean updatePaymentStatus(Long orderId, Order.PaymentStatus status) { return dao.updatePaymentStatus(orderId, status); }
    public int countTotalOrders() { return dao.countTotalOrders(); }
    public BigDecimal getTotalRevenue() { return dao.calculateTotalRevenue(); }
}
