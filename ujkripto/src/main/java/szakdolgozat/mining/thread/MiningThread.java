package szakdolgozat.mining.thread;

import szakdolgozat.block.chain.controller.BlockChainControllerInterface;
import szakdolgozat.checker.Checker;
import szakdolgozat.checker.CheckerInterface;
import szakdolgozat.guesser.Guesser;
import szakdolgozat.guesser.GuesserInterface;
import szakdolgozat.ujkripto.server.UjkriptoServerFactory;
import szakdolgozat.ujkripto.server.UjkriptoServerFactoryInterface;

public class MiningThread extends Thread implements MiningThreadInterface{

    private boolean mining = false;
    private String solver;
    private BlockChainControllerInterface blockChainController;
    private GuesserInterface guesser;
    private CheckerInterface checker;
    private UjkriptoServerFactoryInterface ujkriptoServerFactory;

    public MiningThread(
        UjkriptoServerFactoryInterface ujkriptoServerFactory,
        GuesserInterface guesser,
        CheckerInterface checker
    ) {
      this.ujkriptoServerFactory = ujkriptoServerFactory;
      this.guesser = guesser;
      this.checker = checker;
    }

    public MiningThread() {
      this(
        new UjkriptoServerFactory(),
        new Guesser(),
        new Checker()
        );
    }

    public boolean isMining() {
        return mining;
    }

    public void setMiningFalse() {
        mining = false;
    }

    @Override
    public void run() {
        this.ujkriptoServerFactory.newUjkriptoServer().synchronize();
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

    public void setBlockChainController(final BlockChainControllerInterface blockChainController) {
        this.blockChainController = blockChainController;
    }
}
