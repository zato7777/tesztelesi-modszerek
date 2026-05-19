package szakdolgozat.mining.thread;

import szakdolgozat.block.chain.controller.BlockChainController;

public interface MiningThreadInterface {
  public boolean isMining();

  public void setMiningFalse();

  public void setSolver(final String solver);

  public void setBlockChainController(final BlockChainController blockChainController);

  public void start();
}
