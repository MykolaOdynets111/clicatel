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

        // Generate random integers in range from 0 to 9999
        String rand_int1 = String.format("%04d", rand.nextInt(numberCount));
        //String RandomNumbers = Integer.toString(rand_int1);

        return rand_int1;

    }

}
