package com.example.BSS.service;

import com.example.BSS.callback.UserRepository;
import com.example.BSS.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> updateCustomer(Long id, UserEntity updateData) {
        return userRepository.findById(id).map(userEntity -> {
            if (updateData.getName() != null) userEntity.setName(updateData.getName());
            if (updateData.getAddress() != null) userEntity.setAddress(updateData.getAddress());
            if (updateData.getPhone() != null) userEntity.setPhone(updateData.getPhone());
            if (updateData.getSex() != null) userEntity.setSex(updateData.getSex());
            if (updateData.getNation() != null) userEntity.setNation(updateData.getNation());
            if (updateData.getJob() != null) userEntity.setJob(updateData.getJob());
            if (updateData.getRepresentative() != null) userEntity.setRepresentative(updateData.getRepresentative());
            if (updateData.getRepresentativeAddress() != null)
                userEntity.setRepresentativeAddress(updateData.getRepresentativeAddress());
            if (updateData.getRepresentativePhone() != null)
                userEntity.setRepresentativePhone(updateData.getRepresentativePhone());
            userEntity.setUserStatus(updateData.getUserStatus());
            if (updateData.getUserType() != null) userEntity.setUserType(updateData.getUserType());
            if (updateData.getDocumentType() != null) userEntity.setDocumentType(updateData.getDocumentType());
            if (updateData.getDocumentAddress() != null) userEntity.setDocumentAddress(updateData.getDocumentAddress());
            if (updateData.getDocumentCreate() != null) userEntity.setDocumentCreate(updateData.getDocumentCreate());
            if (updateData.getDocumentEnd() != null) userEntity.setDocumentEnd(updateData.getDocumentEnd());
            if (updateData.getTaxCode() != null) userEntity.setTaxCode(updateData.getTaxCode());
            if (updateData.getRole() != null) userEntity.setRole(updateData.getRole());
            userEntity.setUpdateAt(Instant.now());
            return userRepository.save(userEntity);
        });
    }
}
