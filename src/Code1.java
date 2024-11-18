import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * A program that interacts with the OpenWeather API to display weather data, manage a list of
 * favorite cities, and provide user-friendly features like adding, removing, and updating favorite
 * cities.
 */
public class Code1 {
  private static final ArrayList<String> favour = new ArrayList<>();
  private static final int LIMIT = 3;
  private static boolean FLAG = true;

  /**
   * Main method to run the program. Provides a menu for user interaction.
   *
   * @param args command-line arguments (not used in this program)
   */
  public static void main(String[] args) {
    printWelcomeMessage();

    try (Scanner sc = new Scanner(System.in)) {
      while (FLAG) {
        System.out.print("Please enter your command: ");
        String userInput = sc.nextLine();

        switch (userInput) {
          case "1":
            System.out.print("Enter the city name to check its weather: ");
            String cityName = sc.nextLine();
            checkWeather(cityName);
            break;

          case "2":
            addCitiesToFavorites(sc);
            break;

          case "3":
            updateFavoriteCities(sc);
            break;

          case "4":
            listFavoriteCities();
            break;

          case "5":
            System.out.println("See you again!");
            FLAG = false;
            break;

          default:
            System.out.println("Invalid command. Please try again.");
        }
      }
    }
  }

  /**
   * Prints a welcome message with instructions for using the program.
   */
  private static void printWelcomeMessage() {
    System.out.println("*************************************************************************");
    System.out.println("*                     Welcome to the OpenWeather API                    *");
    System.out.println("*         Type 1 to check the current weather of a city                 *");
    System.out.println("*         Type 2 to add cities to your favorite list                    *");
    System.out.println("*         Type 3 to update cities in your favorite list                 *");
    System.out.println("*         Type 4 to check your favorite list                            *");
    System.out.println("*         Type 5 to close the program                                   *");
    System.out.println("*************************************************************************");
  }

  /**
   * Fetches and displays the current weather for a given city by calling the OpenWeather API.
   *
   * @param city the name of the city to fetch weather data for
   */
  private static void checkWeather(String city) {
    String LOCATION = city.toLowerCase();
    LOCATION = LOCATION.replaceAll(" ", "%20");
    String apiKey = "1661712e57e071ff5464c44baa52ab2c";
    String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + apiKey;

    try {
      StringBuilder result = new StringBuilder();
      URL url = new URL(urlString);
      URLConnection urlCon = url.openConnection();
      try (BufferedReader rd = new BufferedReader(new InputStreamReader(urlCon.getInputStream()))) {
        String line;
        while ((line = rd.readLine()) != null) {
          result.append(line);
        }
      }

      // Parse JSON response into a Map
      Gson gson = new Gson();
      Map<String, Object> weatherData = gson.fromJson(result.toString(), Map.class);

      // Display polished output
      System.out.println(formatWeatherOutput(weatherData));
    } catch (MalformedURLException e) {
      System.out.println("Error: Invalid city name.");
    } catch (IOException e) {
      System.out.println("Error: Unable to retrieve weather data. Please try again.");
    }
  }

  /**
   * Formats the weather data from the JSON response for user-friendly display.
   *
   * @param weatherData a Map containing the parsed JSON weather data
   * @return a formatted string representing the weather details
   */
  private static String formatWeatherOutput(Map<String, Object> weatherData) {
    if (weatherData == null || weatherData.isEmpty()) {
      return "No weather data available.";
    }

    StringBuilder output = new StringBuilder();
    output.append("Weather Details:\n");
    output.append("-----------------\n");

    output.append("City: ").append(weatherData.get("name")).append("\n");
    Map<String, Object> mainData = (Map<String, Object>) weatherData.get("main");
    if (mainData != null) {
      output.append("Temperature: ").append(mainData.get("temp")).append(" K\n");
      output.append("Humidity: ").append(mainData.get("humidity")).append("%\n");
    }

    ArrayList<Map<String, Object>> weatherList = (ArrayList<Map<String, Object>>) weatherData.get("weather");
    if (weatherList != null && !weatherList.isEmpty()) {
      Map<String, Object> weather = weatherList.get(0);
      output.append("Weather: ").append(weather.get("description")).append("\n");
    }

    output.append("Wind: ").append(((Map<String, Object>) weatherData.get("wind")).get("speed")).append(" m/s\n");

    return output.toString();
  }

