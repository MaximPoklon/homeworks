import java.nio.file.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Path inputFilePath = null;
    static Path outputFilePath = null;
    static byte[] gamma = null;
    public static void main(String[] args) {
        System.out.println("Practical task 1.10, Student Max Kalugin, RIBO-04-22, Variant 3");
        Scanner scanner = new Scanner(System.in);
        int command;

        while (true) {
            System.out.println("Enter command number: 1 - create test file, 2 - process test file with XOR, 3 - process custom file with XOR, 4 - read processed file, 5 - exit");
            try {
                command = scanner.nextInt();
                scanner.nextLine();
                switch (command) {
                    case 1:
                        createTestFile(scanner);
                        break;
                    case 2:
                        if (inputFilePath != null && gamma != null) {
                            processFileWithXOR();
                        } else {
                            System.out.println("No file path or gamma set. Please set them first.");
                        }
                        break;
                    case 3:
                        setInputFilePathAndGamma(scanner);
                        if (inputFilePath != null && gamma != null) {
                            processFileWithXOR();
                        }
                        break;
                    case 4:
                        readProcessedFile();
                        break;
                    case 5:
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Incorrect command number. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static void createTestFile(Scanner scanner) {
        System.out.println("Enter the path to create the test file: Example for Windows: C:\\Example, Linux: /home/user/Example");
        try {
            inputFilePath = Paths.get(scanner.nextLine()).toAbsolutePath().normalize();
            Files.write(inputFilePath, new byte[]{0x1A, 0x2B, 0x3C, 0x3C, 0x44, 0x44}); // Write predefined data

            setGamma(scanner);
            System.out.println("Test file created at: " + inputFilePath + "\n" + " with data: 0x1A, 0x2B, 0x3C, 0x3C, 0x44, 0x44");
        } catch (IOException | InvalidPathException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    private static void readProcessedFile() {
        if (outputFilePath != null && Files.exists(outputFilePath)) {
            try {
                byte[] fileContent = Files.readAllBytes(outputFilePath);
                System.out.println("Content of the processed file:");
                for (byte b : fileContent) {
                    System.out.print(String.format("%02X ", b));
                }
                System.out.println(); // Newline for clean output
            } catch (IOException e) {
                System.out.println("There was an error reading the processed file: " + e.getMessage());
            }
        } else {
            System.out.println("Processed file not found. Please process a file first.");
        }
    }

    private static void setInputFilePathAndGamma(Scanner scanner) {
        System.out.println("Enter the path of the existing input file: Example for Windows: C:\\Example, Linux: /home/user/Example");
        try {
            Path testPath = Paths.get(scanner.nextLine()).toAbsolutePath().normalize();
            if (Files.isRegularFile(testPath)) {
                inputFilePath = testPath;
                setGamma(scanner); // Prompt user to set gamma
            } else {
                System.out.println("The path does not point to a regular file. Please try again.");
            }
        } catch (InvalidPathException e) {
            System.out.println("The path is not valid. Please enter a correct file path.");
        }
    }

    private static void setGamma(Scanner scanner) {
        System.out.println("Enter the gamma as hex-string (e.g., ABCD):");
        String hexGamma = scanner.nextLine();
        if (hexGamma.matches("^[0-9A-Fa-f]+$") && hexGamma.length() % 2 == 0) {
            gamma = hexStringToByteArray(hexGamma);
        } else {
            System.out.println("Invalid hex string. Please ensure it contains only hex characters and has an even length.");
            gamma = null;
        }
    }

    private static void processFileWithXOR() {
        try {
            byte[] fileContent = Files.readAllBytes(inputFilePath);
            byte[] result = xorWithGamma(fileContent, gamma);

            outputFilePath = inputFilePath.getParent().resolve(inputFilePath.getFileName().toString() + ".xor");
            Files.write(outputFilePath, result);

            System.out.println("The file has been processed and saved as " + outputFilePath);
        } catch (IOException e) {
            System.out.println("There was an error processing the file: " + e.getMessage());
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Error hexstring: " + s);
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] xorWithGamma(byte[] data, byte[] gamma) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ gamma[i % gamma.length]);
        }
        return result;
    }
}