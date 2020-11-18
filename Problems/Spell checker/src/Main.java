import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<String> words = new HashSet<>();
        Set<String> notFound = new TreeSet<>();

        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            words.add(scanner.nextLine().toLowerCase());
        }

        int m = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < m; i++) {
            for (String word : scanner.nextLine().trim().toLowerCase().split("\\s+")) {
                if (!words.contains(word)) notFound.add(word);
            }
        }

        for (String word : notFound) System.out.println(word);
    }
}