  /**
   * Adds cities to the user's favorite list, ensuring the city is valid and the limit is not
   * exceeded.
   *
   * @param sc a Scanner object to read user input
   */
  private static void addCitiesToFavorites(Scanner sc) {
    int diff = LIMIT - favour.size();
    System.out.printf("You can add up to %d cities to your favorite list one time.%n", LIMIT);
    System.out.print("Enter the number of cities you want to add: ");
    String readIn = sc.nextLine();
    int count = Integer.parseInt(readIn);


    if (favour.size() == LIMIT) {
      System.out.println("Your favorite list is full, please use update command instead.");
      return;
    }

    if (count > LIMIT) {
      System.out.printf("You can only have up to %d cities in your favorites one time.%n", LIMIT);
      return;
    }

    if (count > diff) {
      System.out.printf("You only have %d city slots left in your favorites.%n", diff);
      return;
    }

    String[] input = new String[count];

    System.out.println("Enter the names of the cities:");

    for (int i = 0; i < count; i++) {
      String city = sc.nextLine().toLowerCase();
      if (favour.contains(city)) {
        System.out.println("City already in favorites: " + city);
        continue;
      }


      if (isValidCity(city)) {
        input[i] = city;
        favour.add(input[i]);
        System.out.println("Added: " + city);
      } else {
        System.out.println("Invalid city: " + city);
        i -= 1;
        System.out.println("Please re-enter the city name");
      }
    }
  }

  /**
   * Validates if a city is valid by attempting to fetch its weather data.
   *
   * @param city the name of the city to validate
   * @return true if the city is valid, false otherwise
   */
  private static boolean isValidCity(String city) {
    String LOCATION = city.toLowerCase();
    LOCATION = LOCATION.replaceAll(" ", "%20");
    String apiKey = "1661712e57e071ff5464c44baa52ab2c";
    String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + apiKey;
    try {
      URL url = new URL(urlString);
      URLConnection urlCon = url.openConnection();
      try (BufferedReader rd = new BufferedReader(new InputStreamReader(urlCon.getInputStream()))) {
        // If we can successfully read data, the city is valid
        return true;
      }
    } catch (IOException e) {
      // If an exception occurs, the city is invalid
      return false;
    }
  }

  /**
   * Updates the user's favorite cities by removing and adding cities.
   *
   * @param sc a Scanner object to read user input
   */
  private static void updateFavoriteCities(Scanner sc) {
    System.out.printf("You can update up to %d cities in your favorite list.%n", LIMIT);

    System.out.print("Enter the number of cities you want to remove: ");
    String input = sc.nextLine();
    int count = Integer.parseInt(input);

    if (count > favour.size()) {
      System.out.println("You don't have enough cities to remove.");
      return;
    }

    System.out.println("Enter the names of the cities to remove:");

    for (int i = 0; i < count; i++) {
      String city = sc.nextLine().toLowerCase();
      if (favour.remove(city)) {
        System.out.println("Removed: " + city);
      } else {
        System.out.println("City not found: " + city);
        return;
      }
    }

    System.out.println("Now, add new cities to replace the removed ones.");
    for (int i = 0; i < count; i++) {
      String city = sc.nextLine().toLowerCase();
      if (favour.add(city)) {
        System.out.println("Added: " + city);
      } else {
        System.out.println("City not found: " + city);
        return;
      }
    }
  }

  /**
   * Lists the cities in the user's favorite list and fetches their weather data.
   */
  private static void listFavoriteCities() {
    if (favour.isEmpty()) {
      System.out.println("Your favorite list is empty.");
      return;
    }

    System.out.println("Your favorite cities are:");
    for (String city : favour) {
      System.out.println("- " + city);
      checkWeather(city);
    }
  }
}
