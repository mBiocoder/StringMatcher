import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Main extends Application {
    public static void main(String[] args) {

        // Parse arguments
        Parser parser = new Parser("-t", "-p", "-n", "-kmp", "-bmn", "-bmb", "-z");
        parser.setNonParameterOptions("-n", "-kmp", "-bmn", "-bmb", "-z");
        parser.parseArguments(args);

        boolean doNaive = parser.isSet("-n");
        boolean doZbox = parser.isSet("-z");
        boolean doKMP = parser.isSet("-kmp");
        boolean doBoyerN = parser.isSet("-bmn");
        boolean doBoyerB = parser.isSet("-bmb");

        // values
        String text = "TTGACEDAACACBDAA";;
        String pattern = "AATACACBDA";
        char[] t = text.toCharArray();
        char[] p = pattern.toCharArray();

        if(parser.getValue("-t") != null){
            text = parser.getValue("-t");
            t = text.toCharArray();
        }
        if(parser.getValue("-p") != null){
            pattern = parser.getValue("-t");
            p = pattern.toCharArray();
        }

        // call functions
        if(doNaive){
            naive(t, t.length, p, p.length);
        }
        if(doZbox){
            zBoxes(p, p.length);
        }
        if(doKMP){
            knuthMorrisPratt(t, t.length, p, p.length);
        }
        if(doBoyerN){
            boyerMoore(t, t.length, p, p.length, false);
        }
        if(doBoyerB){
            boyerMoore(t, t.length, p, p.length, true);
        }

        // if no algorithm is specified call javafx
        if(!(doNaive || doKMP || doBoyerB || doBoyerN)){
            launch(args);
        }
        System.exit(0);
    }

    public static boolean knuthMorrisPratt(char[] t, int n, char[] s, int m){
        System.out.println("Knuth-Morris-Pratt Algorithm with text: " + "\n" + Arrays.toString(t) + "\n" + "and pattern " + "\n" + Arrays.toString(s) + "\n");
        int[] border = new int[m + 1];
        computeBorders(border, m, s);
        System.out.println("border table:");
        System.out.println(Arrays.toString(s) + "\n" + Arrays.toString(border));
        int comparing = 0;
        int shift = 0;
        int i = 0;
        int j = 0;
        while (i <= n - m){
            while(t[i + j] == s[j]){
                comparing++;
                j++;
                if(j == m) {
                    System.out.println("number of comparisons: " + comparing);
                    System.out.println("number of shifts: " + shift);
                    System.out.println("pattern found at: " + i);
                    return true;
                }
            }
            i = i + (j - border[j]);
            j = Math.max(0, border[j]);
            System.out.println();
            comparing++;
            shift++;
        }
        System.out.println("number of comparisons: " + comparing);
        System.out.println("number of shifts: " + shift);
        System.out.println("pattern not found");
        return false;
    }

    public static boolean knuthMorrisPrattRaw(char[] t, int n, char[] s, int m){
        int[] border = new int[m + 1];
        computeBorders(border, m, s);
        int i = 0;
        int j = 0;
        while (i <= n - m){
            while(t[i + j] == s[j]){
                j++;
                if(j == m) {
                    return true;
                }
            }
            i = i + (j - border[j]);
            j = Math.max(0, border[j]);
        }
        return false;
    }

    public static int knuthMorrisPrattCount(char[] t, int n, char[] s, int m, char v) {
        int[] border = new int[m + 1];
        computeBorders(border, m, s);
        int comparing = 0;
        int shift = 0;
        int i = 0;
        int j = 0;
        while (i <= n - m) {
            while (t[i + j] == s[j]) {
                j++;
                comparing++;
                if (j == m) {
                    if(v == 'c'){
                        return comparing;
                    }else{
                        return shift;
                    }
                }
            }
            i = i + (j - border[j]);
            j = Math.max(0, border[j]);
            shift++;
            comparing++;
        }
        if(v == 'c'){
            return comparing;
        }else{
            return shift;
        }
    }

    private static void computeBorders(int[] border, int m, char[] s) {
        // for KMP
        border[0] = -1;
        border[1] = 0;
        int i = 0;
        for(int j = 2; j <= m; j++){
            //Hier gilt i = border[j - 1]
            while((i >= 0) && (s[i] != s[j - 1])){
                i = border[i];
            }
            i++;
            border[j] = i;
        }
    }


    public static boolean boyerMoore(char[] t, int n, char[] s, int m, boolean badCharacter){
        System.out.println("Boyer-Moore Algorithm with text: " + Arrays.toString(t) + " and pattern " + Arrays.toString(s) + ":");
        int[] shiftTable = new int[m + 1];
        HashMap<Character, Integer> ebc = new HashMap<>();
        if(badCharacter){
            System.out.println("bad Character: ");
            bc_boyerMoore(ebc, s, m);
            System.out.println(ebc.toString());
        }else{
            System.out.println("shift table:");
            computeShiftTable(shiftTable, m, s);
            System.out.println(Arrays.toString(shiftTable));
        }
        int comparing = 0;
        int shift = 0;
        int i = 0;
        int j = m-1;
        while(i <= n - m){
            while(t[i + j] == s[j]){
                comparing++;
                if(j == 0) {
                    System.out.println("Number of comparisons: " + comparing);
                    System.out.println("Number of shifts: " + shift);
                    System.out.println("Pattern found at: " + i);
                    return true;
                }
                j--;
            }
            if(badCharacter){
                i = i + ebc.get(t[i + j]);
            } else {
                i = i + shiftTable[j];
            }
            j = m - 1;
            comparing++;
            shift++;
        }
        System.out.println("Number of comparisons: " + comparing);
        System.out.println("Number of shifts: " + shift);
        System.out.println("Pattern found: ");
        return false;
    }

    public static boolean boyerMooreRaw(char[] t, int n, char[] s, int m, boolean badCharacter){
        int[] shiftTable = new int[m + 1];
        HashMap<Character, Integer> ebc = new HashMap<>();
        if(badCharacter){
            bc_boyerMoore(ebc, s, m);
        }else{
            computeShiftTable(shiftTable, m, s);
        }
        int i = 0;
        int j = m-1;
        while(i <= n - m){
            while(t[i + j] == s[j]){
                if(j == 0) return true;
                j--;
            }
            if(badCharacter){
                i = i + ebc.get(t[i + j]);
            } else {
                i = i + shiftTable[j];
            }
            j = m - 1;
        }
        return false;
    }

    public static int boyerMooreCount(char[] t, int n, char[] s, int m, boolean badCharacter, char v){
        int[] shiftTable = new int[m + 1];
        HashMap<Character, Integer> ebc = new HashMap<>();
        if(badCharacter){
            bc_boyerMoore(ebc, s, m);
        }else{
            computeShiftTable(shiftTable, m, s);
        }
        int comparing = 0;
        int shift = 0;
        int i = 0;
        int j = m-1;
        while(i <= n - m){
            while(t[i + j] == s[j]){
                comparing++;
                if(j == 0) {
                    if(v == 'c') return comparing;
                    else return shift;
                }
                j--;
            }
            if(badCharacter){
                i = i + ebc.get(t[i + j]);
            } else {
                i = i + shiftTable[j];
            }
            j = m - 1;
            comparing++;
            shift++;
        }
        if(v == 'c') return comparing;
        else return shift;
    }

    private static void computeShiftTable(int[] shiftTable, int m, char[] s) {
        for(int j = 0; j <= m; j++){
            shiftTable[j] = m;
        }
        //Teil 1 Sigma <= j
        int[] border2 = new int[m + 1];
        border2[0] = -1;
        border2[1] = 0;
        int i = 0;
        for(int k = 2; k <= m; k++){
            // Hier gilt i = border2[k - 1]
            while((i >= 0) && (s[m - i - 1] != s[m - k])){
                int g = k - i - 1;
                shiftTable[m - i - 1] = Math.min(shiftTable[m - i - 1], g);
                i = border2[i];
            }
            i++;
            border2[k] = i;
        }
        //Teil 2: sigma > j
        int j = 0;
        for(i = border2[m]; i >= 0; i = border2[i]){
            int g = m - i;
            while(j < g){
                shiftTable[j] = Math.min(shiftTable[j], g);
                j++;
            }
        }
        //System.out.println(Arrays.toString(s));
        //System.out.println(Arrays.toString(border2));
        //System.out.println(Arrays.toString(shiftTable));
    }

    public static void bc_boyerMoore(HashMap<Character, Integer> ebc, char[] s, int m){
        char[] alphabet = {'A', 'B'};
        for(int i = 1; i < m-1; i++){
            if(!ebc.containsKey(s[m-i-1])){
                ebc.put(s[m-i-1], i);
            }
            if(ebc.size() == alphabet.length){
                break;
            }
        }
        for(char c : alphabet){
            if(!ebc.containsKey(c)){
                ebc.put(c, m);
            }
        }
    }


    public static int[] zBoxes(char[] s, int m){
        System.out.println("Z BOX");
        int[] z = new int[m + 1];
        int l = 0;
        int r = 0;
        int i = 1;
        for(int k = 1; k < m; k++){
            if(k > r){
                i = k;
                while((i < m) && (s[i] == s[i - k])){
                    i++;
                }
                z[k] = i - k;
                if(z[k] > 0){
                    l = k;
                    r = i - 1;
                }
            } else {
                if(z[k - l] < r - k + 1){
                    z[k] = z[k - l];
                } else {
                    i = r + 1;
                    while((i < m) && (s[i] == s[i - k])){
                        i++;
                    }
                    z[k] = i - k;
                    if(i - 1 > r){
                        l = k;
                        r = i - 1;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(s));
        System.out.println(Arrays.toString(z));
        return z;
    }

    public static boolean naive(char[] t, int n, char[] s, int m) {
        System.out.println("Naive Algorithm with text: " + Arrays.toString(t) + " and pattern " + Arrays.toString(s) + ":");
        int comparisons = 0;
        int shifts = 0;
        // Slide pattern one by one
        for (int i = 0; i <= n - m; i++) {
            //For current index check if pattern and text match
            int j = 0;
            while(t[i + j] == s[j]){
                comparisons++;
                j++;
                if(j == m) {
                    System.out.println("number of comparisons: " + comparisons);
                    System.out.println("number of shifts: " + shifts);
                    System.out.println("pattern found at: " + i);
                    return true;
                }
            }
            comparisons++;
            shifts++;
        }
        System.out.println("number of comparisons: " + comparisons);
        System.out.println("number of shifts: " + shifts);
        System.out.println("pattern not found");
        return false;
    }

    public static boolean naiveRaw(char[] t, int n, char[] s, int m) {
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while(t[i+ j] == s[j]){
                j++;
                if(j == m) return true;
            }
        }
        return false;
    }

    public static int naiveCount(char[] t, int n, char[] s, int m, char v) {
        int comparison = 0;
        int shift = 0;
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while(t[i+ j] == s[j]){
                comparison++;
                j++;
                if(j == m) {
                    if (v == 'c') return comparison;
                    else return shift;
                }
            }
            shift++;
            comparison++;
        }
        if (v == 'c') return comparison;
        else return shift;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane root = new FlowPane();
        root.getChildren().addAll(createLineChart('t'), createLineChart('c'), createLineChart('s'));

        Scene scene = new Scene(root, 1300, 400);

        primaryStage.setTitle("Line Chart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private LineChart createLineChart(char v) {
        String seedChars = "AB";
        int patternLength = 100;
        int textLengthIn = 100000;
        int upperBound = 50;
        ArrayList<Double> naive = new ArrayList();
        ArrayList<Double> kmp = new ArrayList();
        ArrayList<Double> bm_n = new ArrayList();
        ArrayList<Double> bm_b = new ArrayList();

        int j = 1;
        while(j <= upperBound){
            // generate random Strings
            String text = generateRandomString(textLengthIn*j, seedChars);
            String pattern = generateRandomString(patternLength, seedChars);
            char[] t = text.toCharArray();
            char[] s = pattern.toCharArray();
            int n = text.length();
            int m = pattern.length();

            if(v == 't'){
                long startTime = System.nanoTime();
                naiveRaw(t, n, s, m);
                long endTime = System.nanoTime();
                naive.add((((double) (endTime - startTime))/1000000.0));

                startTime = System.nanoTime();
                knuthMorrisPrattRaw(t, n, s, m);
                endTime = System.nanoTime();
                kmp.add((((double) (endTime - startTime))/1000000.0));

                startTime = System.nanoTime();
                boyerMooreRaw(t, n, s, m, false);
                endTime = System.nanoTime();
                bm_n.add((((double) (endTime - startTime))/1000000.0));

                startTime = System.nanoTime();
                boyerMooreRaw(t, n, s, m, true);
                endTime = System.nanoTime();
                bm_b.add((((double) (endTime - startTime))/1000000.0));
            } else if(v == 'c'){
                naive.add((double) naiveCount(t, n, s, m, 'c')/1000000);
                kmp.add((double) knuthMorrisPrattCount(t, n, s, m, 'c')/1000000);
                bm_n.add((((double) boyerMooreCount(t, n, s, m, false, 'c')/1000000)));
                bm_b.add((((double) boyerMooreCount(t, n, s, m, true, 'c')/1000000)));
            } else if(v == 's'){
                naive.add((double) naiveCount(t, n, s, m, 's')/1000000);
                kmp.add((double) knuthMorrisPrattCount(t, n, s, m, 's')/1000000);
                bm_n.add((((double) boyerMooreCount(t, n, s, m, false, 's')/1000000)));
                bm_b.add((((double) boyerMooreCount(t, n, s, m, true, 's')/1000000)));
            }
            j++;
        }

        //Defining axis
        NumberAxis xAxis = new NumberAxis(1, upperBound, 5);
        xAxis.setLabel("text length in " + textLengthIn + " (pattern len " + patternLength + ", seed Chars: " + seedChars + ")");
        NumberAxis yAxis = new NumberAxis(0, Math.round(naive.get(naive.size()-1))+10, 10);
        if(v == 't'){
            yAxis.setLabel("time in milliseconds");
        } else if(v == 'c'){
            yAxis = new NumberAxis   (0, 10, 1);
            yAxis.setLabel("comparisons in mio");
        } else if(v == 's'){
            yAxis = new NumberAxis   (0, 10, 1);
            yAxis.setLabel("shifts in mio");
        }

        //Creating the line chart
        LineChart linechart =  new LineChart(xAxis, yAxis);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("naive");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("KMP");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("bm next");
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("bm bc");

        for (int i = 0; i < naive.size(); i++){
            series1.getData().add(new XYChart.Data(i+1, naive.get(i)));
            series2.getData().add(new XYChart.Data(i+1, kmp.get(i)));
            series3.getData().add(new XYChart.Data(i+1, bm_n.get(i)));
            series4.getData().add(new XYChart.Data(i+1, bm_b.get(i)));
        }

        //Setting the data to Line chart
        linechart.getData().addAll(series1, series2, series3, series4);

        linechart.setMaxWidth(400);
        return linechart;
    }

    private static String generateRandomString(int length, String seedChars) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        Random rand = new Random();
        while (i < length) {
            sb.append(seedChars.charAt(rand.nextInt(seedChars.length())));
            i++;
        }
        return sb.toString();
    }

}

