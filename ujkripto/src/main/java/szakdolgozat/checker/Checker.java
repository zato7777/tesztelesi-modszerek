package szakdolgozat.checker;

import java.util.Arrays;

public class Checker implements CheckerInterface{
    public boolean isResolution(String guess, int currentBlockHash) {
        return Arrays.hashCode(new String[]{String.valueOf(currentBlockHash), guess}) % 100000 == 0;
    }
}
