package sorting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String[] dataTypes = new String[]{"long", "word", "line"};
    private static final String[] sortingTypes = new String[]{"natural", "byCount"};
    private static String dataType = "word";
    private static String sortingType = "natural";
    private static String outputFile = "console";
    private static String inputFile = "console";
    private static Scanner scanner;
    private static FileWriter writer;

    private static void parseArguments(String[] args) {
        String d = "-dataType", s = "-sortingType", out = "-outputFile", in = "-inputFile";
        String noType = "No %s type defined!\n";
        String noFile = "No %s file name defined!\n";
        String print;

        int i = 0;
        while (i < args.length) {
            if (args[i].equals(d) || args[i].equals(s)) {
                print = args[i].equals(d) ? "data" : "sorting";
                if (i == args.length - 1) {
                    System.out.printf(noType, print);
                    return;
                } else if (contains(dataTypes, args[i + 1]) && args[i].equals(d)) {
                    dataType = args[i + 1];
                } else if (contains(sortingTypes, args[i + 1]) && args[i].equals(s)) {
                    sortingType = args[i + 1];
                } else {
                    System.out.printf(noType, print);
                    return;
                }
                i += 2;
            } else if (args[i].equals(out) || args[i].equals(in)) {
                print = args[i].equals(out) ? "output" : "input";
                if (i == args.length - 1 || args[i].equals(d) || args[i].equals(s)) {
                    System.out.printf(noFile, print);
                    return;
                } else if (args[i].equals(out) && args[i + 1].equals(in)) {
                    System.out.printf(noFile, print);
                    return;
                } else if (args[i].equals(in) && args[i + 1].equals(out)) {
                    System.out.printf(noFile, print);
                    return;
                } else if (args[i].equals(out)) outputFile = args[i + 1];
                  else if (args[i].equals(in)) inputFile = args[i + 1];
                i += 2;
            } else {
                System.out.printf("\"%s\" is not a valid parameter. It will be skipped.\n", args[i]);
                i++;
            }
        }

    }

    private static boolean contains(String[] arr, String str) {
        for (String a : arr) if (a.equals(str)) return true;
        return false;
    }

    private static List<Long> getListLong() {
        List<Long> list = new ArrayList<>();
        String num = "";
        while (scanner.hasNext()) {
            try {
                num = scanner.next();
                list.add(Long.parseLong(num));
            } catch (InputMismatchException e) {
                System.out.printf("\"%s\" is not a long. It will be skipped", num);
            }
        }
        Collections.sort(list);
        return list;
    }

    private static List<String> getListWord() {
        List<String> list = new ArrayList<>();
        while (scanner.hasNext()) list.add(scanner.next());
        Collections.sort(list);
        return list;
    }

    private static List<String> getListLine() {
        List<String> list = new ArrayList<>();
        while (scanner.hasNext()) list.add(scanner.nextLine());
        Collections.sort(list);
        return list;
    }

    private static void printNaturalSort(List<String> list, String type) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append(String.format("Total %s: %d.\n", type, list.size()));
        str.append("Sorted data: ");
        list.forEach(num -> str.append(num).append(" "));
        str.append("\n");

        if (outputFile.equals("console")) {
            System.out.print(str);
        } else {
            writer.write(str.toString());
        }
    }

    private static void printByCountSort(List<String> list, String type) throws IOException {
        Map<String, Long> map = new LinkedHashMap<>();
        Map<String, Long> sorted = new LinkedHashMap<>();

        for (String n : list) if (!map.containsKey(n)) map.put(n, count(list, n));

        for (int i = 0; i < map.size(); i++) {
            String k = null;
            long v = Long.MAX_VALUE;
            for (String key : map.keySet()) {
                if (map.get(key) < v && !sorted.containsKey(key)) {
                    v = map.get(key);
                    k = key;
                }
            }
            sorted.put(k, v);
        }

        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Total %s: %d.\n", type, list.size()));
        for (Map.Entry<String, Long> entry : sorted.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            outputString.append(String.format("%s: %d time(s), %d%%\n", key, value, (value * 100) / list.size()));
        }

        if (outputFile.equals("console")) {
            System.out.print(outputString);
        } else {
            writer.write(outputString.toString());
        }
    }

    private static List<String> convertToString(List<Long> list) {
        List<String> l = new ArrayList<>();
        list.forEach(num -> l.add(Long.toString(num)));
        return l;
    }

    private static void sortNaturalLong() throws IOException {
        List<Long> list = getListLong();
        Collections.sort(list);
        printNaturalSort(convertToString(list), "numbers");
    }

    private static void sortNaturalWord() throws IOException {
        List<String> list = getListWord();
        Collections.sort(list);
        printNaturalSort(list, "words");
    }

    private static void sortNaturalLine() throws IOException {
        List<String> list = getListLine();
        Collections.sort(list);
        printNaturalSort(list, "lines");
    }


    private static void sortByCountLong() throws IOException {
        List<Long> list = getListLong();
        Collections.sort(list);
        printByCountSort(convertToString(list), "numbers");
    }

    private static void sortByCountWord() throws IOException {
        List<String> words = getListWord();
        Collections.sort(words);
        printByCountSort(words, "words");
    }

    private static void sortByCountLine() throws IOException {
        List<String> lines = getListLine();
        Collections.sort(lines);
        printByCountSort(lines, "lines");
    }

    private static long count(List<String> words, String w) {
        long count = 0;
        for (String word : words) {
            if (word.equals(w)) count++;
        }
        return count;
    }

    public static void main(final String[] args) throws IOException {

        parseArguments(args);

        if (inputFile.equals("console")) scanner = new Scanner(System.in);
        else {
            File input = new File(inputFile);
            scanner = new Scanner(input);
        }

        if (!outputFile.equals("console")) {
            File output = new File(outputFile);
            writer = new FileWriter(output);
        }

        switch (sortingType) {
            case "natural": {
                if (dataType.equals("long")) sortNaturalLong();
                if (dataType.equals("word")) sortNaturalWord();
                if (dataType.equals("line")) sortNaturalLine();
                break;
            }
            case "byCount": {
                if (dataType.equals("long")) sortByCountLong();
                if (dataType.equals("word")) sortByCountWord();
                if (dataType.equals("line")) sortByCountLine();
                break;
            }
        }

        if (!outputFile.equals("console")) writer.close();
        scanner.close();
    }
}
