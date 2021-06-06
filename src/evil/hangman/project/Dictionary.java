package evil.hangman.project;

import java.util.ArrayList;     // Used to create ArrayLists dictionary use
import java.util.Scanner;
import java.io.*;               // Used for IOException, File
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dictionary {

    // Declare a dynamically allocated ArrayList of Strings for the dictionary.
    // The dictionary can hold any number of words.
    ArrayList<String> dictionary;
    private File dictionaryFile;

    // Constructor
    Dictionary() {
        // Define the instance of the dictionary ArrayList
        dictionary = new ArrayList<String>();
        // Now fill the dictionary array list with words from the dictionary file
        readInDictionaryWords();
    }//end Constructor

    //---------------------------------------------------------------------------------
    // Read in the words to create the dictionary.
    public void readInDictionaryWords() {

//          
        FileReader r = null;
        try {
            r = new FileReader("src/resources/Dictionary.txt");

        } catch (FileNotFoundException ex) {

            System.out.println("*** Error *** \n"
                    + "Your dictionary file has the wrong name or is "
                    + "in the wrong directory.  \n"
                    + "Aborting program...\n\n");
            System.exit(-1);    // Terminate the program

        }
        Scanner f = new Scanner(r);

        while (f.hasNextLine()) {
            dictionary.add(f.nextLine());
        }

    }//end createDictionary()

    //---------------------------------------------------------------------------------
    // Allow looking up a word in dictionary, returning a value of true or false
    public boolean wordExists(String wordToLookup) {
        if (dictionary.contains(wordToLookup.toUpperCase())) {
            return true;    // words was found in dictionary
        } else {
            return false;   // word was not found in dictionary    
        }
    }//end wordExists

    //---------------------------------------------------------------------------------
    // return number of words in dictionary
    public int numberOfWordsInDictionary() {
        return dictionary.size();
    }

    //---------------------------------------------------------------------------------
    // return word at a particular position in dictionary
    public String wordAtIndex(int index) {
        return dictionary.get(index);
    }

    public int indexOfWord(String wordToLookup) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).equalsIgnoreCase(wordToLookup)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList getDictionary() {
        return dictionary;
    }

    //returns an array of words that do not contain the letters
    //the words will be of a certain length only
    public ArrayList getArrayNoMatch(String letters, int len)
        /*
        Pre-condition: len is a positive integer that represents the length, 
        and the string must contain atleast one letter that is uppercase
        */
    {
        ArrayList<String> returnList = new ArrayList<>();
        /*
        An Arraylist will be created that contains the words that do not contain 
        any of the letters from the String that was inputed, 
        and meets the length of the word
        */
        int count = 0;
        /*
        This integer is used to count how many letters meet the requirements of 
        the letters that are not in the word
        */
        for (int i = 0; i < dictionary.size(); i++) {
            /*
            This outside for loop will search through the dictionary and will 
            look through each word
            */
            count = 0;
           /*
            This variable will  be reset each time the word changes
            */
            if ((dictionary.get(i)).length() == len) {
                /*
                This if statement will check to see if the word is the same 
                length as the length that was passed in as a parameter
                */
                for (int j = 0; j < len; j++) {
                    /*
                    This for loop will search through each letter in the word 
                    from the dictionary
                    */
                    for (int k = 0; k < letters.length(); k++) {
                        /*
                        This for loop will search through each letter in the 
                        string of letters guessed
                        */
                        if (!((dictionary.get(i)).substring(j, j + 1)).equals(letters.substring(k, k + 1))) {
                            /*
                            This if statement is looking to see if a letter in 
                            the word is not equal to a letter in the string of 
                            guessed letters
                            */
                            count++;//
                            /*
                            If the letter in the word does not equal a letter in
                            the substring, this counter will increase. This means 
                            that out of the total amount of times that the two 
                            inside for loops run, the letter doesn't match that 
                            many times
                            */
                        }
                    }
                }
            }
            if (count == (len * letters.length())) {
                /*
                This if will see if the counter is equal to the amount of times 
                each letter is compared
                */
                returnList.add(dictionary.get(i));
                /*
                This statement is adding the word to the initialized array that 
                is called returnList. The word will only be addded if each letter 
                in the word does not match each letter in the string of guessed 
                letters.
                */
            }
        }
        return returnList;
        /*
        Post-condition: This function will return an ArrayList with the words 
        that doesn't have the letters that were inputed, with the length of the 
        word.
        */
    }
    public ArrayList getArrayNoMatchNew(String letters, int len){
        /*
        Pre-condition: len is a positive integer that represents the length, 
        and the string must contain atleast one letter that is uppercase
        */
        ArrayList<String> returnList = new ArrayList<>();
        /*
        An Arraylist will be created that contains the words that do not contain 
        any of the letters from the String that was inputed, 
        and meets the length of the word
        */
        for (int i = 0; i < dictionary.size(); i++) {
            /*
            This outside for loop will search through the dictionary and will 
            look through each word
            */
            boolean bad = false;
            /*
            This boolean will keep be used to see if the word is bad, or is good
            an needs to be added to the array. This boolean gets changed to 
            false for every word.
            */
            if ((dictionary.get(i)).length() == len) {
                /*
                This if statement will check to see if the word is the same 
                length as the length that was passed in as a parameter
                */
                int l =0;
                /*
                This int will represent the index of the letter in the word.
                */
                int s = 0;
                /*
                This int will represent the index of the letter in the string.
                */
                while(l<len && s<letters.length()+1){
                    /*
                    This loop will continue to run while there are still letters
                    that need to be compared in the word
                    */
                    if (((dictionary.get(i)).substring(l, l + 1)).equals(letters.substring(s, s + 1))) {
                        /*
                        This if statement is looking to see if a letter in 
                        the word is not equal to a letter in the string of 
                        guessed letters
                        */
                        bad = true;
                        /*
                        If the letter in the word matches a letter in the sring, 
                        this boolean will turn true, which will be used to show 
                        that the word is bad, and not to add it to new ArrayList
                        */
                    }
                    s++;
                    /*
                    This number should always increase, and will continue on to 
                    the next letter. If there are no more letteres in the String, 
                    the inside loop will end.
                    */
                    if(l<len && s>=letters.length()){
                        s=0;
                        l++;
                        /*
                        This If will first check to see if there are anymore 
                        letters in the word or string. If there are letters left 
                        in the word, and the first letter has been compared to 
                        every letter in the string, the index of the letter in 
                        the word in the string will increase, and the index of 
                        the letter in the string will reset to 0.
                        */
                    }
                }
            }else{
                /*
                If the length does not match, the letter should not be added. 
                It is a "bad" word.
                */
                bad = true;
            }   
            if (!bad) {
                /*
                If the word is not bad(good), the word will be added to the new 
                ArrayList.
                */
                returnList.add(dictionary.get(i));
            }
        }
        return returnList;
        /*
        Post-condition: This function will return an ArrayList with the words 
        that doesn't have the letters that were inputed, with the length of the 
        word.
        */
    }
}//end class Dictionary
