package szakdolgozat.miner;

import org.junit.jupiter.api.Test;
import szakdolgozat.block.chain.controller.BlockChainController;
import szakdolgozat.block.chain.controller.BlockChainControllerInterface;
import szakdolgozat.mining.thread.MiningThreadFactoryInterface;
import szakdolgozat.mining.thread.MiningThreadInterface;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMiner {

  @Test
  public void isMiningFalseByDefault() {
    MiningThreadFactoryInterface miningThreadFactory = new MiningThreadFactoryStub();
    Miner miner = new Miner(miningThreadFactory);

    assertFalse(miner.isMining());
  }

  @Test
  public void isMiningTrueAfterStart() {
    MiningThreadFactoryInterface miningThreadFactory = new MiningThreadFactoryStub();
    Miner miner = new Miner(miningThreadFactory);

    miner.start("sdf", BlockChainController.getInstance());

    assertTrue(miner.isMining());
  }

  @Test
  public void isMiningFalseAfterStartThenStop() {
    MiningThreadFactoryInterface miningThreadFactory = new MiningThreadFactoryStub();
    Miner miner = new Miner(miningThreadFactory);

    miner.start("sdf", BlockChainController.getInstance());
    miner.stop();

    assertFalse(miner.isMining());
  }

  private class MiningThreadFactoryStub implements MiningThreadFactoryInterface {

    public MiningThreadInterface newMiningThread() {
      return new MiningThreadStub();
    }
  }

  private class MiningThreadStub implements MiningThreadInterface {

    private boolean isMining = false;

    public boolean isMining() {
      return isMining;
    }

    public void setMiningFalse() {
      isMining = false;
    }

    public void setSolver(String solver) {

    }

    public void setBlockChainController(BlockChainControllerInterface blockChainController) {

    }

    public void start() {
      isMining = true;
    }
  }
}
