import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        int n = scanner.nextInt();
        for (int i = 0; i <= n; i++) {
            String str = scanner.nextLine();
            if (!list.contains(str)) list.add(str);
        }

        Collections.sort(list);
        list.forEach(System.out::println);
    }
}