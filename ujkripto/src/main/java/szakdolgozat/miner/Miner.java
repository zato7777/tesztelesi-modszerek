package szakdolgozat.miner;

import szakdolgozat.block.chain.controller.BlockChainController;
import szakdolgozat.mining.thread.MiningThreadFactory;
import szakdolgozat.mining.thread.MiningThreadFactoryInterface;
import szakdolgozat.mining.thread.MiningThreadInterface;

public class Miner {

    private final MiningThreadFactoryInterface miningThreadFactory;
    private MiningThreadInterface miningThread;

    public Miner(MiningThreadFactoryInterface miningThreadFactory){
      this.miningThreadFactory = miningThreadFactory;
      this.miningThread = miningThreadFactory.newMiningThread();
    }

    public Miner() {
      this.miningThreadFactory = new MiningThreadFactory();
      this.miningThread = miningThreadFactory.newMiningThread();
    }

    public boolean isMining() {
        return miningThread.isMining();
    }

    public void stop() {
        miningThread.setMiningFalse();
    }

    public void start(final String publicKeyAsString, BlockChainController blockChainController) {
        miningThread = miningThreadFactory.newMiningThread();
        miningThread.setSolver(publicKeyAsString);
        miningThread.setBlockChainController(blockChainController);
        miningThread.start();
    }
}
