package com.example.BSS.controller;

import com.example.BSS.entity.ApiResponse;
import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.DocumentEntity;
import com.example.BSS.entity.ServiceEntity;
import com.example.BSS.service.ContractService;
import com.example.BSS.service.ServicesService;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ServiceController {

    private final ServicesService servicesService;
    private final ContractService contractService;

    public ServiceController(ServicesService servicesService, ContractService contractService) {
        this.servicesService = servicesService;
        this.contractService = contractService;
    }

    // đầu vào formatTime = yyyy-MM-DD
    // truyền startTime, endTime
    //api tìm kiếm lịch sử giao dịch
    @GetMapping(value = "/getHistoryService", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ServiceEntity>>> getHistory(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        LocalDate startTimeStr = LocalDate.parse(startTime);
        LocalDate endTimeStr = LocalDate.parse(endTime);
        List<ServiceEntity> service = servicesService.getHistoryService(startTimeStr, endTimeStr);
        if (service != null) {
            ApiResponse<List<ServiceEntity>> response = new ApiResponse<List<ServiceEntity>>(200, "Thành công", service);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, "Không tìm thấy user", new ArrayList<>()));
        }
    }
}
