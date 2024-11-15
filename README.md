# GCS_CA

GCS Coding Assessment from Yicheng Jiang.

Two coding activity files are both in side the src folder

## Code1 is Making API Calls

### Future Upgrades(2/2):

1. (Achieved)~~Adding the edge cases for checking the incorrect city names.~~

2. (Achieved)~~Create Map to store the JSON date with better outputs.~~

### Edge Case 1: Invalid City Name

Fix Method:

Add a wrong name to the favourite list
![img.png](img.png)

Output wrong message
![img_1.png](img_1.png)

Use update feature to replace the invalid city
![img_2.png](img_2.png)

### Edge Case 2: Outputs Optimization 

Fix Method:

Add gson package
```
// Parse JSON response into a Map
      Gson gson = new Gson();
      Map<String, Object> weatherData = gson.fromJson(result.toString(), Map.class);
```
![img_5.png](img_5.png)

## Code2 is Pseudocode to Code

### Inputs handled:
![img_3.png](img_3.png)

### Edge Case: Invalid Inputs
![img_4.png](img_4.png)

### Time and Space Complexity:
1. Time: 𝑂(𝑛log𝑛)
2. Space: 𝑂(𝑛)