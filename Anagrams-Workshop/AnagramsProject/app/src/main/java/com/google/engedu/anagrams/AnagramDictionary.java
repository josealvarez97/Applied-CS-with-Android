/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordLength = DEFAULT_WORD_LENGTH; // For restricting the length of starter words...
    private Random random = new Random();
    // WORD LIST - Just a list with all words in the file
    private ArrayList<String> wordList = new ArrayList<>();
    // WORD SET - Will allow us to rapidly (in O(1)) verify whether a word is valid
    private HashSet<String> wordSet = new HashSet();
    // LETTERS TO WORD - Will allow to group anagrams together.
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<>();
    // SIZE TO WORDS - Will allow to map words to their lengths.
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        //ArrayList<String> wordList = new ArrayList<>();
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word); // I'm not sure if this would work out just by using wordSet and not wordList...
            wordSet.add(word);
            String key = sortLetters(word);
            if (lettersToWord.containsKey(key)) {
                // If key already exists, word is added to ArrayList at that key.
                lettersToWord.get(key).add(word);
            }
            else {
                // Otherwise, new ArrayList is created and put where corresponding key.
                lettersToWord.put(key, new ArrayList<String>());
                // Word is added at the ArrayList of such key.
                lettersToWord.get(key).add(word);
            }

            if (sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            } else {
                sizeToWords.put(word.length(), new ArrayList<String>());
                sizeToWords.get(word.length()).add(word);
            }

        }
    }

    public boolean isGoodWord(String word, String base) {
        // Checks whether the provided word is a valid dictionary word
        // Checks whether the word does not contain the base word as a substring
        if (!word.contains(base)) {
            //boolean valid = wordSet.contains(word);
            if (wordSet.contains(word)) {
                return true;
            }
        }

        return false;

    }

    public List<String> getAnagrams(String targetWord) {

        // Array list for storing the anagrams for the target word
        ArrayList<String> targetWordAnagrams= new ArrayList<>();

        // We iterate through word list for finding matching anagrams.
        /*for (int i = 0; i < wordList.size(); i++) {
            String anagramCandidate = wordList.get(i); // retrieving the value once from wordList is faster
            if (targetWord.length() == anagramCandidate.length()) { // for the sake of speed, we first check the length
                //anagramCandidate = sortLetters(anagramCandidate); // Ultimately, ...
                //targetWord = sortLetters(targetWord);
                if (sortLetters(anagramCandidate).equals(sortLetters(targetWord))) { // ... we compare the sorted version of the words.
                    targetWordAnagrams.add(anagramCandidate); // We append the matching anagram to the corresponding array list.
                }
            }
        }*/

        // This approach will be faster
        String key = sortLetters(targetWord);
        if (lettersToWord.containsKey(key)) {
            targetWordAnagrams = lettersToWord.get(key);
        }
        return targetWordAnagrams;
    }
    public String sortLetters(String word) {
        // Good reference
        // https://stackoverflow.com/questions/6266572/creating-new-string-with-sorted-letters-from-a-string-word-in-java
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        String sortedWord = new String(chars);

        return sortedWord;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> anagramsWithOneMoreLetter = new ArrayList<String>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray(); //https://stackoverflow.com/questions/17575840/better-way-to-generate-array-of-all-letters-in-the-alphabet

        // Good discussion on the performance of a for each loop and a normal for
        // https://stackoverflow.com/questions/85190/how-does-the-java-for-each-loop-work

        for (char letter : alphabet) {
            String wordPlusLetter = word + letter;
            String wordPlusLetter_Key = sortLetters(wordPlusLetter);
            if (lettersToWord.containsKey(wordPlusLetter_Key)) {
                anagramsWithOneMoreLetter.addAll(lettersToWord.get(wordPlusLetter_Key));
            } // else that was not a valid arrange of letters to form anagrams from.
        }

        
        return anagramsWithOneMoreLetter;
    }

    public String pickGoodStarterWord() {

        int startingPoint = random.nextInt(wordList.size());

        for (int i = startingPoint; i < wordList.size(); i++) {
            String potentialWord = wordList.get(i);

            if (potentialWord.length() != wordLength) { // We will only use words of the right length.
                continue;
            }
            if (getAnagramsWithOneMoreLetter(potentialWord).size() >= MIN_NUM_ANAGRAMS) {
                if (wordLength < MAX_WORD_LENGTH) {
                    wordLength++; // We will increase the length each time to increase difficulty until reaching MAX LENGTH
                }
                return potentialWord;
            }
        }
        return "post"; // This is the default, which is unlikely...
    }
}
