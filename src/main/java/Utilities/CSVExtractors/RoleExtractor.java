package Utilities.CSVExtractors;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Entities.Business.Role.Role;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class RoleExtractor {

    public static List<Role> parseRolesFromCSV(String filePath) throws IOException {
        List<Role> roles = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            // Skip header row if present
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    // Extract data from each CSV line
                    String roleName = line[0]; // Assuming role name is in the first column
                    Long filmId = Long.parseLong(line[1]); // Assuming film ID is in the second column
                    Long actorId = Long.parseLong(line[2]); // Assuming actor ID is in the third column

                    // Create Role object without setting IDs (database-managed)
                    Role role = new Role(roleName, null, null);

                    // Potentially set film and actor references later using their IDs

                    roles.add(role);
                } catch (NumberFormatException e) {
                    // Handle invalid number formats during parsing
                    System.err.println("Error parsing line: " + e.getMessage());
                } catch (Exception e) {
                    // Handle other potential exceptions
                    System.err.println("Error parsing line: " + e.getMessage());
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return roles;
    }
}