package szakdolgozat.ujkripto.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import szakdolgozat.block.chain.BlockChain;
import szakdolgozat.receiver.thread.ReceiverThread;
import szakdolgozat.receiver.thread.ReceiverThreadInterface;
import szakdolgozat.sender.Sender;
import szakdolgozat.sender.SenderInterface;

public class UjkriptoServer implements UjkriptoServerInterface{
    public static final String BLOCK_CHAIN_END_TOKEN = "BLOKK_LANC_VEGE_TOKEN";

    private static UjkriptoServer instance;

    public static final Logger logger = LoggerFactory.getLogger("Ujkripto");
    private final ReceiverThreadInterface receiverThread;
    private final SenderInterface sender;

    private UjkriptoServer(
      ReceiverThreadInterface receiverThread,
      SenderInterface sender
    ) {
      this.receiverThread = receiverThread;
      this.sender = sender;
    }

  public ReceiverThreadInterface getReceiverThread() {
    return receiverThread;
  }

  public static UjkriptoServer getInstance() {
      return getInstance(new ReceiverThread(),new Sender());
    }

    public static UjkriptoServer getInstance(
      ReceiverThreadInterface receiverThread,
      SenderInterface sender
    ) {
      if (instance == null) {
        instance = new UjkriptoServer(receiverThread, sender);
      }
      return instance;
    }

    public void start() {
        receiverThread.start();
        synchronize();
    }

    public void stop() {
        receiverThread.stopReceiving();
        sender.sendEnd();
    }

    public void send(final BlockChain blockChain) {
        sender.send(blockChain);
    }

    public void synchronize() {
        sender.sendEnd();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
