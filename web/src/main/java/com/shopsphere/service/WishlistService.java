package com.shopsphere.service;
import com.shopsphere.dao.WishlistDAO;
import com.shopsphere.model.WishlistItem;
import java.util.List;
public class WishlistService {
    private final WishlistDAO dao = new WishlistDAO();
    public List<WishlistItem> getWishlist(Long userId) { return dao.findByUserId(userId); }
    public boolean addToWishlist(Long userId, Long prodId) { return dao.add(userId, prodId); }
    public boolean removeFromWishlist(Long userId, Long prodId) { return dao.remove(userId, prodId); }
    public boolean isProductInWishlist(Long userId, Long prodId) { return dao.isProductInWishlist(userId, prodId); }
}
