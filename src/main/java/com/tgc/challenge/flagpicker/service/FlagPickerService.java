package com.tgc.challenge.flagpicker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tgc.challenge.flagpicker.dto.ContinetDTO;
import com.tgc.challenge.flagpicker.dto.CountryDTO;
import com.tgc.challenge.flagpicker.model.Continet;
import com.tgc.challenge.flagpicker.model.Country;
import com.tgc.challenge.flagpicker.repository.JSONFlagPickerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlagPickerService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private JSONFlagPickerRepository repository;

    /**
     * Fetches all the continents
     * @return
     */
    public List<ContinetDTO> getAllContinents() {
        List<ContinetDTO> continents = mapper.map(repository.getAllContinents(), new TypeToken<List<ContinetDTO>>(){}.getType());
        return continents;
    }

    /**
     * Gives the continent details matching to provided name
     * @param continent
     * @return
     */
    public Optional<ContinetDTO> getContinent(String continent) {
        Optional<Continet> result = repository.getContinent(continent);
        return result.isPresent() ? Optional.of(mapper.map(result.get(), ContinetDTO.class)) : Optional.empty();
    }

    /**
     * Fetches all the countries of continent
     * @param continent
     * @return
     */
    public List<CountryDTO> getContinentCountries(String continent) {
        return mapper.map(repository.getContinentCountries(continent), new TypeReference<List<CountryDTO>>() {}.getType());
    }

    /**
     * Fetches all the countries
     * @return
     */
    public List<CountryDTO> getAllCountries() {
        return mapper.map(repository.getAllCountries(), new TypeReference<List<CountryDTO>>() {}.getType());
    }

    /**
     * Finds all the countries for provided names
     * @param countries
     * @return
     */
    public List<CountryDTO> findCountries(List<String> countries) {
        return mapper.map(repository.findCountries(countries), new TypeReference<List<CountryDTO>>() {}.getType());
    }

    /**
     * Gets the country details of the provided name
     * @param country
     * @return
     */
    public Optional<CountryDTO> getCountry(String country) {
        Optional<Country> result = repository.findCountry(country);
        return result.isPresent() ? Optional.of(mapper.map(result.get(), CountryDTO.class)) : Optional.empty();
    }

    /**
     * Gives the flag of provided country
     * @param country
     * @return
     */
    public Optional<String> getCountryFlag(String country) {
        Optional<CountryDTO> result = getCountry(country);
        return result.isPresent() ? Optional.of(result.get().getFlag()) : Optional.empty();
    }

    /**
     * Find flags for the provided countries
     * @param countries
     * @return
     */
    public List<String> findCountryFlags(List<String> countries) {
        List<Country> result = repository.findCountries(countries);
        return result.stream().map(s -> s.getFlag()).collect(Collectors.toList());
    }
}
