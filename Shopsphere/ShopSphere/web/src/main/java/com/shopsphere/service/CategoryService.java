package com.shopsphere.service;
import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.model.Category;
import java.util.List;
public class CategoryService {
    private final CategoryDAO dao = new CategoryDAO();
    public List<Category> getActiveCategories() { return dao.findAllActive(); }
    public List<Category> getAllCategoriesWithCount() { return dao.findAllWithProductCount(); }
    public Category getCategoryById(Integer id) { return dao.findById(id); }
    public Category getCategoryBySlug(String slug) { return dao.findBySlug(slug); }
    public boolean createCategory(String name, String desc, String img) { String slug = name.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-"); Category c = new Category(); c.setName(name.trim()); c.setSlug(slug); c.setDescription(desc); c.setImageUrl(img); c.setStatus(Category.Status.ACTIVE); return dao.create(c); }
    public boolean updateCategory(Integer id, String name, String desc, String img, String stat) { Category c = dao.findById(id); if (c == null) return false; c.setName(name.trim()); c.setSlug(name.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-")); c.setDescription(desc); c.setImageUrl(img); if (stat != null) c.setStatus(Category.Status.valueOf(stat.toUpperCase())); return dao.update(c); }
    public boolean deleteCategory(Integer id) { return dao.delete(id); }
    public int countTotalCategories() { return dao.countTotalCategories(); }
}
