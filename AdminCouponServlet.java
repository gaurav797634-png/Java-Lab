package com.shopsphere.controller;
import com.shopsphere.model.Coupon;
import com.shopsphere.service.CouponService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
@WebServlet(name = "AdminCouponServlet", urlPatterns = {"/admin/coupons"})
public class AdminCouponServlet extends HttpServlet {
    private CouponService coupSvc;
    @Override public void init() { coupSvc = new CouponService(); }
    @Override
