package com.shopsphere.controller;
import com.shopsphere.model.Address;
import com.shopsphere.model.User;
import com.shopsphere.service.AddressService;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
    private UserService userSvc; private AddressService addrSvc; private OrderService ordSvc;
    @Override public void init() { userSvc = new UserService();
