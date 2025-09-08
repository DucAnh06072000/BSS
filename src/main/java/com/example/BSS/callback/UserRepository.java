package com.example.BSS.callback;

import com.example.BSS.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByPhone(String phone);

    List<UserEntity> findByNameContainingIgnoreCase(String name);

}
