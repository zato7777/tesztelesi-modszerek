package szakdolgozat.mining.thread;

import szakdolgozat.block.chain.controller.BlockChainController;
import szakdolgozat.checker.Checker;
import szakdolgozat.guesser.Guesser;
import szakdolgozat.ujkripto.server.UjkriptoServer;

public class MiningThread extends Thread implements MiningThreadInterface{

    private boolean mining = false;
    private String solver;
    private BlockChainController blockChainController;
    private Guesser guesser = new Guesser();
    private Checker checker = new Checker();

    public boolean isMining() {
        return mining;
    }

    public void setMiningFalse() {
        mining = false;
    }

    @Override
    public void run() {
        UjkriptoServer.getInstance().synchronize();
        mining = true;
        while (mining) {
            String guess = guesser.nextGuess();
            if (checker.isResolution(guess, blockChainController.getCurrentBlockHash())) {
                blockChainController.closeCurrentBlock(solver, guess);
            }
        }
    }

    public void setSolver(final String solver) {
        this.solver = solver;
    }

    public void setBlockChainController(final BlockChainController blockChainController) {
        this.blockChainController = blockChainController;
    }
}
