package szakdolgozat.mining.thread;

import org.junit.jupiter.api.Test;
import szakdolgozat.block.chain.controller.BlockChainControllerInterface;
import szakdolgozat.checker.CheckerInterface;
import szakdolgozat.guesser.GuesserInterface;
import szakdolgozat.ujkripto.server.UjkriptoServerFactoryInterface;
import szakdolgozat.ujkripto.server.UjkriptoServerInterface;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMiningThread {

  @Test
  public void isMiningFalseByDefault() {
    MiningThread miningThread = new MiningThread(
      new UjkriptoServerFactoryStub(),
      new GuesserStub(),
      new CheckerStub()
    );

    assertFalse(miningThread.isMining());
  }

  @Test
  public void isMiningTrueWhileRunStartedInThread() {
    MiningThread miningThread = new MiningThread(
      new UjkriptoServerFactoryStub(),
      new GuesserStub(),
      new CheckerStub()
    );

    miningThread.setBlockChainController(new BlockChainControllerStub());
    miningThread.setSolver("Test");

    Thread thread = new Thread(miningThread);

    thread.start();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    assertTrue(miningThread.isMining());

    miningThread.setMiningFalse();
  }

  @Test
  public void isMiningFalseWhenRunStartedInThreadThenMiningSetToFalse() {
    MiningThread miningThread = new MiningThread(
      new UjkriptoServerFactoryStub(),
      new GuesserStub(),
      new CheckerStub()
    );

    miningThread.setBlockChainController(new BlockChainControllerStub());
    miningThread.setSolver("Test");

    Thread thread = new Thread(miningThread);

    thread.start();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    miningThread.setMiningFalse();

    assertFalse(miningThread.isMining());
  }

  private class UjkriptoServerFactoryStub implements UjkriptoServerFactoryInterface {

    public UjkriptoServerInterface newUjkriptoServer() {
      return new UjkriptoServerStub();
    }
  }

  private class UjkriptoServerStub implements UjkriptoServerInterface {

    public void synchronize() {

    }
  }

  private class GuesserStub implements GuesserInterface {

    public String nextGuess() {
      return "";
    }
  }

  private class CheckerStub implements CheckerInterface {

    public boolean isResolution(String guess, int currentBlockHash) {
      return false;
    }
  }

  private class BlockChainControllerStub implements BlockChainControllerInterface {

    public int getCurrentBlockHash() {
      return 0;
    }

    public void closeCurrentBlock(String solver, String guess) {

    }
  }
}
