package com.shopsphere.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/* =====================================================================
 * Consolidated model POJOs. Only Product is public (file name matches).
 * All other classes are package-private, still fully usable from other
 * packages via public getters/setters because the *type* is referenced
 * only inside this package + through DAOs that live in the same build.
 * IMPORTANT: If a class outside this package needs the *type name*, keep
 * it public by moving it out — this flat build avoids that.
 * ===================================================================== */

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId, categoryId, stock;
    private String categoryName, name, brand, description, imageUrl;
    private double price, discount;
    private boolean status;
    private Timestamp createdAt;

    public double getDiscountedPrice() { return price - (price * discount / 100.0); }
    public int getProductId() { return productId; } public void setProductId(int v) { productId = v; }
    public int getCategoryId() { return categoryId; } public void setCategoryId(int v) { categoryId = v; }
    public String getCategoryName() { return categoryName; } public void setCategoryName(String v) { categoryName = v; }
    public String getName() { return name; } public void setName(String v) { name = v; }
    public String getBrand() { return brand; } public void setBrand(String v) { brand = v; }
    public String getDescription() { return description; } public void setDescription(String v) { description = v; }
    public double getPrice() { return price; } public void setPrice(double v) { price = v; }
    public double getDiscount() { return discount; } public void setDiscount(double v) { discount = v; }
    public int getStock() { return stock; } public void setStock(int v) { stock = v; }
    public String getImageUrl() { return imageUrl; } public void setImageUrl(String v) { imageUrl = v; }
    public boolean isStatus() { return status; } public void setStatus(boolean v) { status = v; }
    public Timestamp getCreatedAt() { return createdAt; } public void setCreatedAt(Timestamp v) { createdAt = v; }
}

class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId; private String name, email, password, mobile, role;
    private boolean status; private Timestamp createdAt;
    public int getUserId() { return userId; } public void setUserId(int v) { userId = v; }
    public String getName() { return name; } public void setName(String v) { name = v; }
    public String getEmail() { return email; } public void setEmail(String v) { email = v; }
    public String getPassword() { return password; } public void setPassword(String v) { password = v; }
    public String getMobile() { return mobile; } public void setMobile(String v) { mobile = v; }
    public String getRole() { return role; } public void setRole(String v) { role = v; }
    public boolean isStatus() { return status; } public void setStatus(boolean v) { status = v; }
    public Timestamp getCreatedAt() { return createdAt; } public void setCreatedAt(Timestamp v) { createdAt = v; }
}

class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    private int addressId, userId; private String addressLine, city, state, pincode, addressType;
    public int getAddressId() { return addressId; } public void setAddressId(int v) { addressId = v; }
    public int getUserId() { return userId; } public void setUserId(int v) { userId = v; }
    public String getAddressLine() { return addressLine; } public void setAddressLine(String v) { addressLine = v; }
    public String getCity() { return city; } public void setCity(String v) { city = v; }
    public String getState() { return state; } public void setState(String v) { state = v; }
    public String getPincode() { return pincode; } public void setPincode(String v) { pincode = v; }
    public String getAddressType() { return addressType; } public void setAddressType(String v) { addressType = v; }
}

class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private int cartId, productId, quantity, availableStock;
    private String productName, imageUrl; private double price, discount;
    public double getUnitPrice() { return price - (price * discount / 100.0); }
    public double getSubtotal() { return getUnitPrice() * quantity; }
    public int getCartId() { return cartId; } public void setCartId(int v) { cartId = v; }
    public int getProductId() { return productId; } public void setProductId(int v) { productId = v; }
    public String getProductName() { return productName; } public void setProductName(String v) { productName = v; }
    public String getImageUrl() { return imageUrl; } public void setImageUrl(String v) { imageUrl = v; }
    public double getPrice() { return price; } public void setPrice(double v) { price = v; }
    public double getDiscount() { return discount; } public void setDiscount(double v) { discount = v; }
    public int getQuantity() { return quantity; } public void setQuantity(int v) { quantity = v; }
    public int getAvailableStock() { return availableStock; } public void setAvailableStock(int v) { availableStock = v; }
}

class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private int categoryId; private String name, description; private boolean status;
    public int getCategoryId() { return categoryId; } public void setCategoryId(int v) { categoryId = v; }
    public String getName() { return name; } public void setName(String v) { name = v; }
    public String getDescription() { return description; } public void setDescription(String v) { description = v; }
    public boolean isStatus() { return status; } public void setStatus(boolean v) { status = v; }
}

class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;
    private int couponId; private String code, discountType; private double discountValue, minimumOrder;
    private Double maximumDiscount; private Date expiryDate; private boolean status;
    public int getCouponId() { return couponId; } public void setCouponId(int v) { couponId = v; }
    public String getCode() { return code; } public void setCode(String v) { code = v; }
    public String getDiscountType() { return discountType; } public void setDiscountType(String v) { discountType = v; }
    public double getDiscountValue() { return discountValue; } public void setDiscountValue(double v) { discountValue = v; }
    public double getMinimumOrder() { return minimumOrder; } public void setMinimumOrder(double v) { minimumOrder = v; }
    public Double getMaximumDiscount() { return maximumDiscount; } public void setMaximumDiscount(Double v) { maximumDiscount = v; }
    public Date getExpiryDate() { return expiryDate; } public void setExpiryDate(Date v) { expiryDate = v; }
    public boolean isStatus() { return status; } public void setStatus(boolean v) { status = v; }
}

