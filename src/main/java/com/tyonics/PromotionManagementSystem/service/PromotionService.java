package com.tyonics.PromotionManagementSystem.service;

import com.tyonics.PromotionManagementSystem.model.Promotion;
import com.tyonics.PromotionManagementSystem.repo.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepo promotionRepo;

    // Create a new promotion
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepo.save(promotion);
    }

    // Retrieve all promotions
    public List<Promotion> getAllPromotions() {
        return promotionRepo.findAll();
    }

    // Retrieve promotion by ID
    public Promotion getPromotionById(int id) {
        Optional<Promotion> promotion = promotionRepo.findById(id);
        return promotion.orElseThrow(() -> new IllegalArgumentException("Promotion not found"));
    }

    // Update a promotion
    public Promotion updatePromotion(int id, Promotion promotionDetails) {
        Promotion promotion = getPromotionById(id);
        promotion.setName(promotionDetails.getName());
        promotion.setStartDate(promotionDetails.getStartDate());
        promotion.setEndDate(promotionDetails.getEndDate());

        if (promotionDetails.getImagePath() != null) {
            promotion.setImagePath(promotionDetails.getImagePath());
        }
        return promotionRepo.save(promotion);
    }

    // Delete a promotion
    public String deletePromotion(int id) {
        Promotion promotion = getPromotionById(id);
        String imagePath = promotion.getImagePath();

        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File("E:/Learn/Java/PromotionManagementSystem/src/main/resources/static" + imagePath);  // Adjust path as necessary
            if (imageFile.exists()) {
                boolean deleted = imageFile.delete();
                if (!deleted) {
                    System.err.println("Failed to delete image: " + imagePath);
                }
            }
        }

        promotionRepo.delete(promotion);
        return "Promotion deleted successfully.";
    }
}