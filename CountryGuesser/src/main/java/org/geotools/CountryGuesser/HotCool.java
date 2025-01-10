package org.geotools.CountryGuesser;

import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.api.data.SimpleFeatureSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import java.io.File;
import java.io.IOException;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The Hotcool class manages the game logic for a country guessing game.
 * It tracks the player's time elapsed, calculates the score based on it,
 * and provides feedback ("Hotter" or "Cooler") based on the proximity of the player's guess to the target country.
 * 
 * The class uses GeoTools library for getting the centroid of the answer country in order to calculate 
 * the coordinate between the target country to the clicked coordinate.
 * 
 * @author Jack Chen
 * @version 1.0
 */
public class HotCool {
    private Coordinate targetCountryCoordinate;
    private Timer scoreTimer;
    private long startTime;
    private int score = 1000;
    private Coordinate lastGuess;

    /**
     * Constructor for HotCool class.
     * Initializes the game with the provided country's name and gets its centroid coordinate.
     * 
     * @param shapefilePath Path to the shapefile used for getting country coordinates.
     * @param countryName The name of the target country.
     * @throws IOException If an I/O error occurs.
     */
    public HotCool(String shapefilePath, String countryName) {
        this.targetCountryCoordinate = GeoUtils.getCountryCentroid(shapefilePath, countryName);
    }

    /**
     * Initializes the timer for score calculation.
     * Decreases score every second based on elapsed time.
     */
    public void initializeScoreTimer() {
        scoreTimer = new Timer(1000, new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startTime < 0) return;
                long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
                if (elapsedSeconds <= 15) {
                score -= 5; 
            } else {
                score -= 10; 
            }
            // Ensure the score does not fall below zero
            if (score < 0) {
                score = 0;
            }
            
            }
        });
    }

    /**
     * Starts the game timer.
     */
    public void startGame() {
        startTime = System.currentTimeMillis();
        scoreTimer.start();
    }

    /**
     * Gets the current score.
     * 
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Determines whether the player's guess is "Hotter" or "Cooler" compared to the last guess.
     * 
     * @param currentGuess The player's current guess coordinate.
     * @return "Hotter", "Cooler", or "First Guess" based on the guess's proximity to the target.
     */
    public String getHotOrCool(Coordinate currentGuess) {
        if (lastGuess == null) {
            lastGuess = currentGuess;
            return "First Guess";
        }

        double lastDistance = lastGuess.distance(targetCountryCoordinate);
        double currentDistance = currentGuess.distance(targetCountryCoordinate);
        lastGuess = currentGuess;

        if (currentDistance < lastDistance) {
            return "Hotter";
        } else {
            return "Cooler";
        }
    }

 
}


class GeoUtils {
    /**
     * Gets the centroid coordinate of a country specified by name from a shapefile.
     * 
     * @param shapefilePath The path to the shapefile.
     * @param countryName The name of the country.
     * @return The centroid coordinate of the country, or null if not found.
     * @throws IOException If an I/O error occurs.
     */
    public static Coordinate getCountryCentroid(String shapefilePath, String countryName) {
        try {
            File file = new File(shapefilePath);
            FileDataStore store = FileDataStoreFinder.getDataStore(file);
            SimpleFeatureSource featureSource = store.getFeatureSource();
            SimpleFeatureCollection collection = featureSource.getFeatures();

            try (SimpleFeatureIterator features = collection.features()) {
                while (features.hasNext()) {
                    SimpleFeature feature = features.next();
                    String name = (String) feature.getAttribute("NAME_EN"); // Replace with actual attribute name
                    if (countryName.equals(name)) {
                        Geometry geometry = (Geometry) feature.getDefaultGeometry();
                        return geometry.getCentroid().getCoordinate();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
