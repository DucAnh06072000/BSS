package com.example.BSS.controller;

import com.example.BSS.entity.ApiResponse;
import com.example.BSS.entity.DocumentEntity;
import com.example.BSS.entity.PromotionEntity;
import com.example.BSS.service.DocumentService;
import com.example.BSS.service.PromotionService;
import com.example.BSS.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class DocumentController {


    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
    private final DocumentService documentService;
    private final UserService userService;


    public DocumentController(DocumentService documentService, UserService userService) {
        this.documentService = documentService;
        this.userService = userService;
    }

    // api này dùng để test postman thêm dữ liệu vào
    @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userCode") String userCode) {
        try {
            DocumentEntity document = documentService.uploadFile(file, userCode);
            ApiResponse<String> response = new ApiResponse<String>(200, "upload File thành công", document.getFileName());
            return ResponseEntity.ok()
                    .body(response);
        } catch (IOException e) {
            ApiResponse<String> response = new ApiResponse<String>(404, "upload thất bại", e.getMessage());
            return ResponseEntity.ok()
                    .body(response);
        }
    }


    // api lấy toàn bộ các file truyền userCode ở trong bảng File
    @PostMapping(value = "/getDocument", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<DocumentEntity>>> getDocument(@RequestParam("userCode") Long userCode) {
        List<DocumentEntity> data = documentService.getDocument(userService.getIdUser(userCode));
        if (data != null) {
            ApiResponse<List<DocumentEntity>> response = new ApiResponse<List<DocumentEntity>>(200, "Thành công", data);
            return ResponseEntity.ok()
                    .body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Không tìm thấy user", null));
        }
    }

    //api download
    //id là truyền vào id của bảng file
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        DocumentEntity doc = documentService.downloadFileById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"").body(doc.getFileData());
    }
}
