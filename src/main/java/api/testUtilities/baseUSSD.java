/**
 * This class is the start to all USSD test
 * All USSD test start with the same API call to enter into the menu
 * Author: Adam Bethlehem
 */
package api.testUtilities;

import api.testUtilities.testConfig;

import java.util.Random;


public class baseUSSD extends testConfig {





    public static String getRandomDoubleBetweenRange(){

        Random rand = new Random();
        int num = rand.nextInt(900000000) + 1000000000;
        String result = Integer.toString(num);
        return result;
    }

}
