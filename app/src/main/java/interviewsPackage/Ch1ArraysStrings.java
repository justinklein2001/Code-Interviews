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
     * "Write a method to replace all spaces in a string with '%20'. You may assume that the string has sufficient space at the end to hold the additional characters."
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
     * 1.4 Solution 2 - Checking for odds as we search through the string,
     * slightly more o
     */
    public boolean isPermutationofPalindrome2(String phrase){
        
        int table[] = new int[26]; //26 letters in alphabet
        int oddCount=0;

        //foreach loop to search through entire phrase
        for (char c: phrase.toCharArray()){
            int x = charToInt(c);

            //if it is apart of the alphabet
            if (x !=-1){
                if ((table[x] %2) == 1){
                    //increment the odd count
                    oddCount++;
                } else {
                    //ran into a duplicate, now no longer an odd count
                    oddCount--;
                }
            }
        }
        //returns true if palindrome, false if not
        return (oddCount <= 1);

    }

    /* 
     * 1.4 Solution 3 - Using bitwise operators, super complicated,
     * took me three days to figure out
     */
    
     /**
      * 
      * @param bitVector - the bit used to to flip bits to specify that a character was 
      * @param index - current numeric value of the char being looked at in the String
      * @return - the newbit mask
      */
     private int toggle(int bitVector, int index){
        
        //error checking, only want alphabetical values
        if (index < 0){
            return bitVector;
        }

        int mask = 1 << index; //shift one to the bit that it represents
        bitVector ^= mask; //XOR it with itself so that if it already exists, flips back to 0
        return bitVector;
     }

     /**
      * 
      * @param phrase - given string
      * @return - the bitVector that represents the string
      */
     private int createBitVector(String phrase){
        int bitVector = 0;
        for (char c :phrase.toCharArray()){
            int x = charToInt(c); //convert to int
            bitVector = toggle(bitVector, x); //toggle the ith bit at char location
        }
        return bitVector;
     }

     /**
      * Example: 00010000-1 = 00001111
                 00010000 & 00001111 = 0
      * @param bitVector - the bitvector that holds all the locations of the chars
      * @return -if its a permuation or not
      */
    private boolean checkAtMostOneBitSet(int bitVector){
        return((bitVector & (bitVector-1)) == 0);
    }

    private boolean isPermutationofPalindrome3(String phrase){
         int bitVector = createBitVector(phrase);
         return checkAtMostOneBitSet(bitVector);
    }

    /* 1.5 Question: There are three types of edits that can be performed on strings: insert a character, remove a character,
     * or replace a character, or replace a character. Given two strings, write a function to check if they are one edit (or zero edits)
     */
    
    /**
     * @param one -first string given to check
     * @param two -second string given to check
     * @return - boolean value telling you if the two strings 
     * truly are one edit away to being equivalent
     */
    public boolean oneEditAway(String one, String two){
        if (one.length() == two.length()){
            return (replaceEdit(one, two));
        } else if ((one.length()+1) == two.length()){
            insertDelete(one, two);
        } else if ((one.length()-1) == two.length()){
            insertDelete(two, one);
        }
        return false;
    }

    /**
     * @param one -first string given to check
     * @param two -second string given to check
     * @return - boolean value if they can be edited
     */
    private boolean replaceEdit(String one, String two){
        
        boolean foundDiff = false;

        for (int i=0; i<one.length(); i++){
            if (one.charAt(i) != two.charAt(i)){
                if (foundDiff){
                    return false; //if here, we already found a difference, no go
                }
                foundDiff=true;
            }
        }
        return true;
    }

    /**
     * @param one -longer string
     * @param two - shorter string
     * @return -if they are one edit away
     */
    private boolean insertDelete(String one, String two){
        int i=0;
        int j=0;

        //loop through both strings
        while (i < one.length() && j < two.length()){
            //check if they're not the same
            if (one.charAt(i) != two.charAt(j)){
                //if they're not equal, one is ahead
                if (i != j){
                    return false; //if not the same, not one edit away
                }
                j++; //keep moving
            } else {
                i++;
                j++;
            }
        }
        return true;
        
    }

    public boolean oneEditAway2(String one, String two){
        
        //checking the length
        if (Math.abs(one.length() - two.length()) > 1){
            return false;
        }

        //clever way to get the longest string
        String s1 = (one.length() < two.length() ? one: two);
        String s2 = (one.length() < two.length() ? two: one);

        int i=0;
        int j=0;
        boolean foundDiff=false;

        //loop through until end of string
        while (i < s1.length() && j < s2.length()){
            //if they're not the same we have some work to do
            if (s1.charAt(i) != s2.charAt(j)){

                //multiple differences, nope
                if (foundDiff){
                    return false;
                }
                foundDiff=true;
                //replace sine the same length
                if (s1.length() == s2.length()){
                    i++; //move shorter string 
                }
            } else {
                i++; //always move shorter if matching
            }
            j++;//second string is always moved
        }
        return true;
    }

    /* 1.6 Question: There are three types of edits that can be performed on strings: insert a char
     * or replace a character, or replace a character. Given two strings, write a function to check if they are one edit (or zero edits)
     */


    /*Inefficient Solution*/
    public String compressBad (String str){
        
        String newString = "";
        int consecutive =0;

        for (int i=0; i < str.length(); i++){
            
            consecutive++;

            if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i+1)){
                newString += ""+str.charAt(i)+consecutive;
                consecutive=0;
            }

        }
        return newString;
    }

    /*String Builder Solution*/
    public String compressGood(String given){

        //count of consecutive strings
        int consecutive=0;

        //string builder, does not have 6 different sequences to copy
        StringBuilder comped= new StringBuilder();

        for (int i=0; i < given.length(); i++){

            consecutive++;

            //check to see if the next one is different, if so append
            if (i+1 >= given.length() || given.charAt(i) != given.charAt(i+1)){
                comped.append(given.charAt(i));
                comped.append(consecutive);
                consecutive=0;
            }
        }

        //checking after you built it, return smallest one
        return (given.length() <= comped.length()? given : comped.toString());
    }

    /*1.7 Rotate Matrix: Given an image represented by an N x N matrix, where each pixel in the image
     *is represented by an integer, wirte a method to rotate the image by 90 degrees. Can you do this in place?*/

    public boolean rotateMatrix(int matrix[][]){

        //edge case testing
        if (matrix.length == 0 || matrix.length != matrix[0].length){
            return false;
        }
        printMatrix(matrix);
        //get number of rows
        int n = matrix.length;
        System.out.println("Number of layers: "+(n/2));
        


        //looping through each "layer" of the matrix
        for (int layer=0; layer < n/2; layer++){
            //getting the first row entry in the layer            
            int first = layer;
            //getting the last entry in the layer
            int last = n-1-layer;

            for (int i=first; i <last; i++){
                
                //the iterator
                int offset = i-first;
                int top = matrix[first][i];
                System.out.println("Offset: "+offset+" OG top: "+top);

                //left -> top
                matrix[first][i] = matrix[last-offset][first];

                //bottom -> left
                matrix[last-offset][first] =matrix[last][last-offset];

                //right ->bottom
                matrix[last][last-offset]=matrix[i][last];

                //top -> right
                matrix[i][last] = top; //right ->saved top


            }
        }
        printMatrix(matrix);
        return true;

    }


    public void printMatrix(int matrix[][]){

        for (int i=0; i <matrix.length; i++){
            System.out.print("[");
            for (int j=0; j < matrix[0].length; j++){
                System.out.print("{"+matrix[i][j]+"}");
            }
            System.out.println("]");
        }
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

        int matrix[][] = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,16}
        };

        int matrixOdd[][] = {
            {1,2,3},
            {4,5,6},
            {7,8,9},
        };
        return (
            "Ch1 Arrays & Strings\n"
            +"1.1 Does 'abc' have all unique chars? answer = "+isUniqueChars(tester)+"\n"
            +"1.2 Are 'abc' & 'abcd' permutations of each other? answer = "+isPermutation(tester, permutation)+"\n"
            +"1.3 Before whitespace replace: 'a b' after: "+string.toString()+"\n"
            +"1.4 Solution 1: Is Tact Coa a permuation of a palindrome? answer = "+isPermutationofPalindrome(palindrome)+"\n"
            +"1.4 Solution 2: Is Tact Coa a permuation of a palindrome? answer = "+isPermutationofPalindrome2(palindrome)+"\n"
            +"1.4 Solution 3: Is Tact Coa a permuation of a palindrome? answer = "+isPermutationofPalindrome3(palindrome)+"\n"
            +"1.5 Solution 1: Is pale, ple, one edit away? answer = "+oneEditAway("pale", "ple")+"\n"
            +"1.5 Solution 2: Is pale, ple, one edit away? answer = "+oneEditAway2("ple", "pale")+"\n"
            +"1.6 Solution 1 (Bad): Compressed aaabbccaaa answer = "+compressBad("aaabbccaaa")+"\n"
            +"1.6 Solution 2 (Good): Compressed aaabbccaaa answer = "+compressGood("aaabbccaaa")+"\n"
            +"1.7 Matrix Rotation with 4 X 4 Matrix:"+rotateMatrix(matrix)+"\n"
            +"1.7 Matrix Rotation with 3 X 3 Matrix:"+rotateMatrix(matrixOdd)+"\n"
            
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
