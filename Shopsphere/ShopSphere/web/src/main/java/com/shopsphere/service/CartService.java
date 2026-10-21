package com.shopsphere.service;
import com.shopsphere.dao.*;
import com.shopsphere.model.*;
import jakarta.servlet.http.HttpSession;
public class CartService {
    private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAO();
    public Cart getCart(HttpSession session, Long userId) {
        if (userId != null && userId > 0) { Cart cart = cartDAO.getOrCreateCartForUser(userId); refresh(cart); return cart; }
        Cart cart = (Cart) session.getAttribute("sessionCart");
        if (cart == null) { cart = new Cart(); session.setAttribute("sessionCart", cart); } else refresh(cart);
        return cart;
    }
    public void addItem(HttpSession session, Long userId, Long prodId, int qty) {
        Product p = productDAO.findById(prodId);
        if (p == null || p.getStatus() != Product.Status.ACTIVE) throw new IllegalArgumentException("Product unavailable.");
        if (p.getStock() < qty) throw new IllegalArgumentException("Only " + p.getStock() + " available.");
        if (userId != null && userId > 0) { Cart c = cartDAO.getOrCreateCartForUser(userId); cartDAO.addItem(c.getId(), prodId, qty); }
        else { Cart c = getCart(session, null); c.addItem(new CartItem(prodId, p, qty)); session.setAttribute("sessionCart", c); }
    }
    public void updateQuantity(HttpSession session, Long userId, Long prodId, int qty) { if (userId != null && userId > 0) { Cart c = cartDAO.getOrCreateCartForUser(userId); cartDAO.updateItemQuantity(c.getId(), prodId, qty); } else { Cart c = getCart(session, null); c.updateQuantity(prodId, qty); session.setAttribute("sessionCart", c); } }
    public void removeItem(HttpSession session, Long userId, Long prodId) { if (userId != null && userId > 0) { Cart c = cartDAO.getOrCreateCartForUser(userId); cartDAO.removeItem(c.getId(), prodId); } else { Cart c = getCart(session, null); c.removeItem(prodId); session.setAttribute("sessionCart", c); } }
    public void clearCart(HttpSession session, Long userId) { if (userId != null && userId > 0) { Cart c = cartDAO.getOrCreateCartForUser(userId); cartDAO.clearCart(c.getId()); } Cart sc = (Cart) session.getAttribute("sessionCart"); if (sc != null) sc.clear(); }
    public void syncCartOnLogin(HttpSession session, Long userId) {
        Cart sc = (Cart) session.getAttribute("sessionCart");
        if (sc != null && !sc.getItems().isEmpty()) { Cart db = cartDAO.getOrCreateCartForUser(userId); for (CartItem it : sc.getItems()) cartDAO.addItem(db.getId(), it.getProductId(), it.getQuantity()); sc.clear(); session.removeAttribute("sessionCart"); }
    }
    private void refresh(Cart cart) { if (cart == null || cart.getItems() == null) return; for (CartItem it : cart.getItems()) { Product f = productDAO.findById(it.getProductId()); if (f != null) it.setProduct(f); } }
}
