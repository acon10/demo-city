package com.example.cities.service.impl;

import com.example.cities.domain.City;
import com.example.cities.dto.CitiesResponse;
import com.example.cities.dto.CityDto;
import com.example.cities.repository.CityRepository;
import com.example.cities.service.CityService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final Mapper mapper;

    @Override
    public City saveCity(City city) {
        log.info("city saved: {}", city.toString());
        return cityRepository.save(city);
    }

    @Override
    public CitiesResponse getAll() {
        List<CityDto> cities = new ArrayList<>();
        cityRepository.findAll().forEach(city -> cities.add(mapper.map(city, CityDto.class)));
        CitiesResponse response = new CitiesResponse();
        response.setCities(cities);
        return response;
    }

    @Override
    public CitiesResponse getContain(String name) {
        List<CityDto> cities = new ArrayList<>();
        cityRepository.findByNameContainingIgnoreCase(name).forEach(city -> cities.add(mapper.map(city, CityDto.class)));
        CitiesResponse response = new CitiesResponse();
        response.setCities(cities);
        return response;
    }

    @Override
    public CityDto updateCity(CityDto cityDto) {
        cityRepository.findById(cityDto.getId())
                .ifPresentOrElse(c->{
                            c.setName(cityDto.getName());
                            c.setPhoto(cityDto.getPhoto());
                            cityRepository.saveAndFlush(c);
                        },
                        () -> new RuntimeException("City not found"));

        return cityDto;
    }

    @Bean
    CommandLineRunner cityInitRunner(){
        return (arg)->{
            init();
        };
    }

    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:assets/csv/cities.csv");
        try(Reader reader = new FileReader(file)) {
            CsvToBean<City> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(City.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<City> cities = csvToBean.parse();
            cities.forEach(city -> this.saveCity(city));
            log.info("all cities saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
