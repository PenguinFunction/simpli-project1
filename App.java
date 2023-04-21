import java.util.Scanner;
import java.util.HashMap;
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
                            System.out.println("Contents of " + DIRECTORY);

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
                            System.out.println("Adding file");
                            break;
                        }
                        case 2: {
                            System.out.println("Deleting file");
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
}