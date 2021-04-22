package cphbusiness.ufo.letterfrequencies;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        double[] runTimes = new double[10];
        String fileName = System.getProperty("user.dir") + "/src/main/resources/FoundationSeries.txt";
        Timer timer = new Timer();
        timer.play();
        //Reader reader = new FileReader(fileName);
        FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader,10000);
        Map<Integer, Long> freq = new HashMap<>();
        tallyChars(reader, freq);
        print_tally(freq);

        //for(int i = 0; i < 10; i++) {
        //   Timer timer = new Timer();
        //    timer.play();
        //    //Reader reader = new FileReader(fileName);
        //    FileReader fileReader = new FileReader(fileName);
        //    BufferedReader reader = new BufferedReader(fileReader,10000);
        //    Map<Integer, Long> freq = new HashMap<>();
        //    tallyChars(reader, freq);
        //   double timeItTookToRunCode = timer.check();
        //    runTimes[i] = timeItTookToRunCode;
        //}

        //for (int i = 0; i < runTimes.length; i++) {
        //    System.out.println("Run " + i + ": " + (runTimes[i] * 1_000 + " ms"));
        //}
        //System.out.println("Average: " + (findSumUsingStream(runTimes) / runTimes.length) * 1_000 + " ms");
    }


    //public static double findSumUsingStream(double[] array) {
    //    return Arrays.stream(array).sum();
    //}

    private static void tallyChars(BufferedReader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);
            } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
        }
    }

    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));;
        }
    }
}
