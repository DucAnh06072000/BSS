package com.example.BSS.service;

import com.example.BSS.callback.ServicesRepository;
import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.ServiceEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesService {
    private final ServicesRepository servicesRepository;

    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public ServiceEntity getServiceID(String idContract) {
        return servicesRepository.findByIdContract(idContract).orElse(null);
    }

    public List<ServiceEntity> getAllService() {
        return servicesRepository.findAll();
    }
    public List<ServiceEntity> getHistoryService(LocalDate startTime, LocalDate endTime) {
        if (startTime == null || endTime == null || startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Khoảng thời gian không hợp lệ.");
        }
        return servicesRepository.findAll().stream()
                .filter(user -> user.getCreateAt() != null
                        && !user.getCreateAt().isBefore(startTime)
                        && !user.getCreateAt().isAfter(endTime)
                )
                .collect(Collectors.toList());
    }
}
