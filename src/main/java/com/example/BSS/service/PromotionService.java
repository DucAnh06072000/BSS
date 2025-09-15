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
            if (update.getUserApply() != null) promotion.setUserApply(update.getUserApply());
            if (update.getCount() != 0) promotion.setCount(update.getCount());
            if (update.getVoucherCode() != null) promotion.setVoucherCode(update.getVoucherCode());
            if (update.getTypeCustomer() != null) promotion.setTypeCustomer(update.getTypeCustomer());
            if (update.getDescription() != null) promotion.setDescription(update.getDescription());
            if (update.getName() != null) promotion.setName(update.getName());
            if (update.getDiscountAmount() != 0) promotion.setDiscountAmount(update.getDiscountAmount());
            if (update.getTxnMinAmount() != 0) promotion.setTxnMinAmount(update.getTxnMinAmount());
            if (update.getExpirationAtVoucher() != null) promotion.setExpirationAtVoucher(update.getExpirationAtVoucher());
            if (update.getServiceType() != null) promotion.setServiceType(update.getServiceType());
            return promotionRepository.save(promotion);
        });
    }

    public Optional<PromotionEntity> getDetailPromotion(Long id){
        return promotionRepository.findById(id);
    }

    public boolean removePromotion(Long id) {
        if (promotionRepository.existsById(id)) {
            promotionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
