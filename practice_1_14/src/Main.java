import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Practical task 1.14, Student Max Kalugin, RIBO-04-22, Variant 3");
        Scanner scan = new Scanner(System.in);
        int[] sequence = null;

        while (true) {
            System.out.println("Введите последовательность чисел, разделенных пробелом (например, 1 2 3 4 5):");
            String input = scan.nextLine();

            // Проверяем, что ввод не пустой и содержит пробел, если введено больше одного числа.
            if (input.isEmpty() || (input.trim().contains(" ") && input.trim().matches("([0-9]+\\s+)+[0-9]+"))) {
                String[] numbers = input.split("\\s+");
                sequence = new int[numbers.length];

                boolean isInputValid = true;
                try {
                    for (int i = 0; i < numbers.length; i++) {
                        sequence[i] = Integer.parseInt(numbers[i]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: вводите только числа, разделенные пробелом.");
                    isInputValid = false;
                }

                if (isInputValid) {
                    break;
                }
            } else {
                System.out.println("Ошибка: числа должны быть разделены пробелами. Попробуйте еще раз.");
            }
        }

        int[] finalSequence = sequence;
        Thread maxThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " начал искать максимальное число в последовательности.");
            int max = findMax(finalSequence);
            System.out.println("Максимальное число в последовательности: " + max);
            System.out.println(Thread.currentThread().getName() + " закончил искать максимальное число в последовательности.");
        }, "MaxThread");

        Thread minThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " начал искать минимальное число в последовательности.");
            int min = findMin(finalSequence);
            System.out.println("Минимальное число в последовательности: " + min);
            System.out.println(Thread.currentThread().getName() + " закончил искать минимальное число в последовательности.");
        }, "MinThread");

        maxThread.start();
        minThread.start();

        try {
            maxThread.join();
            minThread.join();
        } catch (InterruptedException e) {
            System.out.println("Возникла ошибка: " + e.getMessage());
        }
        scan.close();
    }

    public static int findMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) max = num;
        }
        return max;
    }

    public static int findMin(int[] array) {
        int min = array[0];
        for (int num : array) {
            if (num < min) min = num;
        }
        return min;
    }
}