package interviewsPackage;

public class ExtraPractice {

    /**
     * You are given an array prices where prices[i] is the price of a given stock on the ith day.
     * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
     * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
     */

    /**
     * Own Solution, O(n) time
     */
    public static int stockPick(int[] stocks){
        if (stocks.length == 0){
            return 0;
        }
        int min=stocks[0];
        int minPos=0;

        //find lowest stock price
        for (int i=0; i<stocks.length; i++){
            if (stocks[i] <= min){
                min=stocks[i];
                minPos=i;
            }
        }
        //make sure that you didn't get the lowest price on the last day of the stock market
        if ((minPos+1) < stocks.length) {
            int max=stocks[minPos+1];
            //once found loop from there until the end to find the max
            for (int j = minPos + 1; j<stocks.length; j++){
                    if (stocks[j] >= max){
                        max=stocks[j];
                    }
            }
            if ((max - min) > 0){
                return  (max-min);
            }
        }
        return 0;
    }

    /**
     * Given an array of strings words representing an English Dictionary,
     * return the longest word in words that can be built one character at a time by other words in words.
     * If there is more than one possible answer, return the longest word with the smallest lexicographical order.
     * If there is no answer, return the empty string.
     */
}
