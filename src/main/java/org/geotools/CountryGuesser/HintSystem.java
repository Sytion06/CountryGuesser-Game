package org.geotools.CountryGuesser;
import java.util.Random;

public class HintSystem {

    private String[][] countryHints;
    private Random random;
    private String selectedCountryName;
    private String[] selectedCountryHints;

    public HintSystem() {
        countryHints = new String[][] { { "Country", "Continent", "First Hint", "Second Hint", "Third Hint" },
        { "People's Republic of China", "Asia", "Great Wall", "Yangtze River", "Forbidden City" },
        { "India", "Asia", "Ganges River", "Taj Mahal", "Himalayan Mountains" },
        { "United States", "North America", "World's strongest military", "Grand Canyon", "Mount Rushmore" },
        { "Indonesia", "Asia", "Bali temples", "Komodo dragons", "Rice terraces" },
        { "Russia", "Europe", "Vodka", "Matryoshka dolls", "Ballet" },
        { "Mexico", "North America", "Mayan pyramids", "Mariachi music", "Aguascalientes festival" },
        { "Japan", "Asia", "Samurai", "Cherry blossoms", "Shinto shrines" },
        { "Philippines", "Asia", "Chocolate Hills", "Sinulog Festival", "Tarsier sanctuary" },
        { "Egypt", "Africa", "Nile River", "Pyramids", "Hieroglyphics" },
        { "Vietnam", "Asia", "Mekong Delta", "Halong Bay", "Ao Dai traditional dress" },
        { "Democratic Republic of the Congo", "Africa", "Congo Basin", "Okapi Wildlife Reserve", "African rainforest" },
        { "Turkey", "Asia", "Anatolian cuisine", "Hagia Sophia", "Whirling Dervishes" },
        { "Iran", "Asia", "Persepolis ruins", "Caspian Sea", "Zoroastrianism" },
        { "Germany", "Europe", "Oktoberfest", "Black Forest", "Home of the autobahn" },
        { "Thailand", "Asia", "Thai cuisine", "Elephant sanctuaries", "Songkran festival" },
        { "United Kingdom", "Europe", "Stonehenge", "Fish and chips", "Hadrian's Wall" },
        { "France", "Europe", "Eiffel Tower", "Louvre Museum", "Fashion capital of the world" },
        { "Italy", "Europe", "Roman Colosseum", "Venetian canals", "Renaissance art" },
        { "South Africa", "Africa", "Safari", "Table Mountain", "Apartheid history" },
        { "Republic of Korea", "Asia", "K-pop", "Hanbok dress", "Joseon dynasty" },
        { "Colombia", "South America", "Emeralds", "Coffee plantations", "El Dorado legend" },
        { "Spain", "Europe", "Flamenco dance", "La Tomatina festival", "Spanish Armada" },
        { "Uganda", "Africa", "Source of the Nile", "Mountain gorillas", "Pearl of Africa" },
        { "Argentina", "South America", "Tango", "Pampas grasslands", "Che Guevara" },
        { "Sudan", "Africa", "Ancient pyramids", "Nubian culture", "Has the largest number of pyramids in the world" },
        { "Ukraine", "Europe", "Chornobyl", "Carpathian Mountains", "Cossack heritage" },
        { "Iraq", "Asia", "Mesopotamian civilization", "Babylon ruins", "Kurdish culture" },
        { "Afghanistan", "Asia", "Buzkashi sport", "Silk Road", "Hindu Kush mountains" },
        { "Canada", "North America", "Maple syrup", "Niagara Falls", "Indigenous culture" },
        { "North Korea", "Asia", "Has the most militarized border in the world", "Large nuclear weapons program",
            "Pyongyang metro" },
        { "Australia", "Oceania", "Great Barrier Reef", "Outback", "Aboriginal art" },
        { "Sweden", "Europe", "Northern lights", "Viking heritage", "Midsummer festivities" },
        { "Austria", "Europe", "Alpine ski resorts", "Vienna's coffeehouses", "Habsburg Empire" },
        { "Switzerland", "Europe", "Alpine peaks", "Chocolate craftsmanship", "Neutrality policy" },
        { "Greece", "Europe", "Ancient Olympia", "Mediterranean cuisine", "Hellenistic era" },
        { "Jordan", "Asia", "Petra ruins", "Dead Sea", "Bedouin traditions" },
        { "Portugal", "Europe", "Port wine", "Lisbon Fado music", "Age of Discovery" },
        { "Laos (Lao PDR)", "Asia", "Luang Prabang", "Plain of Jars", "Mekong River" },
        { "Norway", "Europe", "Fjords", "Viking ships", "Aurora Borealis" },
        { "Ireland", "Europe", "Cliffs of Moher", "Dublin pubs", "Celtic history" },
        { "Georgia", "Asia", "Caucasus Mountains", "Tbilisi architecture", "Wine culture" },
        { "Dominican Republic", "North America", "Punta Cana beaches", "Merengue music", "Santo Domingo history" },
        { "Slovakia", "Europe", "High Tatras", "Bratislava Castle", "Velvet Revolution" },
        { "Cuba", "North America", "Havana salsa", "Cuban cigars", "Revolutionary history" },
        { "Palestine", "Asia", "Jerusalem holy city", "Dead Sea",
            "It is a holy land in Christianity, Islam and Judaism" },
        { "New Zealand", "Oceania", "Kiwi bird", "Maori culture", "Aoraki Mount Cook" },
        { "Madagascar", "Africa", "Lemurs", "Baobab trees", "Tsingy de Bemaraha" },
        { "Singapore", "Asia", "Marina Bay Sands", "Hawker centres", "City-state" },
        { "Fiji", "Oceania", "Yasawa Islands", "Suva capital", "Kava ceremony" },
        { "Bahamas", "North America", "Nassau capital", "Exuma Cays", "Pineapple festivals" },
        { "Iceland", "Europe", "Reykjavik", "Northern Lights", "Geysir hot springs" },
      
    };
        random = new Random();
        selectRandomCountry();
    }

    /**
     * Randomly selects a country and its hints.
     */
    private void selectRandomCountry() {
        int index = random.nextInt(countryHints.length - 1) + 1;
        selectedCountryName = countryHints[index][0];
        selectedCountryHints = new String[] {
            countryHints[index][2],
            countryHints[index][3],
            countryHints[index][4]
        };
    }

    public String getHint(int attempts) {
        if (attempts >= 6) {
            return selectedCountryHints[2];
        } else if (attempts >= 4) {
            return selectedCountryHints[1];
        } else if (attempts >= 2) {
            return selectedCountryHints[0];
        }
        return null;  // Return null if there are no hints yet
    }

    public String getSelectedCountryName() {
        return selectedCountryName;
    }
}