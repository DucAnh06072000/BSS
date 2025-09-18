package com.example.BSS.callback;

import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<ServiceEntity, Long> {
}

