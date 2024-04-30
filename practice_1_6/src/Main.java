import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Practical task 1.6, Student Max Kalugin, RIBO-04-22, Variant 3");
        String recipe = "";
        do {
            System.out.println("Enter the recipe (string only):");
            recipe = scanner.nextLine();

            // validating recipe to be only string
            if (isValidRecipe(recipe)) {
                System.out.println("Invalid recipe! Please enter a valid string.");
            }
        } while (isValidRecipe(recipe));

        Doctor doctor1 = new ReverseDoctor();
        Doctor doctor2 = new CustomDoctor();

        System.out.println("Reversed recipe: " + doctor1.writeRecipe(recipe));
        System.out.println("Custom recipe: " + doctor2.writeRecipe(recipe));


    }
    public static class Doctor {
        public String writeRecipe(String recipe) {
            return recipe;
        }
    }

    private static boolean isValidRecipe(String recipe) {
        return recipe.isEmpty() || recipe.matches(".*\\d.*");
    }

    public static class ReverseDoctor extends Doctor {
        @Override
        public String writeRecipe(String recipe) {
            return new StringBuilder(recipe).reverse().toString();
        }
    }

    public static class CustomDoctor extends Doctor {
        @Override
        public String writeRecipe(String recipe) {
            return recipe.toUpperCase(Locale.ROOT);
        }
    }
}