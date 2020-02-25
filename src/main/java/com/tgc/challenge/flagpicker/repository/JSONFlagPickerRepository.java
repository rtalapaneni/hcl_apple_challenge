package com.tgc.challenge.flagpicker.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgc.challenge.flagpicker.model.Continet;
import com.tgc.challenge.flagpicker.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository loads the continents data on application starts and keeps in memory for later use
 */
@Repository
@RefreshScope
public class JSONFlagPickerRepository {

    @Value("classpath:${location.continent.json}")
    private Resource jsonFileResource;

    @Autowired
    private ObjectMapper mapper;

    private List<Continet> continents = new ArrayList<>();

    /**
     * Load continents data on application start
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        continents = mapper.readValue(jsonFileResource.getURL(), new TypeReference<List<Continet>>() {});
    }

    /**
     * Gives all the continents data
     * @return
     */
    public List<Continet> getAllContinents() {
        return continents;
    }

    /**
     * Finds the continent details matching provided name
     * @param continent
     * @return
     */
    public Optional<Continet> getContinent(String continent) {
        return continents.stream().filter(s -> s.getContinent().equalsIgnoreCase(continent)).findFirst();
    }

    /**
     * Fetches all the countries of continent matching to provided name
     * @param continent
     * @return
     */
    public List<Country> getContinentCountries(String continent) {
        Optional<Continet> result = getContinent(continent);
        return result.isPresent() ? result.get().getCountries() : new ArrayList<>();
    }

    /**
     * Gives all the countries of the available continents
     * @return
     */
    public List<Country> getAllCountries() {
        return continents.stream().flatMap(continents -> continents.getCountries().stream()).collect(Collectors.toList());
    }

    /**
     * Finds all the countries matching to provided names
     * @param countries
     * @return
     */
    public List<Country> findCountries(List<String> countries) {
        return continents.stream().flatMap(s -> s.getCountries().stream()).filter(s -> countries.contains(s.getName())).collect(Collectors.toList());
    }

    /**
     * Finds the country matching with provided country name
     * @param country
     * @return
     */
    public Optional<Country> findCountry(String country) {
        return continents.stream().flatMap(s -> s.getCountries().stream()).filter(s -> s.getName().equalsIgnoreCase(country)).findFirst();
    }
}
