import java.util.Scanner;
import java.io.File;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import java.io.IOException;
import java.util.InputMismatchException;

public class App {
    private static String DIRECTORY = "CompanyLockersFileSystem";

    public static void main(String[] args) {
        System.out.println("Project: Prototype File Management Application for Company Lockers Pvt. Ltd.");
        System.out.println("Developer: Isaiah Chin");

        HashMap<Integer, String> validMenuOptions = new HashMap<Integer, String>() {
            {
                put(1, "List all files");
                put(2, "Enter database");
                put(3, "Quit\n");
            }
        };

        Scanner sc = new Scanner(System.in);
        boolean isApplicationRunning = true;
        do {
            // Print Menu
            System.out.println("\nPlease select an option (Enter a number then press enter)");
            validMenuOptions.forEach((key, value) -> {
                System.out.println("[" + key + "] " + value);
            });

            try {
                System.out.print("> ");
                int userOption = sc.nextInt();

                // Check if user input is allowed
                boolean isInputValid = false;
                for (HashMap.Entry<Integer, String> option : validMenuOptions.entrySet()) {
                    if (userOption == option.getKey()) {
                        isInputValid = true;
                        break;
                    }
                }

                if (isInputValid == false) {
                    throw new InputMismatchException();
                } else {
                    switch (userOption) {
                        case 1: {
                            // List all files in directory
                            System.out.println("Contents of " + DIRECTORY + "\n");
                            File[] files = new File(DIRECTORY).listFiles();
                            Arrays.sort(files);

                            for (int i = 0; i < files.length; i++) {
                                if (files[i].isDirectory()) {
                                    System.out.print("<DIR>");
                                }
                                System.out.print("\t" + files[i].getName() + "\n");
                            }
                            break;
                        }
                        case 2: {
                            sc.nextLine();
                            enterDatabase(sc);
                            break;
                        }
                        case 3: {
                            isApplicationRunning = false;
                            break;
                        }
                        default: {
                            System.out.println("Invalid option, please try again.");
                            break;
                        }
                    }
                }
            } catch (InputMismatchException IME) {
                System.out.println("Invalid option, please try again.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } while (isApplicationRunning != false);

        // Close scanner
        sc.close();
    }

    /**
     * Handles 2nd Menu option sub-menu
     * 
     * @param sc Scanner object
     */
    private static void enterDatabase(Scanner sc) {
        HashMap<Integer, String> validFileMenuOptions = new HashMap<Integer, String>() {
            {
                put(1, "Add a file");
                put(2, "Delete a file");
                put(3, "Search for a file");
                put(4, "Return to Main Menu\n");
            }
        };

        boolean isInFileMenu = true;
        do {
            // Print sub-menu
            System.out.println("\nPlease select an option (Enter a number then press enter)");
            validFileMenuOptions.forEach((key, value) -> {
                System.out.println("[" + key + "] " + value);
            });

            try {
                System.out.print("> ");
                int userOption = sc.nextInt();

                // Check if user input is allowed
                boolean isInputValid = false;
                for (HashMap.Entry<Integer, String> option : validFileMenuOptions.entrySet()) {
                    if (userOption == option.getKey()) {
                        isInputValid = true;
                        break;
                    }
                }

                if (isInputValid == false) {
                    throw new InputMismatchException();
                } else {
                    switch (userOption) {
                        case 1: {
                            sc.nextLine();
                            addFile(sc);
                            break;
                        }
                        case 2: {
                            sc.nextLine();
                            deleteFile(sc);
                            break;
                        }
                        case 3: {
                            System.out.println("Searching for file");
                            break;
                        }
                        case 4: {
                            isInFileMenu = false;
                            break;
                        }
                        default: {
                            System.out.println("Invalid option, please try again.");
                            break;
                        }
                    }
                }
            } catch (InputMismatchException IME) {
                System.out.println("Invalid option, please try again.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } while (isInFileMenu != false);
    }

    /**
     * Helper function that adds a file to the directory
     * 
     * @param sc Scanner object
     */
    private static void addFile(Scanner sc) {
        boolean isAddingFile = true;
        do {
            try {
                System.out.print("\nEnter a name for a new file: ");
                String filename = sc.nextLine().trim();

                // Check if file can be created
                if (!isValidFileName(filename)) {
                    System.out.println("Invalid file name, please try another name.");
                } else {
                    // Create and add new file to directory
                    File newFile = new File(DIRECTORY + "\\" + filename);
                    if (newFile.createNewFile()) {
                        System.out.println("Created " + filename + " in " + DIRECTORY);
                        isAddingFile = false;
                    } else {
                        System.out.println("This file already exists, please try another name.");
                    }
                }
            } catch (IOException IOe) {
                System.out.println("Error: Could not create file");
            }
        } while (isAddingFile != false);
    }

    /**
     * Helper function that deletes a file from the directory
     * 
     * @param sc Scanner object
     */
    private static void deleteFile(Scanner sc) {
        boolean isDeletingFile = true;
        do {
            try {
                System.out.print("\nWhich file do you want to delete?: ");
                String filename = sc.nextLine().trim();

                if (!isValidFileName(filename)) {
                    System.out.println("Invalid file name, please try another name.");
                } else {
                    ArrayList<String> filesList = new ArrayList<String>();

                    // Get all files in the directory
                    File[] files = new File(DIRECTORY).listFiles();

                    for (File file : files) {
                        filesList.add(file.getName());
                    }

                    // Sort file names alphabetically
                    Collections.sort(filesList);

                    // Search for file to be deleted
                    int index = Collections.binarySearch(filesList, filename);

                    if (index >= 0) {
                        File fileToDelete = new File(DIRECTORY + "\\" + filename);

                        if (fileToDelete.delete()) {
                            System.out.println("Successfully deleted " + filename + " from " + DIRECTORY);
                        } else {
                            System.out.println("Error: Could not delete file");
                        }

                        isDeletingFile = false;
                    } else {
                        System.out.println("Could not find " + filename);
                        isDeletingFile = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: Could not delete file");
            }
        } while (isDeletingFile != false);
    }

    /**
     * Helper function to check the validity of file names
     * 
     * @param filename Name of file
     * @return True if filename is valid
     */
    private static boolean isValidFileName(String fileName) {
        String regex = "^(?!^(PRN|AUX|CLOCK\\$|NUL|CON|COM\\d|LPT\\d)(?:\\..*)?$)[^<>:\"/\\\\|?*\\x00-\\x1F]+$";
        return Pattern.matches(regex, fileName);
    }
}