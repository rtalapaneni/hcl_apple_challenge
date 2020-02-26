package com.tgc.challenge.flagpicker.controller;

import com.tgc.challenge.flagpicker.dto.ContinetDTO;
import com.tgc.challenge.flagpicker.dto.CountryDTO;
import com.tgc.challenge.flagpicker.service.FlagPickerService;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Flag Picker API for handling the GET and PUT Operations
 */
@RestController
@RequestMapping("v1")
public class FlagPickerController {

    @Autowired
    private FlagPickerService service;
    @Autowired
    private MeterRegistry meterRegistry;

    /**
     * Gives all the contents available as part of the flag picker json
     *
     * @return
     */
    @Operation(summary = "Fetch all the continents")
    @GetMapping("continents")
    public @ResponseBody
    ResponseEntity<List<ContinetDTO>> getContinents() {
        meterRegistry.counter("count.continents").increment(1);
        return buildResponseEntity(service.getAllContinents());
    }

    /**
     * Provides Continent details matching to provided search criteria
     *
     * @param continent
     * @return
     */
    @Operation(summary = "Fetch continent details matching to provided search criteria")
    @GetMapping("continents/{continent}")
    public @ResponseBody
    ResponseEntity<ContinetDTO> getContinents(@PathVariable String continent) {
        meterRegistry.counter("count.continents." + continent).increment(1);
        Optional<ContinetDTO> result = service.getContinent(continent);
        return buildResponseEntity(result);
    }

    /**
     * Provides all the countries of given continent
     *
     * @param continent
     * @return
     */
    @Operation(summary = "Fetch all the countries of provided continent")
    @GetMapping("continents/{continent}/countries")
    public @ResponseBody
    ResponseEntity<List<CountryDTO>> getContinentCountries(@PathVariable String continent) {
        meterRegistry.counter("count.continents." + continent + "countries").increment(1);
        return buildResponseEntity(service.getContinentCountries(continent));
    }

    /**
     * Provides all the countries available in provided flag picker json
     *
     * @return
     */
    @Operation(summary = "Fetch all the countries")
    @GetMapping("countries")
    public @ResponseBody
    ResponseEntity<List<CountryDTO>> getCountries() {
        meterRegistry.counter("count.countries").increment(1);
        return buildResponseEntity(service.getAllCountries());
    }

    /**
     * Provides all the country details matching to provided search criteria
     *
     * @param countries
     * @return
     */
    @Operation(summary = "Fetches all the countries matching to provided search criteria")
    @PostMapping(value = "countries", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<CountryDTO>> getCountries(@RequestBody List<String> countries) {
        meterRegistry.counter("count.countries.search").increment(1);
        return buildResponseEntity(service.findCountries(countries));
    }

    /**
     * Provides flags of the countries matching provided search criteria
     *
     * @param countries
     * @return
     */
    @Operation(summary = "Fetches all the countries flags matching to provided search criteria")
    @PostMapping(value = "countries/flags", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<String>> findCountryFlags(@RequestBody List<String> countries) {
        meterRegistry.counter("count.countries.flags").increment(1);
        return buildResponseEntity(service.findCountryFlags(countries));
    }

    /**
     * Provides all the details of country matching to provided search criteria
     *
     * @param country
     * @return
     */
    @Operation(summary = "Fetches the country matching to provided name")
    @GetMapping("countries/{country}")
    public @ResponseBody
    ResponseEntity<CountryDTO> getCountries(@PathVariable String country) {
        meterRegistry.counter("count.countries." + country).increment(1);
        Optional<CountryDTO> result = service.getCountry(country);
        return buildResponseEntity(result);
    }

    /**
     * Provides flag of the country matching provided search criteria
     *
     * @param country
     * @return
     */
    @Operation(summary = "Fetches flag matching to provided country")
    @GetMapping("countries/{country}/flag")
    public ResponseEntity<String> getCountryFlag(@PathVariable String country) {
        meterRegistry.counter("count.countries." + country + ".flag").increment(1);
        Optional<CountryDTO> result = service.getCountry(country);
        return buildResponseEntity(result.isPresent() ? Optional.of(result.get().getFlag()) : Optional.empty());
    }

    /**
     * Creates the response entity for provided data
     * Gives HTTP status OK, if data is available else gives status NO_CONTENT
     *
     * @param data
     * @param <T>
     * @return
     */
    private <T> ResponseEntity<T> buildResponseEntity(Optional<T> data) {
        return data.isPresent() ? new ResponseEntity<T>(data.get(), HttpStatus.OK) : new ResponseEntity<T>(HttpStatus.NO_CONTENT);
    }
}
