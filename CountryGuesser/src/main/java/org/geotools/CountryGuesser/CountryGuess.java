package org.geotools.CountryGuesser;

import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;
import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.api.style.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.JMapPane;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.geotools.api.filter.Filter;
import org.geotools.api.filter.FilterFactory;
import org.geotools.factory.CommonFactoryFinder;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
/**
 * The CountryGuess class represents a simple game where the user tries to guess a randomly selected country
 * based on the provided shapefile. The game is displayed with graphical user interface using GeoTools.
 * 
 * The class uses GeoTools library for handling shapefiles, creating maps, and styling features.
 * It includes functionality to randomly select a target country, display a map, and allow the user to click on
 * a country to make a guess.
 * 
 * @author Steven Yang
 * @version 1.0
 */
public class CountryGuess extends JFrame {

    private String targetCountry;
    private SimpleFeatureCollection countries;
    private GeometryFactory geometryFactory = new GeometryFactory();
    private FileDataStore dataStore;
    private JMapFrame mapFrame;
    private JMapPane mapPane;
    private String shapefilepath;
    public HotCool hotCool;
    public int attempts = 0;
    public HintSystem hintSystem;
    
    /**
     * Constructs a CountryGuess object, initializing the game with a specified shapefile path.
     *
     * @param shapefilePath The path to the shapefile used for the game.
     */
    public CountryGuess(String shapefilePath) {
        // Set up the JFrame properties
        setTitle("Country Guesser");
        setSize(1500, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize instance variables and objects
        shapefilepath = shapefilePath; // Set the shapefile path
        hintSystem = new HintSystem(); // Initialize the HintSystem

        
        try {
            // Load the shapefile and initialize the game
            File shapefile = new File(shapefilePath);
            dataStore = FileDataStoreFinder.getDataStore(shapefile);
            SimpleFeatureSource featureSource = dataStore.getFeatureSource();
            countries = dataStore.getFeatureSource().getFeatures();

            // Start the game by Selecting a random country
            targetCountry = hintSystem.getSelectedCountryName();
            // Output the random selected country (only for testing)
            System.out.println(targetCountry);

            // Create a map content
            MapContent map = new MapContent();
            map.setTitle("Country Guesser");

            // Create a feature layer from the shapefile
            FeatureLayer layer = new FeatureLayer(featureSource, createStyle());
            map.addLayer(layer);

            // Create a map frame and add the map content
            mapFrame = new JMapFrame(map);
            mapPane = mapFrame.getMapPane();
            mapFrame.enableToolBar(false);
            mapFrame.enableStatusBar(false);
            mapFrame.setSize(1600, 1200);
            mapFrame.setVisible(true);

            // Initialize the MapMouseListener for handling mouse events on the map
            MapMouseListener mapMouseListener = new MapMouseListener();
            mapPane.addMouseListener(mapMouseListener);

            // Initialize the HotCool game hints and score calculator and start the game
            hotCool = new HotCool(shapefilePath, targetCountry);
            hotCool.initializeScoreTimer();
            hotCool.startGame();  

            // Display initial message welcome message
            JOptionPane.showMessageDialog(this, "Welcome to the Country Guessing Game!\nTry to guess the country on the map.");
        } catch (IOException e) {
            // Handle any exceptions that may occur during shapefile loading
            JOptionPane.showMessageDialog(null, "Error loading shapefile: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates the style for displaying country borders on the map.
     *
     * @return The style for country borders.
     */
    public Style createStyle() {
        // Create the style for displaying country borders
        Style style = SLD.createLineStyle(Color.BLUE, 1.0f);
        return style;
    }

    /**
     * Inner class representing a MapMouseListener that listens for mouse clicks on the map.
     */
    public class MapMouseListener extends MouseAdapter {
        /**
         * Handles mouse clicks on the map. Identifies the clicked feature and checks if it matches the target country.
         *
         * @param e The mouse event representing the click.
         */
        private HotCool hotcool2 = new HotCool(shapefilepath, targetCountry);
        
        public void mouseClicked(MouseEvent e) {

            // Identify the clicked feature
            Coordinate clickedCoords = getCoordinateFromMouseEvent(e);

            // Get feedback (hot or cool) based on the clicked coordinates
            String feedback = hotcool2.getHotOrCool(clickedCoords);

            // Check if the clicked coordinates are valid
            if (clickedCoords != null) {
                // Identify the feature at the clicked coordinates
                SimpleFeature clickedFeature = identifyClickedFeature(getCoordinateFromMouseEvent(e));
                // Check if a feature is found
                if (clickedFeature != null) {
                    // Test statement of the identifyClickedFeature, getCoordinateFromMouseEvent methods works (unit test)
                    System.out.println(clickedFeature.getAttribute("NAME_EN"));
                    // Check if the guessed country matches the target country
                    if ((clickedFeature.getAttribute("NAME_EN")).equals(targetCountry)) {
                        // If correct, display a congratulatory message with the score and the number of attempts
                        int score = hotCool.getScore();
                        JOptionPane.showMessageDialog(null, "Congratulations! You guessed it!\nYour score is " + score
                         + "!\n You attempted " + attempts + " times!");
                        // Close the jMapFrame
                        mapFrame.dispose();
                    } else{
                        // If incorrect, increment the attempts counter and provide feedback and, if available, a hint
                        attempts ++;
                        String hint = hintSystem.getHint(attempts);
                        // Display an incorrect message with feedback and, if available, a hint
                        if (hint != null) {
                            JOptionPane.showMessageDialog(null, "Incorrect! Try again.\n" + feedback + "\nHint: " + hint);
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect! Try again.\n" + feedback);      
                        }
                    }
                }
            }
        }
        /**
         * Gets the map coordinates from the mouse event.
         *
         * @param e The mouse event.
         * @return The map coordinates of the clicked point.
         */
        public Coordinate getCoordinateFromMouseEvent(MouseEvent e) {
            try {
                // Get the clicked coordinates
                double x = e.getX();
                double y = e.getY();
                
                // Convert pixel coordinates to map coordinates
                
                Point screenPos = new Point((int) x, (int) y);
                Point2D worldPos = mapPane.getScreenToWorldTransform().transform(screenPos, null);

                // Create a Point geometry
                Coordinate clickedCoords = new Coordinate(worldPos.getX(), worldPos.getY());
                return clickedCoords;
            } finally{}
        }
        /**
         * Identifies the feature clicked based on the provided coordinates.
         *
         * @param clickedCoords The coordinates of the clicked point on the map.
         * @return The clicked feature, or null if no feature is found.
         */
        public SimpleFeature identifyClickedFeature(Coordinate clickedCoords) {
            // Get the filter factory for creating spatial filters
            FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
            // Create a spatial filter that checks if the clicked point is within a certain distance of the feature's geometry
            // The distance is set to 0 meters to ensure the clicked point is exactly on the feature
            Filter filter = filterFactory.dwithin(
                filterFactory.literal(geometryFactory.createPoint(clickedCoords)),
                filterFactory.property("the_geom"), 0, "meters"
            );
            // Iterate through the features in the dataset
            SimpleFeatureIterator iterator = countries.features();
            
            try {
                while (iterator.hasNext()) {
                    SimpleFeature feature = iterator.next();
                    // Evaluate the spatial filter for each feature
                    if (filter.evaluate(feature)) {
                        return feature; // Return the feature if the filter condition is satisfied
                    }
                }
                // Close the iterator once all features are processed
                iterator.close();
            } catch (Exception e) {
                // Handle any exceptions that may occur during the iteration
                e.printStackTrace();
            }
            // If no feature is found, print a message and return null
            System.out.println("null");
            return null;
        }
    }
}