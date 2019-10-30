package com.github.therycn.tyhallowinner.service;

import com.github.therycn.tyhallowinner.entity.Athlete;
import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import com.github.therycn.tyhallowinner.repository.AthleteRepository;
import org.springframework.stereotype.Service;

@Service
public class AthleteService {

    private AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public Athlete get(String username) throws AthleteNotFoundException {
        return athleteRepository.findByUsername(username).orElseThrow(AthleteNotFoundException::new);
    }

    public Athlete save(Athlete athlete) {
        return athleteRepository.save(athlete);
    }
}
