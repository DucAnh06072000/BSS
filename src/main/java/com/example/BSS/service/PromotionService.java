package com.example.BSS.service;

import com.example.BSS.callback.PromotionRepository;
import com.example.BSS.entity.PromotionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<PromotionEntity> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public PromotionEntity insertPromotion(PromotionEntity insert) {
        return promotionRepository.save(insert);
    }

    public Optional<PromotionEntity> updatePromotion(Long id, PromotionEntity update) {
        return promotionRepository.findById(id).map(promotion -> {
            if (promotion.getUserApply() != null) promotion.setUserApply(update.getUserApply());
            if (promotion.getVoucherCode() != null) promotion.setVoucherCode(update.getVoucherCode());
            if (promotion.getTypeCustomer() != null) promotion.setTypeCustomer(update.getTypeCustomer());
            if (promotion.getDescription() != null) promotion.setDescription(update.getDescription());
            if (promotion.getName() != null) promotion.setName(update.getName());
            if (promotion.getDiscountAmount() != 0) promotion.setDiscountAmount(update.getDiscountAmount());
            if (promotion.getTxnMinAmount() != 0) promotion.setTxnMinAmount(update.getTxnMinAmount());
            if (promotion.getExpirationAtVoucher() != null)
                promotion.setExpirationAtVoucher(update.getExpirationAtVoucher());
            return promotionRepository.save(promotion);
        });
    }

    public boolean removePromotion(Long id) {
        if (promotionRepository.existsById(id)) {
            promotionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
