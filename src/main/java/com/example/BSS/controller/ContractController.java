package com.example.BSS.controller;

import com.example.BSS.entity.ApiResponse;
import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.DocumentEntity;
import com.example.BSS.service.ContractService;
import com.example.BSS.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ContractController {

    private final ContractService contractService;
    private final DocumentService documentService;

    public ContractController(ContractService contractService, DocumentService documentService) {
        this.contractService = contractService;
        this.documentService = documentService;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error", "No users found")
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }

    // đầu vào formatTime = yyyy-MM-DD
    @GetMapping(value = "/getListContractWarning", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ContractEntity>>> getListContractWarning(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        LocalDate startTimeStr = LocalDate.parse(startTime);
        LocalDate endTimeStr = LocalDate.parse(endTime);
        List<ContractEntity> data = contractService.getContractsExpiredInRange(startTimeStr, endTimeStr);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error", "No users found")
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }


}
