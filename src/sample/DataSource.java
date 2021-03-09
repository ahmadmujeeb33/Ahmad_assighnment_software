package sample;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class DataSource {

    private static ObservableList<TestFile> information = FXCollections.observableArrayList();
    private static File mainDirectory;
    private static double guessOfSpamFiles = 0;
    private static double numOfFiles = 0;
    private static double numFalsePositives = 0;
    private static double numTruePositives = 0;
    private static double numTrueNegatives = 0;
    public void MainDirec(File Directory){
        mainDirectory = Directory;

    }

    /**
     * Will use to help calculate the probability that a file is spam by calculating the probability a particular word is in spam
     * will pass the file path to train to get a hashmap of how many files contain a word in spam and ham
     * @return an ObservableList
     * @throws FileNotFoundException
     */
    public static ObservableList<TestFile> getInformation() throws FileNotFoundException {



        WordCounter trainHamFreq = new WordCounter();
        WordCounter trainSpamFreq = new WordCounter();
        DataSource test = new DataSource();

        // getting informtion from train folder
        trainHamFreq.ParseTrain(new File(mainDirectory + "\\train\\ham"));
        trainHamFreq.ParseTrain(new File(mainDirectory + "\\train\\ham2"));
        trainSpamFreq.ParseTrain(new File(mainDirectory + "\\train\\spam"));



        Map<String, Double> probabilities = new TreeMap<>();

        // calculating  the probability a word is spam
        for(Map.Entry words : trainSpamFreq.wordCounts.entrySet()){
            if(trainHamFreq.wordCounts.containsKey(words.getKey())){
                double spamProbability = (double) words.getValue()/ trainSpamFreq.numOfFiles;
                double num = trainHamFreq.wordCounts.get(words.getKey());
                double hamProbability = num / trainHamFreq.numOfFiles;
                double probWordIsSpam  = (spamProbability)/  (spamProbability+hamProbability);
                String word = (String) words.getKey();

                probabilities.put(word,probWordIsSpam);
            }
            else{
                String word = (String) words.getKey();
                probabilities.put(word,1.00);

            }


        }
        // getting information from test folder
        test.parseTest(new File(mainDirectory + "\\test\\ham"),probabilities);
        test.parseTest(new File(mainDirectory + "\\test\\spam"),probabilities);

        Controller controller = new Controller();
        controller.DetermineAccuracy(numOfFiles,numTruePositives,numFalsePositives,numTrueNegatives);


        return information;
    }

    /**
     * calculating the probability a file is spam
     * will be going through files in the test folder
     * @param file
     * @param probab
     * @throws FileNotFoundException
     */
    public void parseTest(File file,Map<String, Double> probab) throws FileNotFoundException {


        double n = 0;
        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                parseTest(current,probab);
            }
        }
        else{
            Scanner scanner = new Scanner(file);
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                // checking if token is a proper word
                if (isValidWord(token)){
                    // checking if probab hashmap contains the specific token
                    if(probab.containsKey(token)) {
                        double num = probab.get(token);
                        n = n + Math.log(1-num) - Math.log(num);
                    }

                }
            }

        }
        TestFile testFile = new TestFile();
        // calculating the spam probability of a file
        double probability = 1/(1+Math.pow(Math.E,n));

        // name of the file
        String name = file.getName();

        testFile.setSpamProbability(Double.toString(probability));

        // the probability number rounded
        String probabilityRounded = testFile.getSpamProbRounded();


        double number = Double.parseDouble(probabilityRounded);

        if(file.getParentFile().getName().equals("ham") && number  < 0.50) {
            numTrueNegatives+=1;
        }
        if(file.getParentFile().getName().equals("spam") && number>0.50){
            numTruePositives+=1;
        }
        if(file.getParentFile().getName().equals("spam") && number < 0.50){
            numFalsePositives+=1;
        }
        numOfFiles+=1;


        information.add(new TestFile(name, probabilityRounded, file.getParentFile().getName()));

    }
    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

}
