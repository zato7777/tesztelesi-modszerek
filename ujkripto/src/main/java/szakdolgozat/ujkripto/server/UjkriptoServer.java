package szakdolgozat.ujkripto.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import szakdolgozat.block.chain.BlockChain;
import szakdolgozat.receiver.thread.ReceiverThread;
import szakdolgozat.sender.Sender;

public class UjkriptoServer implements UjkriptoServerInterface{
    public static final String BLOCK_CHAIN_END_TOKEN = "BLOKK_LANC_VEGE_TOKEN";

    private static final UjkriptoServer instance = new UjkriptoServer();

    public static final Logger logger = LoggerFactory.getLogger("Ujkripto");
    private final ReceiverThread receiverThread = new ReceiverThread();
    private final Sender sender = new Sender();

    private UjkriptoServer(){}

    public static UjkriptoServer getInstance() {
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
