
package com.example.BSS.callback;
import com.example.BSS.entity.PromotionEntity;
import com.example.BSS.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {
    List<PromotionEntity> findByName(String name);

}

