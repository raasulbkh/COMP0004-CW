package uk.ac.ucl.model;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Model
{
  private DataFrame dataFrame;
  private DataLoader dataLoader;

  public Model(){
    this.dataFrame = new DataFrame();
    this.dataLoader = new DataLoader();
  }

  public void loadDataFrame(String filePath) {
    this.dataFrame = dataLoader.loadDataFrame(filePath);
  }


  //Comeback if time allows to improve sorting (probably use stream???)
  public Map<String, Integer> getPatientNames(){
    //Temporary Map to store unsorted names and their indices
    Map<String, Integer> unsortedPatientNames = new HashMap<>();
    int rowCount = dataFrame.getRowCount("FIRST");

    //Fill the map with unsorted patient names and their indices
    for (int i = 0; i < rowCount; i++) {
      String firstName = dataFrame.getValue("FIRST", i);
      String lastName = dataFrame.getValue("LAST", i);
      String fullName = firstName + " " + lastName;
      String cleanName = removeNumbers(fullName); //get rid of numbers in the names to format them
      unsortedPatientNames.put(cleanName, i);
    }

    //Convert map keys (i.e. patients' name) to a list and sort them in an alphabetical order
    List<String> sortedNames = new ArrayList<>(unsortedPatientNames.keySet());
    sortedNames.sort(String::compareToIgnoreCase);

    //Create a new map and maintain the order of sorted names based on insertion
    // order(i.e. linked Hashmap)
    Map<String, Integer> sortedPatientNamesWithIndices = new LinkedHashMap<>();
    for (String name : sortedNames) {
      sortedPatientNamesWithIndices.put(name, unsortedPatientNames.get(name));
    }

    return sortedPatientNamesWithIndices;
  }

  private String removeNumbers(String str) {
    return str.replaceAll("\\d+", ""); //removes numbers from all the names
  }

  public Map<String, Integer> searchFor(String keyword) {
    Map<String, Integer> searchResults = new TreeMap<>(); // Used TreeMap to keep alphabetical order when displaying search results
    int rowCount = dataFrame.getRowCount("FIRST");

    for (int i = 0; i < rowCount; i++) {
      String firstName = dataFrame.getValue("FIRST", i).replaceAll("\\d+", "");
      String lastName = dataFrame.getValue("LAST", i).replaceAll("\\d+", "");
      String fullName = firstName + " " + lastName;

      if (fullName.toLowerCase().contains(keyword.toLowerCase())) {
        searchResults.put(fullName, i);
      }
    }
    return searchResults;
  }

  public Map<String, String> getPatientDetails(int rowIndex) {
    Map<String, String> details = new HashMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //Extracted and stored the birthdate
    String birthdateStr = dataFrame.getValue("BIRTHDATE", rowIndex);
    details.put("Birthdate", birthdateStr);

    //Concatenated prefix, first name, last name and suffix
    String prefix = dataFrame.getValue("PREFIX", rowIndex).trim();
    String firstName = dataFrame.getValue("FIRST", rowIndex).replaceAll("\\d+", "").trim();
    String lastName = dataFrame.getValue("LAST", rowIndex).replaceAll("\\d+", "").trim();
    String suffix = dataFrame.getValue("SUFFIX", rowIndex).trim();
    String fullName = String.format("%s %s %s %s", prefix, firstName, lastName, suffix).trim().replaceAll("\\s+", " ");
    details.put("Name", fullName);

    //Formatted the gender
    String gender = dataFrame.getValue("GENDER", rowIndex).trim();
    details.put("Gender", "F".equals(gender)? "Female" : "M".equals(gender) ? "Male" : "");

    //Formatted the Marital Status similarly
    String maritalCode = dataFrame.getValue("MARITAL", rowIndex).trim();
    String maritalStatus = "";
    switch (maritalCode) {
      case "M":
        maritalStatus = "Married";
        break;
      case "S":
        maritalStatus = "Single";
        break;
    }
    //Do not display Marital Status if not available
    if (!maritalStatus.isEmpty()) {
      details.put("Marital Status", maritalStatus);
    }

    //Concatenated address, city, state and zipcode
    details.put("Address", String.format("%s, %s, %s, %s",
            dataFrame.getValue("ADDRESS", rowIndex),
            dataFrame.getValue("CITY", rowIndex),
            dataFrame.getValue("STATE", rowIndex),
            dataFrame.getValue("ZIP", rowIndex)));

    // Determine Age (if alive) or Death Date (if dead)
    LocalDate birthDate = LocalDate.parse(birthdateStr, formatter);
    String deathDateStr = dataFrame.getValue("DEATHDATE", rowIndex);
    if (deathDateStr == null || deathDateStr.isEmpty()) {
      LocalDate now = LocalDate.now();
      int age = Period.between(birthDate, now).getYears();
      details.put("Age", String.valueOf(age));
    } else {
      details.put("Death Date", deathDateStr);  // Store death date if present
    }

    //Format and store other details as well
    details.put("Race", formatString(dataFrame.getValue("RACE", rowIndex)));
    details.put("Ethnicity", formatString(dataFrame.getValue("ETHNICITY", rowIndex)));
    details.put("Maiden Name", dataFrame.getValue("MAIDEN", rowIndex).replaceAll("\\d+", "").trim());
    details.put("SSN", dataFrame.getValue("SSN", rowIndex).trim());
    details.put("Passport", dataFrame.getValue("PASSPORT", rowIndex).trim());
    details.put("Drivers License", dataFrame.getValue("DRIVERS", rowIndex).trim());
    details.put("Birthplace", dataFrame.getValue("BIRTHPLACE", rowIndex).trim());

    return details;
  }

  //Method to format Race and Ethnicity (asian_american =>Asian-American, white => White)
  private String formatString(String input) {
    if (input == null || input.isEmpty()) return "N/A";
    input = input.replace("_", "-").toLowerCase();
    char[] chars = input.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    for (int i = 1; i < chars.length; i++) {
      if (chars[i] == '-') {
        i++;
        if (i < chars.length) {
          chars[i] = Character.toUpperCase(chars[i]);
        }
      }
    }
    return new String(chars);
  }

  public List<String> getListOfPatientsSortedByAge() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate now = LocalDate.now();
    List<String[]> livingPatients = new ArrayList<>();

    for (int i = 0; i < dataFrame.getRowCount("BIRTHDATE"); i++) {
      String deathDate = dataFrame.getValue("DEATHDATE", i);
      if (deathDate == null || deathDate.isEmpty()) {
        LocalDate birthDate = LocalDate.parse(dataFrame.getValue("BIRTHDATE", i), formatter);
        int age = Period.between(birthDate, now).getYears();
        String firstName = removeNumbers(dataFrame.getValue("FIRST", i));
        String lastName = removeNumbers(dataFrame.getValue("LAST", i));
        String name = firstName + " " + lastName;
        livingPatients.add(new String[]{name, String.valueOf(age), String.valueOf(i)}); // Include index
      }
    }

    //Sorted by descending order using the lambda expression - comparator
    livingPatients.sort((patient1, patient2) -> Integer.compare(Integer.parseInt(patient2[1]), Integer.parseInt(patient1[1])));

    List<String> sortedPatients = new ArrayList<>();
    for (String[] patient : livingPatients) {
      // The patient's index is included in the output for constructing clickable links
      sortedPatients.add(patient[0] + " - " + patient[1] + " years old - " + patient[2]);
    }
    return sortedPatients;
  }

  public List<String> getCities() {
    Set<String> citiesSet = new HashSet<>();
    for (int i = 0; i < dataFrame.getRowCount("CITY"); i++) {
      String city = dataFrame.getValue("CITY", i);
      if (city != null && !city.isEmpty()) {
        citiesSet.add(city);
      }
    }
    // Convert the set to a list and sort it alphabetically
    List<String> citiesList = new ArrayList<>(citiesSet);
    Collections.sort(citiesList);
    return citiesList;
  }

  public List<String> getPatientsInCity(String city) {
    List<String> patients = new ArrayList<>();
    for (int i = 0; i < dataFrame.getRowCount("CITY"); i++) {
      if (city.equals(dataFrame.getValue("CITY", i))) {
        String name = removeNumbers(dataFrame.getValue("FIRST", i)) + " " + removeNumbers(dataFrame.getValue("LAST", i));
        patients.add(name + "-" + i); // Append index for linking to patient details
      }
    }
    patients.sort(String::compareToIgnoreCase); // Sort alphabetically
    return patients;
  }


  public Map<String, Double> getAgeDistribution() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate now = LocalDate.now();
    int totalLivingPatients = 0;

    // Used TreeMap with a custom comparator to sort age groups to
    // label the graph in ascending order in terms of age groups
    Map<String, Integer> ageDistribution = new TreeMap<>((o1, o2) -> {
      int startAge1 = Integer.parseInt(o1.split("-")[0]);
      int startAge2 = Integer.parseInt(o2.split("-")[0]);
      return Integer.compare(startAge1, startAge2);
    });

    for (int i = 0; i < dataFrame.getRowCount("BIRTHDATE"); i++) {
      String deathDateStr = dataFrame.getValue("DEATHDATE", i);
      if (deathDateStr == null || deathDateStr.isEmpty()) {
        String birthdateStr = dataFrame.getValue("BIRTHDATE", i);
        LocalDate birthDate = LocalDate.parse(birthdateStr, formatter);
        int age = Period.between(birthDate, now).getYears();

        String ageRange = (age / 10) * 10 + "-" + ((age / 10) * 10 + 9);
        ageDistribution.putIfAbsent(ageRange, 0);
        ageDistribution.put(ageRange, ageDistribution.get(ageRange) + 1);
        totalLivingPatients++;
      }
    }

    // Calculate percentages
    Map<String, Double> ageDistributionPercentages = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : ageDistribution.entrySet()) {
      double percentage = (double) entry.getValue() / totalLivingPatients * 100;
      ageDistributionPercentages.put(entry.getKey(), Math.round(percentage * 100.0) / 100.0); // Rounds to 2 decimal places
    }

    return ageDistributionPercentages;
  }

  public void addPatient(Map<String, String> patientData) throws NoSuchElementException {
    // Iterate through each piece of patient data, adding it to the respective column
    for (Map.Entry<String, String> dataEntry : patientData.entrySet()) {
      String columnName = dataEntry.getKey();
      String value = dataEntry.getValue();
      if (!this.dataFrame.getColumnNames().contains(columnName)) {
        continue;
      }

      // Now we add the value to the existing column in the DataFrame.
      this.dataFrame.addValue(columnName, value);
    }
  }


  public void saveToCSV(String fileName) throws IOException {
    // Construct the full path where the new CSV will be saved and write the column headers
    String filePath = "data/" + fileName;
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      List<String> columnNames = this.dataFrame.getColumnNames();
      writer.write(String.join(",", columnNames));
      writer.newLine();
      int numRows = this.dataFrame.getRowCount(columnNames.get(0));
      // Write each row of data
      for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
        List<String> rowValues = new ArrayList<>();
        for (String columnName : columnNames) {
          String value = this.dataFrame.getValue(columnName, rowIndex);
          rowValues.add(value.replace(",", ";"));
        }
        writer.write(String.join(",", rowValues));
        writer.newLine();
      }
    }
  }

  public DataFrame getDataFrame() {
    return dataFrame;
  }
}

