package com.shopsphere.service;
import com.shopsphere.dao.CouponDAO;
import com.shopsphere.model.Coupon;
import java.math.BigDecimal;
import java.util.List;
public class CouponService {
    private final CouponDAO dao = new CouponDAO();
    public Coupon validateCoupon(String code, BigDecimal subtotal) {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("Coupon required.");
        Coupon c = dao.findByCode(code.trim().toUpperCase());
        if (c == null) throw new IllegalArgumentException("Invalid coupon code.");
        if (!c.isValid()) throw new IllegalArgumentException("Coupon expired or limit reached.");
        if (subtotal != null && c.getMinOrderAmount() != null && subtotal.compareTo(c.getMinOrderAmount()) < 0) throw new IllegalArgumentException("Minimum order not met.");
        return c;
    }
    public List<Coupon> getAllCoupons() { return dao.findAll(); }
    public boolean createCoupon(Coupon c) { return dao.create(c); }
    public boolean toggleCouponStatus(Integer id) { Coupon c = dao.findById(id); if (c == null) return false; return dao.updateStatus(id, c.getStatus() == Coupon.Status.ACTIVE ? Coupon.Status.DISABLED : Coupon.Status.ACTIVE); }
    public boolean deleteCoupon(Integer id) { return dao.delete(id); }
}
