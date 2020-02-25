package com.tgc.challenge.flagpicker.dto;

import java.util.List;

public class ContinetDTO {
    private String continent;
    private List<CountryDTO> countries;

    /**
     * Default constructor
     */
    public ContinetDTO() {}

    /**
     * Constructor with arguments to set during the initialization
     * @param continent
     * @param countries
     */
    public ContinetDTO(String continent, List<CountryDTO> countries) {
        this.continent = continent;
        this.countries = countries;
    }

    /**
     * Gives the name of continent
     * @return
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Sets the name of continent
     * @param continent
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * Gives the collection of countries associated to continent
     * @return
     */
    public List<CountryDTO> getCountries() {
        return countries;
    }

    /**
     * Sets the countries to be associated with continent
     * @param countries
     */
    public void setCountries(List<CountryDTO> countries) {
        this.countries = countries;
    }
}
