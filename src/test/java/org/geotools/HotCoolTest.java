package org.geotools;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.geotools.CountryGuesser.HotCool;

public class HotCoolTest {

    private HotCool hotCool;

    @Before
    public void setUp() {
        String shapefilePath = "\\Users\\18261\\CountryGuesser\\target\\generated-sources\\e_50m_admin_0_countries.shp";
        String countryName = "Canada";
        hotCool = new HotCool(shapefilePath, countryName);
    }

    @Test
    public void testScoreTimer() {
        hotCool.initializeScoreTimer();
        hotCool.startGame();
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Properly handle InterruptedException
            throw new RuntimeException("Thread interrupted", e);
        }
        assertTrue("Score should decrease after time passes", hotCool.getScore() < 1000);
    }

}
