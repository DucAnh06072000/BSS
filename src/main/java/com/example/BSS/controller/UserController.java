package com.example.BSS.controller;

import com.example.BSS.entity.ApiResponse;
import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.ServiceEntity;
import com.example.BSS.entity.UserEntity;
import com.example.BSS.service.ContractService;
import com.example.BSS.service.ExcelService;
import com.example.BSS.service.ServicesService;
import com.example.BSS.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ExcelService excelService;
    private final ContractService contractService;
    private final ServicesService servicesService;

    public UserController(UserService userService, ExcelService excelService, ContractService contractService, ServicesService servicesService) {
        this.userService = userService;
        this.excelService = excelService;
        this.contractService = contractService;
        this.servicesService = servicesService;
    }

    // lấy ra khách hàng cá nhân hoặc doanh nghiệp
    @GetMapping(value = "/getInfoContract", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Long>>> getInfoContract() {
        Long data = userService.getNumberCompany();
        Long personal = userService.getPersonal();
        Map<String, Long> result = new HashMap<>();
        result.put("company", data);
        result.put("personal", personal);
        ApiResponse<Map<String, Long>> response = new ApiResponse<>(200, "Thành công", result);
        return ResponseEntity.ok().body(response);
    }


    // lấy ra khách hàng tạo trong quý này

    @GetMapping(value = "/khachhang/quy-nay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserEntity>>> getUsersInCurrentQuarter() {
        List<UserEntity> users = userService.getUsersInCurrentQuarter();
        if (!users.isEmpty()) {
            ApiResponse<List<UserEntity>> response = new ApiResponse<>(200, "Thành công", users);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error", "No users found in current quarter")
                    .body(new ApiResponse<>(404, "Không tìm thấy user trong quý này", null));
        }
    }


    @GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserEntity>>> getUser() {
        List<UserEntity> user = userService.getAllUsers();
        if (user != null && !user.isEmpty()) {
            ApiResponse<List<UserEntity>> response = new ApiResponse<List<UserEntity>>(200, "Thành công", user);
            return ResponseEntity.ok()
                    .body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error", "No users found")
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<ApiResponse<UserEntity>> updateCustomer(@PathVariable Long id, @RequestBody UserEntity user) {
        Optional<UserEntity> updated = userService.updateCustomer(id, user);
        if (updated.isPresent()) {
            ApiResponse<UserEntity> response =
                    new ApiResponse<>(200, "Cập nhật thành công", updated.get());
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error", "No users found")
                    .body(new ApiResponse<>(404, "Lỗi không update thành công", null));
        }
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<UserEntity>> getCustomerById(@PathVariable Long id) {
        Optional<UserEntity> customer = userService.getCustomerById(id);

        if (customer.isPresent()) {
            ApiResponse<UserEntity> response =
                    new ApiResponse<>(200, "Thành công", customer.get());
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Không tìm thấy khách hàng", null));
        }
    }


    @GetMapping("/export-users")
    public ResponseEntity<byte[]> exportUsers() throws IOException {
        List<UserEntity> userEntities = userService.getAllUsers();
        List<ContractEntity> contractEntities = contractService.getAllUser();
        List<ServiceEntity> serviceEntities = servicesService.getAllService();
        ByteArrayInputStream in = excelService.exportUserToUsers(serviceEntities,userEntities,contractEntities);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }


}
