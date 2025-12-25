package org.geotools;

import static org.junit.Assert.assertEquals;
import org.geotools.CountryGuesser.CountryGuess;
import org.geotools.CountryGuesser.run;
import org.junit.Test;
import org.geotools.styling.SLD;
import org.geotools.api.style.Style;

import java.awt.*;
import java.net.URL;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    
    @Test
    public void createStyleTest()
    {
        URL shapefileURL = run.class
                .getClassLoader()
                .getResource("ne_50m_admin_0_countries.shp");

        if (shapefileURL == null) {
            throw new RuntimeException("Shapefile not found in resources");
        }
        CountryGuess test = new CountryGuess(shapefileURL);
        Style style = test.createStyle();
        assertEquals((SLD.createLineStyle(Color.BLUE, 1.0f)), style);
    }
    
}
