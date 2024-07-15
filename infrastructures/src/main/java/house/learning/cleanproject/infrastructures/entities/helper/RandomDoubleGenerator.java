package house.learning.cleanproject.infrastructures.entities.helper;

import java.util.Random;

public class RandomDoubleGenerator {

    private static final Random RANDOM = new Random();

    public static double generate(double min, double max) {
        double price = min + (max - min) * RANDOM.nextDouble();
        return Math.round(price * 100.0) / 100.0;
    }
}
