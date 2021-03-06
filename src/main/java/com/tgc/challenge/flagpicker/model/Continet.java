package com.tgc.challenge.flagpicker.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
@Entity
public class Continet {
    @Id
    private String continent;
    @OneToMany(mappedBy = "continet", cascade = CascadeType.ALL)
    private List<Country> countries;

    /**
     * Default constructor
     */
    public Continet() {}

    /**
     * Constructor with arguments to set during the initialization
     * @param continent
     * @param countries
     */
    public Continet(String continent, List<Country> countries) {
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
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Sets the countries to be associated with continent
     * @param countries
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
