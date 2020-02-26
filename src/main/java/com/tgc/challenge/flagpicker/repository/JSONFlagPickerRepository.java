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
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private FlagPickerRepository repository;

    private Optional<List<Continet>> continents = Optional.empty();

    /**
     * Load continents data on application start
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        List<Continet> data = mapper.readValue(jsonFileResource.getURL(), new TypeReference<List<Continet>>() {});
        continents = CollectionUtils.isEmpty(data) ? Optional.empty() : Optional.of(data);

        //data.stream().forEach(s -> s.getCountries().forEach(c -> c.setContinet(s)));
        //repository.saveAll(data);

    }

    /**
     * Gives all the continents data
     * @return
     */
    public Optional<List<Continet>> getAllContinents() {
        return continents;
    }

    /**
     * Finds the continent details matching provided name
     * @param continent
     * @return
     */
    public Optional<Continet> getContinent(String continent) {
        return continents.isPresent() ? continents.get().stream().filter(s -> s.getContinent().equalsIgnoreCase(continent)).findFirst() : Optional.empty();
    }

    /**
     * Fetches all the countries of continent matching to provided name
     * @param continent
     * @return
     */
    public Optional<List<Country>> getContinentCountries(String continent) {
        Optional<Continet> result = getContinent(continent);
        List<Country> countries = result.isPresent() ? result.get().getCountries() : null;
        return CollectionUtils.isEmpty(countries) ? Optional.empty() : Optional.of(countries);
    }

    /**
     * Gives all the countries of the available continents
     * @return
     */
    public Optional<List<Country>> getAllCountries() {

        List<Country> countries = continents.isPresent() ? continents.get().stream().flatMap(s -> s.getCountries().stream()).collect(Collectors.toList()) : null;

        return CollectionUtils.isEmpty(countries) ? Optional.empty() : Optional.of(countries);
    }

    /**
     * Finds all the countries matching to provided names
     * @param countries
     * @return
     */
    public Optional<List<Country>> findCountries(List<String> countries) {
        List<Country> countriesList = continents.isPresent() ? continents.get().stream().flatMap(s -> s.getCountries().stream()).filter(s -> countries.contains(s.getName())).collect(Collectors.toList()) : null;
        return CollectionUtils.isEmpty(countriesList) ? Optional.empty() : Optional.of(countriesList);
    }

    /**
     * Finds the country matching with provided country name
     * @param country
     * @return
     */
    public Optional<Country> findCountry(String country) {
        return continents.isPresent() ? continents.get().stream().flatMap(s -> s.getCountries().stream()).filter(s -> s.getName().equalsIgnoreCase(country)).findFirst() : null;
    }
}
