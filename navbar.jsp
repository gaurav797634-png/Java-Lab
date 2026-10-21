<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="site-header"><div class="container">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="brand-logo"><div class="brand-icon">S</div>Shop<span>Sphere</span></a>
        <form class="search-form" action="${pageContext.request.contextPath}/products" method="get">
            <input type="text" name="q" class="search-input" placeholder
