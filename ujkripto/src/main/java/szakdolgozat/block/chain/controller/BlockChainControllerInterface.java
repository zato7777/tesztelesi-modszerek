package szakdolgozat.block.chain.controller;

public interface BlockChainControllerInterface {
  public int getCurrentBlockHash();

  public void closeCurrentBlock(final String solver, final String guess);
}
