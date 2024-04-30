import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)
    {
        System.out.println("Practical task 1.12, Student Max Kalugin, RIBO-04-22, Variant 3");
        Scanner scan = new Scanner(System.in);
        int command;
        String filePath = "";
        Doctor doctor = null;
        while (true) {
            System.out.println("Enter command number: 1 - create object for class Doctor, 2 - save object to file, 3 - load object from file, 4 - exit");
            try {
                command = scan.nextInt();
                switch (command) {
                    case 1:
                        System.out.println("Let's create object for class Doctor.");
                        doctor = createDoctor();
                        System.out.println("Object created - " + doctor.toString());
                        break;
                    case 2:
                        if (doctor != null) {
                            filePath = validatePath(
                                    "Enter file path to save object. Example for Windows: C:\\DoctorObject.txt. Linux: /home/user/DoctorObject.txt",
                                    "Invalid file path. Please enter a valid path."
                            );
                            SaveObjectThread sot = new SaveObjectThread(doctor, filePath);
                            Thread t = new Thread(sot);
                            t.start();
                        } else {
                            System.out.println("Object Doctor not created. Create object first.");
                        }
                        break;
                    case 3:
                        filePath = validatePath(
                                "Enter file path to read object. Example for Windows: C:\\DoctorObject.txt. Linux: /home/user/DoctorObject.txt",
                                "Invalid file path. Please enter a valid path."
                        );
                        if (filePath != "") {
                            LoadObjectThread lot = new LoadObjectThread(filePath);
                            Thread t = new Thread(lot);
                            t.start();
                        } else {
                            System.out.println("Object Doctor not created or saved. Create object first or save object.");
                        }
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong command. Try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scan.next(); // Clear the invalid input
            }
        }
    }
    public static Doctor createDoctor() {
        Scanner scan = new Scanner(System.in);
        String first_name = "";
        String last_name = "";
        String specialization = "";
        int age = 0;
        int salary = 0;

        // Validate first name
        first_name = validateName("Enter first name: ", "First name must contain only alphabetic characters.");

        // Validate last name
        last_name = validateName("Enter last name: ", "Last name must contain only alphabetic characters.");

        // Validate specialization
        specialization = validateName("Enter specialization: ", "Specialization must contain only alphabetic characters.");

        // Validate age
        age = validatePositiveInteger("Enter age: ", "Age must be a positive integer.");

        // Validate salary
        salary = validatePositiveInteger("Enter salary ", "Salary must be a positive integer.");

        return new Doctor(first_name, last_name, specialization, age, salary);


    }

    public static String validatePath(String prompt, String errorMessage) {
        Scanner scan = new Scanner(System.in);
        String path = "";
        while (true) {
            try {
                System.out.println(prompt);
                path = scan.nextLine(); // Allowing spaces in file paths
                if (!path.matches("^[a-zA-Z]:\\\\.*$|^/.*$")) {
                    throw new IllegalArgumentException(errorMessage);
                }
                break; // Break the loop if input is valid
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return path;
    }

    public static String validateName(String prompt, String errorMessage) {
        Scanner scan = new Scanner(System.in);
        String name = "";
        while (true) {
            try {
                System.out.println(prompt);
                name = scan.next();
                if (!name.matches("[a-zA-Z]+")) {
                    throw new IllegalArgumentException(errorMessage);
                }
                break; // Break the loop if input is valid
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return name;
    }

    public static int validatePositiveInteger(String prompt, String errorMessage) {
        Scanner scan = new Scanner(System.in);
        int value = 0;
        while (true) {
            try {
                System.out.println(prompt);
                value = scan.nextInt();
                if (value <= 0) {
                    throw new IllegalArgumentException(errorMessage);
                }
                break; // Break the loop if input is valid
            } catch (IllegalArgumentException | InputMismatchException e) {
                System.out.println(errorMessage);
                scan.next(); // Clear the invalid input
            }
        }
        return value;
    }
}