class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId, userId, addressId; private String addressSummary;
    private double totalAmount, discount; private String paymentMethod, paymentStatus, orderStatus;
    private Timestamp createdAt; private List<OrderItem> items = new ArrayList<>();
    public int getOrderId() { return orderId; } public void setOrderId(int v) { orderId = v; }
    public int getUserId() { return userId; } public void setUserId(int v) { userId = v; }
    public int getAddressId() { return addressId; } public void setAddressId(int v) { addressId = v; }
    public String getAddressSummary() { return addressSummary; } public void setAddressSummary(String v) { addressSummary = v; }
    public double getTotalAmount() { return totalAmount; } public void setTotalAmount(double v) { totalAmount = v; }
    public double getDiscount() { return discount; } public void setDiscount(double v) { discount = v; }
    public String getPaymentMethod() { return paymentMethod; } public void setPaymentMethod(String v) { paymentMethod = v; }
    public String getPaymentStatus() { return paymentStatus; } public void setPaymentStatus(String v) { paymentStatus = v; }
    public String getOrderStatus() { return orderStatus; } public void setOrderStatus(String v) { orderStatus = v; }
    public Timestamp getCreatedAt() { return createdAt; } public void setCreatedAt(Timestamp v) { createdAt = v; }
    public List<OrderItem> getItems() { return items; } public void setItems(List<OrderItem> v) { items = v; }
}

class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderItemId, orderId, productId, quantity; private String productName, imageUrl; private double price;
    public double getSubtotal() { return price * quantity; }
    public int getOrderItemId() { return orderItemId; } public void setOrderItemId(int v) { orderItemId = v; }
    public int getOrderId() { return orderId; } public void setOrderId(int v) { orderId = v; }
    public int getProductId() { return productId; } public void setProductId(int v) { productId = v; }
    public String getProductName() { return productName; } public void setProductName(String v) { productName = v; }
    public String getImageUrl() { return imageUrl; } public void setImageUrl(String v) { imageUrl = v; }
    public int getQuantity() { return quantity; } public void setQuantity(int v) { quantity = v; }
    public double getPrice() { return price; } public void setPrice(double v) { price = v; }
}

class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    private int paymentId, orderId; private String paymentMethod, transactionReference, paymentStatus;
    private double amount; private Timestamp createdAt;
    public int getPaymentId() { return paymentId; } public void setPaymentId(int v) { paymentId = v; }
    public int getOrderId() { return orderId; } public void setOrderId(int v) { orderId = v; }
    public String getPaymentMethod() { return paymentMethod; } public void setPaymentMethod(String v) { paymentMethod = v; }
    public String getTransactionReference() { return transactionReference; } public void setTransactionReference(String v) { transactionReference = v; }
    public double getAmount() { return amount; } public void setAmount(double v) { amount = v; }
    public String getPaymentStatus() { return paymentStatus; } public void setPaymentStatus(String v) { paymentStatus = v; }
    public Timestamp getCreatedAt() { return createdAt; } public void setCreatedAt(Timestamp v) { createdAt = v; }
}

class Review implements Serializable {
    private static final long serialVersionUID = 1L;
    private int reviewId, userId, productId, rating; private String userName, reviewText; private Timestamp createdAt;
    public int getReviewId() { return reviewId; } public void setReviewId(int v) { reviewId = v; }
    public int getUserId() { return userId; } public void setUserId(int v) { userId = v; }
    public int getProductId() { return productId; } public void setProductId(int v) { productId = v; }
    public String getUserName() { return userName; } public void setUserName(String v) { userName = v; }
    public int getRating() { return rating; } public void setRating(int v) { rating = v; }
    public String getReviewText() { return reviewText; } public void setReviewText(String v) { reviewText = v; }
    public Timestamp getCreatedAt() { return createdAt; } public void setCreatedAt(Timestamp v) { createdAt = v; }
}

class WishlistItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private int wishlistId, productId; private String productName, imageUrl; private double price, discount;
    public double getDiscountedPrice() { return price - (price * discount / 100.0); }
    public int getWishlistId() { return wishlistId; } public void setWishlistId(int v) { wishlistId = v; }
    public int getProductId() { return productId; } public void setProductId(int v) { productId = v; }
    public String getProductName() { return productName; } public void setProductName(String v) { productName = v; }
    public String getImageUrl() { return imageUrl; } public void setImageUrl(String v) { imageUrl = v; }
    public double getPrice() { return price; } public void setPrice(double v) { price = v; }
    public double getDiscount() { return discount; } public void setDiscount(double v) { discount = v; }
}
