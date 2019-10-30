package com.github.therycn.tyhallowinner.repository;

import com.github.therycn.tyhallowinner.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    Optional<Athlete> findByUsername(String username);

}
