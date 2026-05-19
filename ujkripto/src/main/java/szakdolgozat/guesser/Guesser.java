package szakdolgozat.guesser;

import java.util.Random;

public class Guesser implements GuesserInterface{

    private Random random = new Random();

    public String nextGuess() {
        final int lenght = random.nextInt(128) + 128;
        char[] guess = new char[lenght];
        for (int index = 0; index < lenght; index++) {
            guess[index] = (char) (random.nextInt(77) + 48);
        }
        return new String(guess);
    }
}
