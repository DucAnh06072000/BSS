package com.example.BSS.service;

import com.example.BSS.callback.UserRepository;
import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.PromotionEntity;
import com.example.BSS.entity.ServiceEntity;
import com.example.BSS.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserEntity> getFullUsersOfService(List<ServiceEntity> listService, List<ContractEntity> listContract) {
        List<UserEntity> result = new ArrayList<>();
        for (UserEntity user : getAllUsers()) {
            for (ContractEntity contract : listContract)
                if (contract.getUserCode().equals(user.getIdUser())) {
                    for (ServiceEntity service : listService)
                        if (contract.getIdContract().equals(service.getIdContract())) {
                            user.setExpirationAt(contract.getExpiredDate());
                            user.setServiceCode(service.getServiceName());
                            result.add(user);
                        }
                }

        }
        return result;
    }

    public Optional<UserEntity> getCustomerById(Long id) {
        return userRepository.findById(id);
    }


    public Long getNumberCompany() {
        List<UserEntity> user = getAllUsers();
        return user.stream().filter(data -> {
            if (data.getUserType() == null) {
                return false;
            } else {
                return data.getUserType().equals("1");
            }
        }).count();
    }

    public Long getPersonal() {
        List<UserEntity> user = getAllUsers();
        return user.stream().filter(data -> {
            if (data.getUserType() == null) {
                return false;
            } else {
                return data.getUserType().equals("0");
            }
        }).count();
    }


    public List<UserEntity> getUsersInCurrentQuarter() {
        LocalDate today = LocalDate.now();

        int currentQuarter = (today.getMonthValue() - 1) / 3 + 1;
        int currentYear = today.getYear();

        // Ngày bắt đầu quý
        int startMonth = (currentQuarter - 1) * 3 + 1;
        LocalDate startDate = LocalDate.of(currentYear, startMonth, 1);

        // Ngày kết thúc quý
        int endMonth = startMonth + 2;
        LocalDate endDate = LocalDate.of(currentYear, endMonth,
                YearMonth.of(currentYear, endMonth).lengthOfMonth());

        return getAllUsers().stream()
                .filter(user -> user.getCreateAt() != null &&
                        !user.getCreateAt().isBefore(startDate) &&
                        !user.getCreateAt().isAfter(endDate))
                .collect(Collectors.toList());
    }

    // lấy ra khách hàng sắp hết hạn
    public List<UserEntity> getUsersNearExpiration() {
        LocalDate now = LocalDate.now();
        LocalDate threshold = now.plus(30, ChronoUnit.DAYS);
        return getAllUsers().stream()
                .filter(user -> user.getExpirationAt() != null
                        && !user.getExpirationAt().isBefore(now)
                        && user.getExpirationAt().isBefore(threshold))
                .collect(Collectors.toList());
    }

    public List<UserEntity> getUserOfPhone(String userApply) {
        List<String> phones = Arrays.stream(userApply.split(","))
                .map(String::trim)
                .toList();
        return getAllUsers().stream()
                .filter(userEntity -> phones.contains(userEntity.getPhone()))
                .toList();
    }

    public String getIdUser(Long id) {
        for (UserEntity user : getAllUsers()) {
            if (Objects.equals(user.getId(), id)) {
                return user.getIdUser();
            }
        }
        return null;
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
            if (updateData.getBirthday() != null) userEntity.setBirthday(updateData.getBirthday());
            if (updateData.getPlkhCode() != null) userEntity.setPlkhCode(updateData.getPlkhCode());
            if (updateData.getPlaceResidence() != null) userEntity.setPlaceResidence(updateData.getPlaceResidence());
            if (updateData.getMaTb() != null) userEntity.setMaTb(updateData.getMaTb());
            userEntity.setUpdateAt(LocalDate.now());
            return userRepository.save(userEntity);
        });
    }
}
