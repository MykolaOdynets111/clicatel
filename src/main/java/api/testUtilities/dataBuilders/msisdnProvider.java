package api.testUtilities.dataBuilders;

import java.util.Random;

public class msisdnProvider {

    public  String getRandomDoubleBetweenRange(){

        Random rand = new Random();
        int num = rand.nextInt(900000000) + 1000000000;
        String result = Integer.toString(num);
        return result;
    }
}
