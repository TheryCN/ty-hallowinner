package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.dto.AthleteDto;
import com.github.therycn.tyhallowinner.entity.Athlete;
import com.github.therycn.tyhallowinner.service.AthleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("athlete")
public class AthleteController {

    private AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @GetMapping
    public ResponseEntity<List<AthleteDto>> list() {
        return ResponseEntity.ok(athleteService.list().stream().map(this::map).collect(Collectors.toList()));
    }

    private AthleteDto map(Athlete athlete) {
        return new AthleteDto(athlete.getId(), athlete.getUsername());
    }
}
