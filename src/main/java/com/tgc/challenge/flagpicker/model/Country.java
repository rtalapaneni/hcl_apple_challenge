package com.tgc.challenge.flagpicker.model;

/**
 * Country object with details of the name and flag
 */
public class Country {
    private String name;
    private String flag;

    /**
     * Default constructor
     */
    public Country() {}

    /**
     * Constructor with arguments to set during the initialization
     * @param name
     * @param flag
     */
    public Country(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }

    /**
     * Get name of the country
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of the country
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get flag for the country
     * @return
     */
    public String getFlag() {
        return flag;
    }

    /**
     * set flag for the country
     * @param flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
