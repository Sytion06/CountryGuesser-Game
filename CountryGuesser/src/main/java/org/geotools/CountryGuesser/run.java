package org.geotools.CountryGuesser;


public class run {
    public static void main(String[] args) {
        String shapefilePath = "\\Users\\18261\\CountryGuesser\\target\\generated-sources\\ne_50m_admin_0_countries.shp";
        new CountryGuess(shapefilePath);
    }
}
