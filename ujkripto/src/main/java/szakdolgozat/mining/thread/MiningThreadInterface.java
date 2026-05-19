package szakdolgozat.mining.thread;

import szakdolgozat.block.chain.controller.BlockChainControllerInterface;

public interface MiningThreadInterface {
  public boolean isMining();

  public void setMiningFalse();

  public void setSolver(final String solver);

  public void setBlockChainController(final BlockChainControllerInterface blockChainController);

  public void start();
}
