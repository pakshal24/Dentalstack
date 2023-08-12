package com.dentalstack.auth.demo.Repository;

import com.dentalstack.auth.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}