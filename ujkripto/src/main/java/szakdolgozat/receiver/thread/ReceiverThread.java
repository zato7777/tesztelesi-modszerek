package szakdolgozat.receiver.thread;

import szakdolgozat.block.Block;
import szakdolgozat.block.chain.BlockChain;
import szakdolgozat.block.chain.controller.BlockChainController;
import szakdolgozat.block.chain.validator.BlockChainValidator;
import szakdolgozat.config.Config;
import szakdolgozat.ips.controller.IpsController;
import szakdolgozat.property.loader.PropertyLoader;
import szakdolgozat.transaction.Transaction;
import szakdolgozat.ujkripto.server.UjkriptoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.stream.IntStream;

import static szakdolgozat.ujkripto.server.UjkriptoServer.BLOCK_CHAIN_END_TOKEN;
import static szakdolgozat.ujkripto.server.UjkriptoServer.logger;

public class ReceiverThread extends Thread implements ReceiverThreadInterface{
    private boolean receive = false;
    private DatagramSocket socket;
    private BlockChainValidator blockChainValidator = new BlockChainValidator();

    public ReceiverThread() {
        try {
            socket = new DatagramSocket(
                    Integer.parseInt(PropertyLoader.load(Config.PATH).getProperty(Config.Properties.PORT)) + 1);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        receive = true;
        while (receive) {
            try {
                final String packet = getPacket();
                IpsController.getInstance().add(packet);
                logger.info("[RECEIVER THREAD]" + packet);
                String data = getPacket();
                ArrayList<String> blockChainData = new ArrayList<>();
                while (!data.equals(BLOCK_CHAIN_END_TOKEN)) {
                    blockChainData.add(data);
                    data = getPacket();
                }
                process(blockChainData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(final ArrayList<String> blockChainData) {
        final BlockChain newBlockChain = new BlockChain(blockChainData);
        final BlockChain blockChain = BlockChainController.getInstance().getBlockChain();
        if (!blockChain.equals(newBlockChain)) {
            if (blockChainValidator.isValid(newBlockChain) &&
                    blockChain.size() <= newBlockChain.size()) {
                BlockChainController.getInstance().changeBlockChain(merge(blockChain, newBlockChain));
            } else {
                UjkriptoServer.getInstance().send(blockChain);
            }
        }
    }

    private BlockChain merge(final BlockChain blockChain, final BlockChain newBlockChain) {
        if (newBlockChain.subList(0, blockChain.size()).equals(blockChain)) return newBlockChain;
        if (newBlockChain.subList(0, newBlockChain.size() - 1).equals(blockChain.subList(0, blockChain.size() - 1)))
            return mergeLastBlock(blockChain, newBlockChain);
        return blockChain;
    }

    private BlockChain mergeLastBlock(final BlockChain blockChain, final BlockChain newBlockChain) {
        BlockChain ret = new BlockChain();
        ret.addAll(blockChain);
        ret.getLast().setTransactions(
                merge(blockChain.get(blockChain.size() - 1), newBlockChain.get(blockChain.size() - 1)));
        return ret;
    }

    private ArrayList<Transaction> merge(final Block block, final Block newBlock) {
        final ArrayList<Transaction> transactions = block.getTransactions();
        final ArrayList<Transaction> newTransactions = newBlock.getTransactions();
        if (transactions.containsAll(newTransactions)) return transactions;
        if (newTransactions.containsAll(transactions)) return newTransactions;

        ArrayList<Transaction> ret = new ArrayList<>();
        int index = 0;
        int newIndex = 0;
        while (index < transactions.size() && newIndex < newTransactions.size()) {
            if (newTransactions.get(newIndex).getTimeStamp() < transactions.get(index).getTimeStamp()) {
                ret.add(newTransactions.get(newIndex));
                newIndex++;
            } else {
                ret.add(transactions.get(index));
                index++;
            }
        }
        while (index < transactions.size()) {
            ret.add(transactions.get(index));
            index++;
        }
        while (newIndex < newTransactions.size()) {
            ret.add(newTransactions.get(newIndex));
            newIndex++;
        }
        return ret;
    }

    private String getPacket() throws IOException {
        byte[] receive = new byte[65535];
        DatagramPacket packet = new DatagramPacket(receive, receive.length);
        socket.receive(packet);
        return getData(receive);
    }

    private static String getData(byte[] a) {
        int lenght = 0;
        while (a[lenght] != 0) {
            lenght++;
        }

        byte[] message = new byte[lenght];

        IntStream.range(0, lenght).forEach(index -> message[index] = a[index]);

        return new String(Base64.getDecoder().decode(message)).trim();
    }


    public void stopReceiving() {
        receive = false;
    }
}
