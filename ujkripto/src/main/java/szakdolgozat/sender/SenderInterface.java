package szakdolgozat.sender;

import szakdolgozat.block.chain.BlockChain;

public interface SenderInterface {
  public void send(final BlockChain blockChain);
  public void sendEnd();
}
