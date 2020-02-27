package com.tgc.challenge.flagpicker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tgc.challenge.flagpicker.dto.ContinetDTO;
import com.tgc.challenge.flagpicker.dto.CountryDTO;
import com.tgc.challenge.flagpicker.model.Continet;
import com.tgc.challenge.flagpicker.model.Country;
import com.tgc.challenge.flagpicker.repository.FlagPickerRepository;
import com.tgc.challenge.flagpicker.repository.JSONFlagPickerRepository;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.swing.text.html.Option;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlagPickerService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private JSONFlagPickerRepository repository;

    @Autowired
    private FlagPickerRepository dbRepository;

    private Type continentDTOTypeRef = new TypeReference<List<ContinetDTO>>() {}.getType();
    private Type countryDTOTypeRef = new TypeReference<List<CountryDTO>>() {}.getType();

    /**
     * Fetches all the continents
     *
     * @return
     */
    public Optional<List<ContinetDTO>> getAllContinents() {
        List<Continet> continentsEntityData = dbRepository.findAll();
        Optional<List<Continet>> continentsEntity = continentsEntityData == null ? Optional.empty() : Optional.of(continentsEntityData);
        List<ContinetDTO> continentsDTO = continentsEntity.isPresent() ? mapper.map(continentsEntity.get(), continentDTOTypeRef) : null;
        return CollectionUtils.isEmpty(continentsDTO) ? Optional.empty() : Optional.of(continentsDTO);
    }

    /**
     * Fetches all the continents
     *
     * @return
     */
    public Optional<List<ContinetDTO>> getAllContinentsJSON() {
        Optional<List<Continet>> continentsEntity = repository.getAllContinents();
        List<ContinetDTO> continentsDTO = continentsEntity.isPresent() ? mapper.map(continentsEntity.get(), continentDTOTypeRef) : null;
        return CollectionUtils.isEmpty(continentsDTO) ? Optional.empty() : Optional.of(continentsDTO);
    }

    /**
     * Gives the continent details matching to provided name
     *
     * @param continent
     * @return
     */
    public Optional<ContinetDTO> getContinent(String continent) {
        Optional<Continet> result = repository.getContinent(continent);
        return result.isPresent() ? Optional.of(mapper.map(result.get(), ContinetDTO.class)) : Optional.empty();
    }

    /**
     * Fetches all the countries of continent
     *
     * @param continent
     * @return
     */
    public Optional<List<CountryDTO>> getContinentCountries(String continent) {
        Optional<List<Country>> countryEntities = repository.getContinentCountries(continent);

        List<CountryDTO> countryDTOs = countryEntities.isPresent() ? mapper.map(countryEntities.get(), countryDTOTypeRef) : null;

        return CollectionUtils.isEmpty(countryDTOs) ? Optional.empty() : Optional.of(countryDTOs);
    }

    /**
     * Fetches all the countries
     *
     * @return
     */
    public Optional<List<CountryDTO>> getAllCountries() {
        Optional<List<Country>> countryEntities = repository.getAllCountries();
        List<CountryDTO> countryDTOs = countryEntities.isPresent() ? mapper.map(countryEntities.get(), countryDTOTypeRef) : null;
        return CollectionUtils.isEmpty(countryDTOs) ? Optional.empty() : Optional.of(countryDTOs);
    }

    /**
     * Finds all the countries for provided names
     *
     * @param countries
     * @return
     */
    public Optional<List<CountryDTO>> findCountries(List<String> countries) {
        Optional<List<Country>> countryEntities = repository.findCountries(countries);
        List<CountryDTO> countryDTOs = countryEntities.isPresent() ? mapper.map(countryEntities.get(), countryDTOTypeRef) : null;
        return CollectionUtils.isEmpty(countryDTOs) ? Optional.empty() : Optional.of(countryDTOs);
    }

    /**
     * Gets the country details of the provided name
     *
     * @param country
     * @return
     */
    public Optional<CountryDTO> getCountry(String country) {
        Optional<Country> result = repository.findCountry(country);
        return result.isPresent() ? Optional.of(mapper.map(result.get(), CountryDTO.class)) : Optional.empty();
    }

    /**
     * Gives the flag of provided country
     *
     * @param country
     * @return
     */
    public Optional<String> getCountryFlag(String country) {
        Optional<CountryDTO> result = getCountry(country);
        return result.isPresent() ? Optional.of(result.get().getFlag()) : Optional.empty();
    }

    /**
     * Find flags for the provided countries
     *
     * @param countries
     * @return
     */
    public Optional<List<String>> findCountryFlags(List<String> countries) {
        Optional<List<Country>> result = repository.findCountries(countries);
        return result.isPresent() ? Optional.of(result.get().stream().map(s -> s.getFlag()).collect(Collectors.toList())) : Optional.empty();
    }
}
