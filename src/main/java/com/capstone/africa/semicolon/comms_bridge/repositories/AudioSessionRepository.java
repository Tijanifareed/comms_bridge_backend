package com.capstone.africa.semicolon.comms_bridge.repositories;

import com.capstone.africa.semicolon.comms_bridge.entities.AudioSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AudioSessionRepository extends JpaRepository<AudioSession, Long> {
}
