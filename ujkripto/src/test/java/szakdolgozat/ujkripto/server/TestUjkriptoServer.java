package szakdolgozat.ujkripto.server;

import org.junit.jupiter.api.Test;
import szakdolgozat.block.chain.BlockChain;
import szakdolgozat.receiver.thread.ReceiverThreadInterface;
import szakdolgozat.sender.SenderInterface;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestUjkriptoServer {

  //Because this class is a singleton when running tests in parallel some tests fail,
  //So only 1 test covers the whole class
  @Test
  public void testUjkriptoServer() {
    ReceiverThreadStub receiverThread = new ReceiverThreadStub(1);
    SenderStub sender = new SenderStub();
    UjkriptoServer ujkriptoServer = UjkriptoServer.getInstance(receiverThread, sender);
    ReceiverThreadInterface receiverThread2 = new ReceiverThreadStub(2);
    UjkriptoServer ujkriptoServer2 = UjkriptoServer.getInstance(receiverThread2, sender);

    ReceiverThreadInterface receiverThreadFromUjkriptoServer = ujkriptoServer2.getReceiverThread();
    if (receiverThreadFromUjkriptoServer instanceof ReceiverThreadStub) {
      assertEquals(1, ((ReceiverThreadStub) receiverThreadFromUjkriptoServer).index);
    } else {
      fail();
    }

    receiverThread.startCalled = false;
    sender.sendEndCalled = false;

    ujkriptoServer.start();

    assertTrue(receiverThread.startCalled);
    assertTrue(sender.sendEndCalled);

    receiverThread.stopReceivingCalled = false;
    sender.sendEndCalled = false;

    ujkriptoServer.stop();

    assertTrue(receiverThread.stopReceivingCalled);
    assertTrue(sender.sendEndCalled);

    sender.sendCalled = false;

    ujkriptoServer.send(new BlockChain(new ArrayList<String>()));

    assertTrue(sender.sendCalled);

    sender.sendEndCalled = false;

    ujkriptoServer.synchronize();

    assertTrue(sender.sendEndCalled);
  }

  private static class ReceiverThreadStub implements ReceiverThreadInterface {

    public final int index;
    public boolean startCalled = false;
    public boolean stopReceivingCalled = false;

    public ReceiverThreadStub(int index) {
      this.index = index;
    }

    public void start() {
        this.startCalled = true;
    }

    public void stopReceiving() {
        this.stopReceivingCalled = true;
    }
  }

  private static class SenderStub implements SenderInterface {

    public boolean sendEndCalled = false;
    public boolean sendCalled = false;

    public void send(BlockChain blockChain) {
      this.sendCalled = true;
    }

    public void sendEnd() {
      this.sendEndCalled = true;
    }
  }
}
