package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/* Usage: java NaiveMatching.jar [-t] [-p]
-t: text
-p: pattern
 */
public class Main extends Application {

    static String text;
    static String pattern;
    static int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        ArrayList<Long> binaryAlphabet = new ArrayList();
        ArrayList<Long> DNAAlphabet = new ArrayList();
        ArrayList<Long> AlphanumericAlphabet = new ArrayList();

        //varying text length n
        /*for(int i = 4; i < 40; i++){
            //binary
            long startTime = System.nanoTime(); //start timer
            String binaryText = BinaryGenerator(i); //generate random text and pattern
            String binaryPattern = BinaryGenerator(3);
            search(binaryText, binaryPattern); //call naive algorithm
            long endTime = System.nanoTime(); //end timer
            binaryAlphabet.add(endTime - startTime);

            //DNA
            startTime = System.nanoTime(); //start timer
            String DNAText = DNAGenerator(i); //generate random text and pattern
            String DNAPattern = DNAGenerator(3);
            search(DNAText, DNAPattern); //call naive algorithm
            endTime = System.nanoTime(); //end timer
            DNAAlphabet.add(endTime - startTime);

            //Alphanumerical
            startTime = System.nanoTime(); //start timer
            String AlphanumericalText = AlphanumericGenerator(i); //generate random text and pattern
            String AlphanumericalPattern = AlphanumericGenerator(3);
            search(AlphanumericalText, AlphanumericalPattern); //call naive algorithm
            endTime = System.nanoTime(); //end timer
            AlphanumericAlphabet.add(endTime - startTime);

        }*/

        //for varying m
        /*for(int i = 3; i < 20; i++){
            //binary
            long startTime = System.nanoTime(); //start timer
            String binaryText = BinaryGenerator(40); //generate random text and pattern
            String binaryPattern = BinaryGenerator(i);
            search(binaryText, binaryPattern); //call naive algorithm
            long endTime = System.nanoTime(); //end timer
            binaryAlphabet.add(endTime - startTime);

            //DNA
            startTime = System.nanoTime(); //start timer
            String DNAText = DNAGenerator(40); //generate random text and pattern
            String DNAPattern = DNAGenerator(i);
            search(DNAText, DNAPattern); //call naive algorithm
            endTime = System.nanoTime(); //end timer
            DNAAlphabet.add(endTime - startTime);

            //Alphanumerical
            startTime = System.nanoTime(); //start timer
            String AlphanumericalText = AlphanumericGenerator(40); //generate random text and pattern
            String AlphanumericalPattern = AlphanumericGenerator(i);
            search(AlphanumericalText, AlphanumericalPattern); //call naive algorithm
            endTime = System.nanoTime(); //end timer
            AlphanumericAlphabet.add(endTime - startTime);

        }*/

        //Print to console
        /*System.out.println(binaryAlphabet);
        System.out.println(DNAAlphabet);
        System.out.println(AlphanumericAlphabet);*/

        //Defining axis for run time comparisons
        /*NumberAxis xAxis = new NumberAxis(3, 17, 1);
        xAxis.setLabel("m");
        NumberAxis yAxis = new NumberAxis   (1000, 70000, 1000);
        yAxis.setLabel("time");*/


        /* Character comparisons */

        ArrayList<Integer> numberBinary = new ArrayList<>();
        ArrayList<Integer> numberDNA = new ArrayList<>();
        ArrayList<Integer> numberAlphanumeric = new ArrayList<>();

        //for varying n
        for(int i = 3; i < 40; i ++){
            //binary
            String binaryText = BinaryGenerator(i); //generate random text and pattern
            String binaryPattern = BinaryGenerator(5);
            search(binaryText, binaryPattern); //call naive algorithm
            numberBinary.add(counter);
            counter = 0;

            //DNA
            String DNAText = DNAGenerator(i); //generate random text and pattern
            String DNAPattern = DNAGenerator(5);
            search(DNAText, DNAPattern); //call naive algorithm
            numberDNA.add(counter);
            counter = 0;

            //Alphanumerical
            String AlphanumericalText = AlphanumericGenerator(i); //generate random text and pattern
            String AlphanumericalPattern = AlphanumericGenerator(5);
            search(AlphanumericalText, AlphanumericalPattern); //call naive algorithm
            numberAlphanumeric.add(counter);
            counter = 0;

        }

        System.out.println(numberBinary);
        System.out.println(numberDNA);
        System.out.println(numberAlphanumeric);

        //Defining axis for matching comparisons
        NumberAxis xAxis = new NumberAxis(3, 37, 1);
        xAxis.setLabel("n");
        NumberAxis yAxis = new NumberAxis (0, 100, 1);
        yAxis.setLabel("comparisons");

        //Creating the line chart
        LineChart linechart = new LineChart(xAxis, yAxis);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("binary");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("DNA");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Alphanumerical");

        for (int i = 0; i < numberAlphanumeric.size(); i++){
            series1.getData().add(new XYChart.Data(i+1, numberBinary.get(i)));
            series2.getData().add(new XYChart.Data(i+1, numberDNA.get(i)));
            series3.getData().add(new XYChart.Data(i+1, numberAlphanumeric.get(i)));
        }


        //Setting the data to Line chart
        linechart.getData().addAll(series1, series2, series3);

        Group root1 = new Group(linechart);
        Scene scene = new Scene(root1, 600, 400);
        primaryStage.setTitle("Line Chart for Naive String Matching Algorithm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static boolean search(String txt, String pat)
    {
        boolean isThere = false;
        int m = pat.length();
        int n = txt.length();

        // Slide pattern one by one
        for (int i = 0; i <= n - m; i++) {

            int j;

            //For current index check if pattern and text match
            for (j = 0; j < m; j++)
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    counter++;
                    break;
                }
                else {
                    counter ++;
                }

            if (j == m) //if last position of pattern matches with text aswell
                counter ++;
                isThere = true;
            //System.out.println("Pattern found at index " + i);
        }
        return isThere;
    }

        /* A modified Naive Pettern Searching
        algorithm optimized for all characters of pattern being different */
        public static void searchOptimized(String pat, String txt)
        {
            int M = pat.length();
            int N = txt.length();
            int i = 0;

            while (i <= N - M)
            {
                int j;

                /* For current index i, check for pattern match */
                for (j = 0; j < M; j++)
                    if (txt.charAt(i + j) != pat.charAt(j))
                        break;

                if (j == M) //
                {
                    System.out.println("Pattern found at index "+i);
                    i = i + M;
                }
                else if (j == 0)
                    i = i + 1;
                else
                    i = i + j; // slide the pattern by j
            }
        }

    //Random String generators//
    public static String BinaryGenerator(int len) {
        String chars = "10";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        //System.out.println(sb.toString());
        return sb.toString();
    }

    public static String DNAGenerator(int len) {
        String chars = "ACTG";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        //System.out.println(sb.toString());
        return sb.toString();
    }

    public static String AlphanumericGenerator(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk" +"lmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        //System.out.println(sb.toString());
        return sb.toString();
    }


    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-t")) {
                text = args[i + 1];
                System.out.println("text: " + text);
            }
            else if (args[i].equals("-p")) {
                pattern = args[i + 1];
                System.out.println("pattern: " + pattern);
            }
        }

        //method call
        System.out.println(search(text, pattern));

        //If plot should be printed remove the comment
        launch(args);
    }
}
