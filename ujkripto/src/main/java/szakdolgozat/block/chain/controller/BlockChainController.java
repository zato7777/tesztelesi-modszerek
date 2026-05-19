package szakdolgozat.block.chain.controller;

import szakdolgozat.balancer.Balancer;
import szakdolgozat.block.Block;
import szakdolgozat.block.chain.BlockChain;
import szakdolgozat.block.chain.reader.writer.BlockChainReaderWriter;
import szakdolgozat.checker.Checker;
import szakdolgozat.key.NamedKeyPair;
import szakdolgozat.transaction.Transaction;

import java.util.ArrayList;

public class BlockChainController implements BlockChainControllerInterface{

    private static final BlockChainController instance = new BlockChainController();
    private BlockChainReaderWriter blockChainReaderWriter = new BlockChainReaderWriter();
    private final Balancer balancer = Balancer.getInstance();
    private Checker checker = new Checker();
    private Encrypter encrypter = new Encrypter();

    private BlockChainController(){}

    public static BlockChainController getInstance() {
        return instance;
    }

    public double getBalanceFor(final String publicKeyAsString) {
        BlockChain blockChain = blockChainReaderWriter.read();
        return balancer.getBalance(blockChain, publicKeyAsString);
    }

    public int getCurrentBlockHash() {
        final BlockChain blockChain = blockChainReaderWriter.read();
        return blockChain.getLast().hashCode();
    }

    public void closeCurrentBlock(final String solver, final String guess) {
        BlockChain blockChain = blockChainReaderWriter.read();
        int hash = blockChain.getLast().hashCode();
        if (checker.isResolution(guess, hash)) {
            final Block block = new Block();
            block.setTimeStamp(System.currentTimeMillis());
            block.setPreviousBlockHash(String.valueOf(hash));
            block.setResolution(guess);
            block.setResolver(solver);
            block.setTransactions(new ArrayList<>());
            blockChainReaderWriter.save(block);
        }
    }

    public void addTransaction(final NamedKeyPair namedKeyPair, final String receiver, final double amount) {
        Transaction transaction = new Transaction();
        transaction.setTimeStamp(System.currentTimeMillis());
        transaction.setAmount(amount);
        transaction.setReceiver(receiver);
        transaction.setSender(namedKeyPair.getPublicKey());
        transaction.setSignature(encrypter.encrypt(String.valueOf(transaction.hashCode()), namedKeyPair));
        blockChainReaderWriter.save(transaction);
    }

    public BlockChain getBlockChain() {
        return blockChainReaderWriter.read();
    }

    public void changeBlockChain(final BlockChain blockChain) {
        blockChainReaderWriter.changeTo(blockChain);
    }
}
