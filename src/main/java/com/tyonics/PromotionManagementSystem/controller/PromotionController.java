package com.tyonics.PromotionManagementSystem.controller;

import com.tyonics.PromotionManagementSystem.model.Promotion;
import com.tyonics.PromotionManagementSystem.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ResourceLoader resourceLoader;

    // Create a new promotion
    @PostMapping("/promotions")
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        return promotionService.createPromotion(promotion);
    }


    @PostMapping("/promotions2")
    public ResponseEntity<Promotion> createPromotion2(@ModelAttribute Promotion promotion, @RequestPart("image")MultipartFile image) {
        try {
            String imagePath = saveImage(image);
            promotion.setImagePath(imagePath);
            Promotion createdPromotion = promotionService.createPromotion(promotion);
            return ResponseEntity.ok(createdPromotion);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String folderPath = "E:/Learn/Java/PromotionManagementSystem/src/main/resources/static/public";
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File file = new File(directory, uniqueFileName);
        image.transferTo(file);
        return  "/content/" + uniqueFileName;
    }

    // Get all promotions
    @GetMapping("/promotions")
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    // Get promotion by ID
    @GetMapping("/promotions/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable int id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    // Update a promotion
    @PutMapping("/promotions/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable int id, @ModelAttribute Promotion promotion, @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                promotion.setImagePath(imagePath);
            }
            Promotion updatedPromotion = promotionService.updatePromotion(id, promotion);
            return ResponseEntity.ok(updatedPromotion);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update promotion: " + e.getMessage());
        }
    }

    // Delete a promotion
    @DeleteMapping("/promotions/{id}")
    public String deletePromotion(@PathVariable int id) {
        return promotionService.deletePromotion(id);
    }
}
