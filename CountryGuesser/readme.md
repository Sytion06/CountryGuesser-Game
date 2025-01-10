# Country Guesser

## Purpose

Country Guesser is an interactive geographical learning tool designed to enhance users' knowledge of world countries through an engaging guessing game. The core objective of this application is to present users with a randomly selected country , challenging them to identify the country on a map interface. The application caters to a wide range of users, from geography enthusiasts to students looking to bolster their geographical understanding in a fun and interactive way.

The game is suitable for all age groups and knowledge levels. Users are given hints and receiving 'hot' or 'cold' feedback based on the proximity of their guess to the actual location. This immediate feedback loop not only makes the learning process engaging but also reinforces memory retention of geographical facts.

## How to Run and Use

### Installation

Before running Country Guesser, ensure that the GeoTools library is installed, as it is a critical third-party package required for the application. GeoTools is an open-source Java library that provides tools for geospatial data manipulation and visualization. Must be JDK 11 or newer.

To install the necessary dependencies, please use the `pom.xml` file provided with the application. You can install the dependencies using the following command:


### How to Play

Navigate to the \src\main\java\org\geotools\CountryGuesser\run.java file inside the folder and open it on your favorite IDE, next you need to change the shapefilePath variable to the format below

The directory where you put this folder + \CountryGuesser\target\generated-sources\ne_50m_admin_0_countries.shp

Run the file after you changed the shapefilePath. Upon launching, the application will display a world map and randomly select a country as the target. Your task is to guess this country by clicking on the map. After each guess, the application provides feedback indicating whether your guess is 'hotter' (closer to the target country) or 'cooler' (further from the target country). This process continues until the correct country is guessed.

## Limitations and Future Updates

As of now, Country Guesser primarily focuses on country-level geography. In future updates, we plan to include more granular geographical elements such as cities, landmarks, and rivers, to provide a more comprehensive geographical learning experience.

We are also exploring the integration of a leaderboard system to foster a competitive and social element, allowing users to compare their geographical prowess with others.

## Third-Party Packages

The application relies on the following third-party packages:

- GeoTools: A powerful library used for geospatial data processing and visualization in Java.
