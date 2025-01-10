package org.geotools;

import static org.junit.Assert.assertEquals;
import org.geotools.CountryGuesser.CountryGuess;
import org.junit.Test;
import org.geotools.styling.SLD;
import org.geotools.api.style.Style;

import java.awt.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    
    @Test
    public void createStyleTest()
    {
        CountryGuess test = new CountryGuess("\\Users\\18261\\CountryGuesser\\target\\generated-sources\\ne_50m_admin_0_countries.shp");
        Style style = test.createStyle();
        assertEquals((SLD.createLineStyle(Color.BLUE, 1.0f)), style);
    }
    
}
