package com.example.cities.controller;

import com.example.cities.dto.CitiesResponse;
import com.example.cities.dto.CityDto;
import com.example.cities.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityService cityService;

    @GetMapping("/all")
    public ResponseEntity<CitiesResponse> getAll(){
        return ResponseEntity.ok(cityService.getAll());
    }

    @GetMapping("/find-contain")
    public ResponseEntity<CitiesResponse> getContain(@RequestParam String cityName){
        return ResponseEntity.ok(cityService.getContain(cityName));
    }

    @Secured("ROLE_ALLOW_EDIT")
    @PutMapping("/update")
    public ResponseEntity<CityDto> updateCity(@RequestBody CityDto cityDto){
        return ResponseEntity.ok(cityService.updateCity(cityDto));
    }
}
