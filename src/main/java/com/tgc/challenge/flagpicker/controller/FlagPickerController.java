package com.tgc.challenge.flagpicker.controller;

import com.tgc.challenge.flagpicker.dto.ContinetDTO;
import com.tgc.challenge.flagpicker.dto.CountryDTO;
import com.tgc.challenge.flagpicker.service.FlagPickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class FlagPickerController {

    @Autowired
    private FlagPickerService service;

    @GetMapping("continents")
    public @ResponseBody List<ContinetDTO> getContinents() {
        return service.getAllContinents();
    }

    @GetMapping("continents/{continent}")
    public @ResponseBody ContinetDTO getContinents(@PathVariable String continent) {
        Optional<ContinetDTO> result = service.getContinent(continent);
        return result.isPresent() ? result.get() : null;
    }

    @GetMapping("continents/{continent}/countries")
    public @ResponseBody List<CountryDTO> getContinentCountries(@PathVariable String continent) {
        return service.getContinentCountries(continent);
    }

    @GetMapping("countries")
    public @ResponseBody List<CountryDTO> getCountries() {
        return service.getAllCountries();
    }

    @PostMapping(value = "countries", consumes = "application/json", produces = "application/json")
    public @ResponseBody List<CountryDTO> getCountries(@RequestBody List<String> countries) {
        return service.findCountries(countries);
    }

    @PostMapping(value = "countries/flags", consumes = "application/json", produces = "application/json")
    public @ResponseBody List<String> findCountryFlags(@RequestBody List<String> countries) {
        return service.findCountryFlags(countries);
    }

    @GetMapping("countries/{country}")
    public @ResponseBody CountryDTO getCountries(@PathVariable String country) {
        Optional<CountryDTO> result = service.getCountry(country);
        return result.isPresent() ? result.get() : null;
    }

    @GetMapping("countries/{country}/flag")
    public String getCountryFlag(@PathVariable String country) {
        Optional<CountryDTO> result = service.getCountry(country);
        return result.isPresent() ? result.get().getFlag() : null;
    }
}
