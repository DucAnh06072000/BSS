package com.example.BSS.controller;

import com.example.BSS.entity.ApiResponse;
import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.DocumentEntity;
import com.example.BSS.entity.ServiceEntity;
import com.example.BSS.service.ContractService;
import com.example.BSS.service.DocumentService;
import com.example.BSS.service.ServicesService;
import com.example.BSS.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ContractController {

    private final ContractService contractService;
    private final DocumentService documentService;
    private final ServicesService servicesService;
    private final UserService userService;


    public ContractController(ContractService contractService, DocumentService documentService, ServicesService servicesService, UserService userService) {
        this.contractService = contractService;
        this.documentService = documentService;
        this.servicesService = servicesService;
        this.userService = userService;
    }

    //lấy ra khách hàng sắp hết hạn
//    {
//        "code": 200,
//            "message": "Thành công",
//            "data": [
//        {
//            "id": 1,
//                "user_code": "M1",
//                "file_code": "2",
//                "create_at": "2025-09-16T00:00:00Z",
//                "update_at": null,
//                "user_created": "Trương Thị Dụ",
//                "effective_date": "2025-09-20T00:00:00Z",
//                "contract_type": "1",
//                "expired_date": "2025-09-20"
//        }
//    ]
//    }
    @GetMapping(value = "/getUserWarning", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ContractEntity>>> getUserWaring() {
        List<ContractEntity> data = contractService.getUsersNearExpiration();
        if (data != null && !data.isEmpty()) {
            ApiResponse<List<ContractEntity>> response = new ApiResponse<List<ContractEntity>>(200, "Thành công", data);
            return ResponseEntity.ok()
                    .body(response);
        } else {
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, "Không tìm thấy user", new ArrayList<>()));
        }
    }

    //api lấy ra dịch vụ tham gia
    // Truyền userCode
    @GetMapping(value = "/getService", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ContractEntity>>> getService(@RequestParam("userCode") Long id) {
        String userCode = userService.getIdUser(id);
        List<ContractEntity> listContract = contractService.getContractByUserCode(userCode);
        if (listContract == null || listContract.isEmpty()) {
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, "Không tìm thấy hợp đồng", new ArrayList<>()));
        }

        List<ContractEntity> contractWithService = listContract.stream()
                .peek(contractEntity -> {
                    ServiceEntity service = servicesService.getServiceID(contractEntity.getIdContract());
                    contractEntity.setService(service);
                })
                .toList();

        ApiResponse<List<ContractEntity>> response =
                new ApiResponse<>(200, "Thành công", contractWithService);

        return ResponseEntity.ok(response);
    }


    // đầu vào formatTime = yyyy-MM-DD
    // truyền userCode, startTime, endTime
    // cảnh báo hết hạn hợp đồng
    @GetMapping(value = "/getListContractWarning", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ContractEntity>>> getListContractWarning(@RequestParam("user_code") Long id, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        String userCode = userService.getIdUser(id);
        LocalDate startTimeStr = LocalDate.parse(startTime);
        LocalDate endTimeStr = LocalDate.parse(endTime);
        List<ContractEntity> data = contractService.getContractsExpiredInRange(userCode, startTimeStr, endTimeStr);
        List<ContractEntity> contractAnDocumentList = data.stream().map(contractEntity -> {
            DocumentEntity document = documentService.getDocument(contractEntity.getUserCode()).get(0);
            contractEntity.setDocument(document);
            return contractEntity;
        }).toList();
        if (contractAnDocumentList != null) {
            ApiResponse<List<ContractEntity>> response = new ApiResponse<List<ContractEntity>>(200, "Thành công", contractAnDocumentList);
            return ResponseEntity.ok()
                    .body(response);
        } else {
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, "Không tìm thấy user", new ArrayList<>()));
        }
    }


}
