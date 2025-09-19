package com.example.BSS.controller;

import com.example.BSS.entity.ApiResponse;
import com.example.BSS.entity.PromotionEntity;
import com.example.BSS.entity.UserEntity;
import com.example.BSS.service.PromotionService;
import com.example.BSS.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PromotionController {

    private static final Logger log = LoggerFactory.getLogger(PromotionController.class);
    private final PromotionService promotionService;
    private final UserService userService;

    public PromotionController(PromotionService promotionService, UserService userService) {
        this.promotionService = promotionService;
        this.userService = userService;
    }

    @GetMapping(value = "/getPromotion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PromotionEntity>>> getUser() {
        List<PromotionEntity> user = promotionService.getAllPromotions();
        if (user != null && !user.isEmpty()) {
            ApiResponse<List<PromotionEntity>> response = new ApiResponse<List<PromotionEntity>>(200, "Thành công", user);
            return ResponseEntity.ok()
                    .body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error", "No users found")
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }


    // chi tiết ưu đãi
    @PostMapping(value = "/getDetailPromotion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDetailPromotion(@PathVariable Long id) {
        Optional<PromotionEntity> detailPromotion = promotionService.getDetailPromotion(id);
        if (detailPromotion.isPresent()) {
            List<UserEntity> data = userService.getUserOfPhone(detailPromotion.get().getUserApply());
            Map<String, Object> result = new HashMap<>();
            result.put("promotion", detailPromotion.get());
            result.put("userEntity", data);
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(200, "Thành công", result);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Không tìm thấy khách hàng", null));
        }

    }

    // Thêm thông tin ưu đãi
//    {
//        "voucher_code": "HIBANMOI",
//            "user_apply": "0348067792,0348557759",
//            "count": 100,
//            "name": "Ưu đãi cho khách hàng gia hạn hợp đồng",
//            "description": "Giảm 20% tối đa 100k cho khách hàng gia hạn hợp đồng",
//            "status": 1,
//            "txn_min_amount": 100000,
//            "discount_amount":100000,
//            "expiration_at_voucher":"2025-09-26 11:58:38",
//            "type_customer":""
//    }
    // các trường bắt buộc voucherCode, Description, name,DiscountAmount, TxnMinAmount,ExpirationAtVoucher
    // các trường bắt buộc voucherCode, Description, name,DiscountAmount, TxnMinAmount,ExpirationAtVoucher
    @PostMapping(value = "/insertPromotion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<PromotionEntity>> insertPromotion(@RequestBody PromotionEntity promotion) {
        // Kiểm tra dữ liệu đầu vào
        if (promotion.getVoucherCode() == null || promotion.getVoucherCode().trim().isEmpty() ||
            promotion.getDescription() == null || promotion.getDescription().trim().isEmpty() ||
            promotion.getName() == null || promotion.getName().trim().isEmpty() ||
            promotion.getDiscountAmount() == null || promotion.getDiscountAmount() <= 0 ||
            promotion.getTxnMinAmount() == null || promotion.getTxnMinAmount() <= 0 ||
            promotion.getExpirationAtVoucher() == null || promotion.getExpirationAtVoucher().trim().isEmpty()) {

            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Vui lòng nhập đầy đủ và hợp lệ thông tin", null));
        }

        // Insert
        PromotionEntity insert = promotionService.insertPromotion(promotion);

        if (insert != null) {
            ApiResponse<PromotionEntity> response = new ApiResponse<>(200, "Thêm promotion thành công", insert);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(500, "Không thể thêm promotion", null));
        }
    }


    //api update thông tin ưu đãi
    @PostMapping(value = "/updatePromotion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PromotionEntity>>> updatePromotion(@PathVariable Long id, @RequestBody PromotionEntity promotion) {
        Optional<PromotionEntity> update = promotionService.updatePromotion(id, promotion);
        List<PromotionEntity> user = promotionService.getAllPromotions();
        if (user != null) {
            ApiResponse<List<PromotionEntity>> response = new ApiResponse<List<PromotionEntity>>(200, "Thành công", user);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error", "No users found")
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }

    @PostMapping(value = "/detailPromotionOfUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserEntity>>> getDetailUserPromotion(@RequestParam("user_apply") String userApply) {
        List<UserEntity> data = userService.getUserOfPhone(userApply);
        if (data != null) {
            ApiResponse<List<UserEntity>> response = new ApiResponse<List<UserEntity>>(200, "Thành công", data);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }

    // api xoá ưu đãi
    @DeleteMapping(value = "/removePromotion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PromotionEntity>>> removePromotion(@PathVariable Long id) {
        boolean update = promotionService.removePromotion(id);
        if (update) {
            List<PromotionEntity> user = promotionService.getAllPromotions();
            if (user != null) {
                ApiResponse<List<PromotionEntity>> response = new ApiResponse<List<PromotionEntity>>(200, "Thành công", user);
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Error", "No users found")
                        .body(new ApiResponse<>(404, "Không tìm thấy user", null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }

}
