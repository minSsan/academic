package hw4;
/*
 * CSE2010 Homework #4: BinToDec.java
 *
 * Complete the code below.
 */

public class BinToDec {

    public static int binToDec(String number) {
        if (number.length() == 1) {
            // Base case
            return Integer.parseInt(number);
        } else {
            // Recursive case
            if(number.charAt(0) == '0') return binToDec(number.substring(1));
            int n = 1;
            for(int i = 1; i < number.length(); ++i) {
                n *= 2;
            }
            return n + binToDec(number.substring(1));
        }
    }

    // Tail-recursion
    public static int binToDecTR(String number, int result) {
        if (number.length() == 1) {
            // Base case
            return Integer.parseInt(number) + result;
        } else {
            // Recursive case
            int n;
            if(number.charAt(0) == '0') {
                n = 0;
            } else {
                n = 1;
                for(int i = 1; i < number.length(); ++i) {
                    n *= 2;
                }
            }
            return binToDecTR(number.substring(1), result + n);
        }
    }

}
