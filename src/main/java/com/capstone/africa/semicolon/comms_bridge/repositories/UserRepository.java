package com.capstone.africa.semicolon.comms_bridge.repositories;

import com.capstone.africa.semicolon.comms_bridge.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String username);
}
