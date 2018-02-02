package alvarez.jose.anagrampreparation;

// As an example activity using HashMaps, create a program
// (not necessarily an Android app - command-line is fine) that will take in
// a three-letter country code (see ISO-3166) and will return the full name of the country
// to which it belongs. For example:

//Input | Output
//----- | ----------------------------------------------------
// GBR  | United Kingdom of Great Britain and Northern Ireland
// IDN  | Indonesia
// IND  | India

// As an extension, if the input is greater than 3 letters, consider it as the name of a country,
// and return the three-letter code for it. Write a helpful error message if the input is neither a
// valid code nor a country name.

// Useful documentation
// https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
// https://docs.oracle.com/javase/7/docs/api/java/util/HashSet.html
// https://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html
// http://pages.cs.wisc.edu/~hasti/cs368/JavaTutorial/NOTES/JavaIO_Scanner.html

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AnagramPreparationProgram {
    public static void main(String[] args) {

        String code;
        String country;
        String input;
        Scanner scanner = new Scanner(System.in);

        System.out.println("HELLO WORLD");

        Map countriesMap = new HashMap<>();
        countriesMap.put("GTM","Guatemala");
        countriesMap.put("GBR", "United Kingdom of Great Britain and Northern Ireland");
        countriesMap.put("IDN", "Indonesia");
        countriesMap.put("IND", "India");

        while(true) {
            System.out.println("Add a country code and press enter:");
            code = scanner.next();
            System.out.println("Add a corresponding country and press enter:");
            country = scanner.next();

            countriesMap.put(code, country);

            System.out.println("Do you wanna add another country? y/n");
            input = scanner.next();
            if (input.toUpperCase().equals("N")) {
                break;
            }
        }

        while(true) {
            System.out.println("Now enter a country code to get a country name, or vice versa");
            input = scanner.next();

            if (input.length() == 3) {

                code = input; // For the sake of readability
                country = countriesMap.get(code).toString();
                System.out.println("The country is " + country);

            } else if (input.length() > 3) {

                country = input; // For the sake of readability.

                // Documentation: https://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value
                for(Object codeKey: countriesMap.keySet()) {
                    if(countriesMap.get(codeKey).equals(country)) {
                        System.out.println("The code is " + code.toString());
                    }
                }

            } else {
                System.out.println("Your input is not valid.");
            }

            System.out.println("Do you wanna know about another countries? y/n");
            input = scanner.next();
            if (input.toUpperCase() == "N") {
                break;
            }

        }


    }
}
