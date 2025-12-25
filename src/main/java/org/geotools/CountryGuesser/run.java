package org.geotools.CountryGuesser;
import java.io.*;
import java.net.URL;
import java.nio.file.*;

public class run {
    public static void main(String[] args) throws IOException {
        URL shapefileURL = run.class
                .getClassLoader()
                .getResource("ne_50m_admin_0_countries.shp");

        if (shapefileURL == null) {
            throw new RuntimeException("Shapefile not found in resources");
        }

        new CountryGuess(shapefileURL);

    }
}
