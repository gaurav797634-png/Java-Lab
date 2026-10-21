package com.shopsphere.service;
import com.shopsphere.dao.*;
import com.shopsphere.model.*;
import jakarta.servlet.http.HttpSession;
import java.math.*;
import java.util.*;
public class CheckoutService {
    private final OrderDAO orderDAO = new OrderDAO(); private final AddressDAO addressDAO = new AddressDAO(); private final ProductDAO productDAO = new ProductDAO(); private final CartService cartService = new CartService(); private final CouponService couponService =
