package com.tgc.challenge.flagpicker.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tgc.challenge.flagpicker.dto.ContinetDTO;
import com.tgc.challenge.flagpicker.dto.CountryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlagPickerControllerTest {
    @Autowired
    private FlagPickerController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    /**
     * Verifies the get continents end point
     */
    @Test
    public void testGetContinents() {
        ResponseEntity<ContinetDTO[]> response = restTemplate.getForEntity(getBaseURL()+"continents", ContinetDTO[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify size of the response
        assertThat(response.getBody().length).isEqualTo(5);
    }

    /**
     * Verifies the get continents end point
     */
    @Test
    public void testGetContinent() {
        String continent = "Africa";
        ResponseEntity<ContinetDTO> response = restTemplate.getForEntity(getBaseURL()+"continents/"+continent, ContinetDTO.class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify continent name match
        assertThat(response.getBody().getContinent()).isEqualTo(continent);

        //Verify size of the countries in continent
        assertThat(response.getBody().getCountries().size()).isEqualTo(5);
    }

    /**
     * Verifies the get continents end point
     */
    @Test
    public void testGetContinentForNoContent() {
        String continent = "Test";
        ResponseEntity<ContinetDTO> response = restTemplate.getForEntity(getBaseURL()+"continents/"+continent, ContinetDTO.class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Verifies the get continent countries end point
     */
    @Test
    public void testGetContinentCountries() {
        String continent = "Africa";
        ResponseEntity<CountryDTO[]> response = restTemplate.getForEntity(getBaseURL()+"continents/"+continent+"/countries", CountryDTO[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify size of the countries
        assertThat(response.getBody().length).isEqualTo(5);
    }

    /**
     * Verifies the get continent countries end point
     */
    @Test
    public void testGetContinentCountriesForNoContent() {
        String continent = "Test";
        ResponseEntity<CountryDTO[]> response = restTemplate.getForEntity(getBaseURL()+"continents/"+continent+"/countries", CountryDTO[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Verifies the get countries end point
     */
    @Test
    public void testGetCountries() {
        ResponseEntity<CountryDTO[]> response = restTemplate.getForEntity(getBaseURL()+"/countries", CountryDTO[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify size of the countries
        assertThat(response.getBody().length).isEqualTo(25);
    }

    /**
     * Verifies the get countries end point
     */
    @Test
    public void testPostCountriesSearch() {
        List<String> countries = Arrays.asList("India", "USA");
        ResponseEntity<CountryDTO[]> response = restTemplate.postForEntity(getBaseURL()+"countries", countries, CountryDTO[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify size of the countries
        assertThat(response.getBody().length).isEqualTo(2);
    }

    /**
     * Verifies the get countries end point
     */
    @Test
    public void testPostCountriesSearchForNoContent() {
        List<String> countries = Arrays.asList("Test");
        ResponseEntity<CountryDTO[]> response = restTemplate.postForEntity(getBaseURL()+"countries", countries, CountryDTO[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Verifies the get countries flags end point
     */
    @Test
    public void testPostCountriesFlagsSearch() {
        List<String> countries = Arrays.asList("India", "USA");
        ResponseEntity<String[]> response = restTemplate.postForEntity(getBaseURL()+"countries/flags", countries, String[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify size of the countries
        assertThat(response.getBody().length).isEqualTo(2);
    }

    /**
     * Verifies the get countries flags end point
     */
    @Test
    public void testPostCountriesFlagsSearchForNoContent() {
        List<String> countries = Arrays.asList("Test");
        ResponseEntity<String[]> response = restTemplate.postForEntity(getBaseURL()+"countries/flags", countries, String[].class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Verifies the get country end point
     */
    @Test
    public void testGetCountry() {
        String country = "USA";
        ResponseEntity<CountryDTO> response = restTemplate.getForEntity(getBaseURL()+"countries/"+country, CountryDTO.class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify size of the countries
        assertThat(response.getBody().getName()).isEqualToIgnoringCase(country);
    }

    /**
     * Verifies the get countries end point for no content
     */
    @Test
    public void testGetCountryForNoContent() {
        String country = "Test";
        ResponseEntity<CountryDTO> response = restTemplate.getForEntity(getBaseURL()+"countries/"+country, CountryDTO.class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Verifies the get country end point
     */
    @Test
    public void testGetCountryFlag() {
        String country = "USA";
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseURL()+"countries/"+country+"/flag", String.class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        //Verify flag
        assertThat(response.getBody()).isNotEmpty();
    }

    /**
     * Verifies the get countries end point for no content
     */
    @Test
    public void testGetCountryFlagForNoContent() {
        String country = "Test";
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseURL()+"countries/"+country+"/flag", String.class);

        //Verify response status
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Gives the base URL
     * @return
     */
    private String getBaseURL() {
        return "http://localhost:"+port+"/v1/";
    }
}
