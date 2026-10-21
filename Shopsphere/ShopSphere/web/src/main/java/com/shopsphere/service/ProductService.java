package com.shopsphere.service;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.Product;
import java.math.BigDecimal;
import java.util.List;
public class ProductService {
    private final ProductDAO dao = new ProductDAO();
    public Product getProductById(Long id) { return dao.findById(id); }
    public List<Product> getFilteredProducts(Integer catId, String key, BigDecimal minP, BigDecimal maxP, String sort, int page, int size) { int limit = size > 0 ? size : 12; int offset = page > 1 ? (page - 1) * limit : 0; return dao.findFiltered(catId, key, minP, maxP, sort, offset, limit); }
    public int countFilteredProducts(Integer catId, String key, BigDecimal minP, BigDecimal maxP) { return dao.countFiltered(catId, key, minP, maxP); }
    public List<Product> getFeaturedProducts(int limit) { return dao.findFeatured(limit); }
    public List<Product> getRelatedProducts(Long id, Integer catId, int limit) { return dao.findRelated(id, catId, limit); }
    public List<Product> getAllProductsForAdmin() { return dao.findAllForAdmin(); }
    public boolean createProduct(Product p) { return dao.create(p); }
    public boolean updateProduct(Product p) { return dao.update(p); }
    public boolean deleteProduct(Long id) { return dao.delete(id); }
    public int countTotalProducts() { return dao.countTotalProducts(); }
}
