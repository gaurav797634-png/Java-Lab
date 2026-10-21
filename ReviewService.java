package com.shopsphere.service;
import com.shopsphere.dao.ReviewDAO;
import com.shopsphere.model.Review;
import java.util.List;
public class ReviewService {
    private final ReviewDAO dao = new ReviewDAO();
    public List<Review> getReviewsForProduct(Long prodId) { return dao.findByProductId(prodId); }
    public boolean hasUserReviewed(Long userId, Long prodId) { return dao.hasUserReviewedProduct(userId, prodId); }
    public Review addReview(Long userId, Long prodId, int rating, String text) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1-5.");
        if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException("Review text required.");
        if (dao.hasUserReviewedProduct(userId, prodId)) throw new IllegalArgumentException("Already reviewed.");
        Review r = new Review(); r.setUserId(userId); r.setProductId(prodId); r.setRating(rating); r.setReviewText(text.trim());
        if (dao.create(r)) return r; throw new IllegalStateException("Failed to submit review.");
    }
    public boolean deleteReview(Long id) { return dao.delete(id); }
    public List<Review> getAllReviewsForAdmin() { return dao.findAllForAdmin(); }
}
