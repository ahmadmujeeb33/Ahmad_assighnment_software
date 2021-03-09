package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WordCounter{


    Map<String, Double> wordCounts = new TreeMap<>();
    double numOfFiles = 0;


    HashSet<String> set=new HashSet();

    /**
     * counting how many files contain a specific word and that each word is only counted once in each file
     * @param file
     * @throws FileNotFoundException
     */
    public void ParseTrain(File file) throws FileNotFoundException {
        if(file.isDirectory()){
                //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                numOfFiles+=1;
                ParseTrain(current);
            }
        }
        else{
            Scanner scanner = new Scanner(file);
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                if (isValidWord(token)){
                    // checking if that word has been already counted for
                    if(!set.contains(token)){
                        set.add(token);
                        // checking if that word is in hashmap or not
                        if(wordCounts.containsKey(token)){
                            double previous = wordCounts.get(token);
                            wordCounts.put(token, previous+1);

                        }
                        // if not in hashmap then add to hashmap
                        else{
                            wordCounts.put(token, 1.0);
                        }
                    }
                }
            }
            set.clear();
        }

    }
    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }





}