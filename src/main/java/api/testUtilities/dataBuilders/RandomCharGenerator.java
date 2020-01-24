/* Random number generator class intended to replace or add a random array of numbers
 *
 * Author: Juan-Claude Botha
 */


package api.testUtilities.dataBuilders;

import java.util.Random;

public class RandomCharGenerator {

    public static String getRandomNumbers(int numberCount){

        // Create instance of Random class
        Random rand = new Random();

        // Generate random integers in range from 0 to 999
        int rand_int1 = rand.nextInt(numberCount);
        String RandomNumbers = Integer.toString(rand_int1);

        return RandomNumbers;

    }

}
