package com.example.BSS.service;

import com.example.BSS.callback.ContractRepository;
import com.example.BSS.entity.ContractEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {
    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<ContractEntity> getAllUser() {
        return contractRepository.findAll();
    }

    // lấy ra hợp đồng sắp hết hạn
    public List<ContractEntity> getUsersNearExpiration() {
        LocalDate now = LocalDate.now();
        LocalDate threshold = now.plus(30, ChronoUnit.DAYS);
        return getAllUser().stream()
                .filter(user -> user.getExpiredDate() != null
                        && !user.getExpiredDate().isBefore(now)
                        && user.getExpiredDate().isBefore(threshold))
                .collect(Collectors.toList());
    }

    public List<ContractEntity> getContractsExpiredInRange(LocalDate startTime, LocalDate endTime) {
        if (startTime == null || endTime == null || startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Khoảng thời gian không hợp lệ.");
        }
        return getAllUser().stream()
                .filter(user -> user.getExpiredDate() != null
                        && !user.getExpiredDate().isBefore(startTime) // Ngày hết hạn không trước startTime
                        && !user.getExpiredDate().isAfter(endTime)) // Ngày hết hạn không sau endTime
                .collect(Collectors.toList());
    }

}
