package com.gestion.activities.web;

import com.gestion.activities.domain.dto.ActivitiesRequestDto;
import com.gestion.activities.domain.dto.ActivitiesResponseDto;
import com.gestion.activities.repository.ActivitiesRepository;
import com.gestion.activities.service.ActivitiesService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/ms-soa")
@RequiredArgsConstructor
public class ActivitiesController {


    final ActivitiesService activitiesService;

    final ActivitiesRepository activitiesRepository;

    @GetMapping("{id}")
    public Mono<ActivitiesResponseDto> getDataActivitiesById(@PathVariable Integer id) {
        return this.activitiesService.findById(id);
    }

    @GetMapping("/listData")
    public Flux<ActivitiesResponseDto> getDataActivitiesComplete() {
        return this.activitiesService.findAll();
    }


    @PutMapping("/update/{id}")
    public Mono<ActivitiesResponseDto> updateDataActivities(@RequestBody ActivitiesRequestDto dto, @PathVariable Integer id) {
        return this.activitiesService.updateActivities(dto, id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public Mono<ActivitiesResponseDto> saveNewDataActivities(@RequestBody ActivitiesRequestDto dto) {
        return this.activitiesService.saveNewActivities(dto);
    }

    @DeleteMapping("/dtotal/{id}")
    public Mono<Void> deleteTotalActivities(@PathVariable Integer id) {
        return this.activitiesService.deleteActivities(id);
    }

}
