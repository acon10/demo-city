package com.example.cities.service;

import com.example.cities.domain.City;
import com.example.cities.dto.CitiesResponse;
import com.example.cities.dto.CityDto;

public interface CityService {
    City saveCity(City city);
    CitiesResponse getAll();

    CitiesResponse getContain(String name);

    CityDto updateCity(CityDto cityDto);
}
