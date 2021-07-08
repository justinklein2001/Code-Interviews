package interviewsPackage;

/* Questions and Solutions Derived From:
 * Cracking the Coding Interview
 * 189 Programming Questions & Solutions
 * Written By: Gayle LaakMann McDowell
 * Program Written By: Justin Klein
 * Last Updated: January 19th, 2021
 */

public class Ch1ArraysStrings {

    /* 1.1 Question
     * "Implement an algorithm to determine if a string has all unique characters."
     *  Time Complexity: O(n), Space Complexity is O(1)
     */
     public boolean isUniqueChars(String givenString) {

        /* 128 unique chars in ASCII, if there is more
         * than 128 ever char cannot be unique
         */
        if (givenString.length() > 128){
            return false; 
        }

        boolean[] charSet = new boolean[128];
        int val;

        for (int i=0; i<givenString.length(); i++){
            val = givenString.charAt(i);
            if(charSet[val] == true){
                return false; //char has already been found
            }
            charSet[val]=true;
        }
        return true; //if code makes it here, the string is Unique
     }

    /* 1.2 Question
     * "Given two strings, write a method to decide if one is a permutation of the other."
     *  Time Complexity: O(n), Space Complexity is O(1)
     */
    public boolean isPermutation (String first, String second){

        if (first.length() != second.length()){
            return false; //cannot be a permutation with different lengths
        }
        return sortString(first).equals(sortString(second));

    }
    
    /*
     * 1.2 Helper Function
     */
    private String sortString(String givenString){
        char[] temp = givenString.toCharArray();
        java.util.Arrays.sort(temp);
        return new String(temp);
    }

    /* 1.3 Question
     * "Write a method to replace all spaces in a string with '%20'. You may assume that the string has siffucient space at the end to hold the additional characters."
     */
    public void replaceSpaces(char[] string, int trueLength){

        int countOfSpaces = countOfChar(string,0,trueLength, ' ');
        int newIndex = trueLength - 1 + countOfSpaces * 2;

        if (newIndex + 1 < string.length){
            string[newIndex+1]='\0'; //if there is excess space, has not been replaced
        }
        
        for (int oldIndex = trueLength-1; oldIndex >= 0; oldIndex -= 1){
            if (string[oldIndex] == ' ') { //insert here
                string[newIndex] = '0';
                string[newIndex - 1] = '2';
                string[newIndex -2] = '%';
                newIndex -=3; //time to move on to next char
            } else {
                string[newIndex] = string[oldIndex];
                newIndex -=1; //not whitespace, move on
            }
        }
    }

    /*
     * 1.3 Helper Function
     */
    int countOfChar(char[] string, int start, int end, int target){
        
        int count=0;
        for (int i=start; i <end; i++){
            if (string[i] == target){
                count++;
            }
        }
        return count;
    }
    
    /* 1.4 Question 
     * Given a string, write a function to check if it is a permutation of a palidrome. A palindrome is a word or a phrase
     * that is the same forwards and backwards. A permutation is a rearrangement of letters. The palindrome does not need to be limited 
     * to just dictionary words. You can ignore casting and non-letter characters
     */

    /* 
     * 1.4 Solution 1 - Hash Table O(N) time 
     */

    /* 
     * 1.4 Sol.1 Helper Function, get conversion of char to int 
     */
    private int charToInt(Character c) {
        
        //conversion to int value of chars, kinda miss C with this
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('b');
        int value = Character.getNumericValue(c);

        if ((value >= a) && (value <= z)){
            return value-a; //think ASCII in C, 
        }
        return -1; //if it's another character, we don't want it
    }

    /* 
     * 1.4 Sol.1 Helper Function, Build hash table 
     */
    private int[] buildHashTable(String givenPhrase){
        
        int [] table = new int[26]; //only 26 chars in alphabet

        //super cool way to loop through a char array, ty textbook
        for (char c: givenPhrase.toCharArray()){
            int curr = charToInt(c);
            //if it's not -1, it is valid
            if ( curr != -1){
                table[curr]++;
            }
        }
        return table;
    }

    /* 
     * 1.4 Sol.1 Helper Function, Check if only one character is odd
     */
    private boolean checkOdd(int[] table){
        boolean foundOdd=false;

        for (int count: table){
            if (count % 2 == 1){
                if (foundOdd){
                    return false; //more than one odd
                }
                foundOdd=true;
            }
        }
        return true; //if here, it's passed the test
    }

    /*
     * 1.4 Sol 1 Main Driver Function 
     */
    public boolean isPermutationofPalindrome(String phrase){
        int table[] = buildHashTable(phrase);
        return checkOdd(table);

    }



    /*
     * Standard Object Constructors
     */
    public String toString(){
        
        String tester = "abc";
        String permutation ="abcd";
        String palindrome ="Tact Coa";
        char[] string = new char[4];
        string[0]='a';
        string[1]=' ';
        string[2]='b';
        string[3]='\0';
        replaceSpaces(string, string.length-2);
        return (
            "Ch1 Arrays & Strings\n"
            +"1.1 Does 'abc' have all unique chars? answer = "+isUniqueChars(tester)+"\n"
            +"1.2 Are 'abc' & 'abcd' permutations of each other? answer = "+isPermutation(tester, permutation)+"\n"
            +"1.3 Before whitespace replace: 'a b' after: "+string.toString()+"\n"
            +"1.4 Solution 1: Is Tact Coa a permuation of a palindrome? answer = "+isPermutationofPalindrome(palindrome)+"\n"
        );
    }

    public boolean equals (Object object){
        
        //type casting of given object
        Ch1ArraysStrings compare = (Ch1ArraysStrings) object;
        String givenString="validation";
        String compareString="not-validated";
        return (
            compare.isUniqueChars(givenString) == this.isUniqueChars(givenString)
            && compare.isPermutation(givenString, compareString) == this.isPermutation(givenString, compareString)
        );

    }
}
