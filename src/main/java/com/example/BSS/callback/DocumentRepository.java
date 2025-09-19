package com.example.BSS.callback;

import com.example.BSS.entity.DocumentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
  List<DocumentEntity> findByUserCode(String userCode);
}
