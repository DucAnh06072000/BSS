package com.example.BSS.service;

import com.example.BSS.callback.DocumentRepository;
import com.example.BSS.callback.PromotionRepository;
import com.example.BSS.entity.DocumentEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public DocumentEntity uploadFile(MultipartFile file, Long userCode) throws IOException {
        DocumentEntity doc = new DocumentEntity();
        doc.setFileData(file.getBytes());
        doc.setFileName(file.getOriginalFilename());
        doc.setUserCode(userCode);
        documentRepository.save(doc);
        return doc;
    }


    public List<DocumentEntity> getDocument(Long userCode) {
        return documentRepository.findAll().stream().filter(data -> {
            return Objects.equals(data.getUserCode(), userCode);
        }).collect(Collectors.toList());
    }

    public DocumentEntity downloadFileById(Long id) {
        return documentRepository.findById(id).orElseThrow();
    }

}
