import java.util.Scanner;
public class BankInput {
    private static Scanner sc = new Scanner(System.in);

    public static String getString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    public static double getDouble(String message) {
        System.out.print(message);
        double value = sc.nextDouble();
        sc.nextLine(); // clear buffer
        return value;
    }

    public static int getInt(String message) {
        System.out.print(message);
        int value = sc.nextInt();
        sc.nextLine(); // clear buffer
        return value;
    }
}
