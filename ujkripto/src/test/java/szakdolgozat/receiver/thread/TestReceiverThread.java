package szakdolgozat.receiver.thread;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import szakdolgozat.block.Block;
import szakdolgozat.block.chain.BlockChain;
import szakdolgozat.config.Config;
import szakdolgozat.property.loader.PropertyLoader;
import szakdolgozat.transaction.Transaction;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestReceiverThread {
    private MockedStatic<PropertyLoader> mockedPropertyLoader;

    @BeforeEach
    void setUp() {
        mockedPropertyLoader = mockStatic(PropertyLoader.class);
        Properties properties = new Properties();
        properties.setProperty(Config.Properties.PORT, "-1");
        mockedPropertyLoader.when(() -> PropertyLoader.load(anyString())).thenReturn(properties);
    }

    @AfterEach
    void tearDown() {
        mockedPropertyLoader.close();
    }

    @Test
    @DisplayName("Test stopReceiving method")
    void testStopReceiving() throws IllegalAccessException, NoSuchFieldException {
        ReceiverThread receiverThread = new ReceiverThread();
        receiverThread.stopReceiving();

        Field receiveField = ReceiverThread.class.getDeclaredField("receive");
        receiveField.setAccessible(true);
        boolean isReceive = (boolean) receiveField.get(receiverThread);

        assertFalse(isReceive);
    }

    @Test
    @DisplayName("Test getData method")
    void testGetData() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = ReceiverThread.class.getDeclaredMethod("getData", byte[].class);
        method.setAccessible(true);

        String text = "teszt";
        byte[] encodedText = Base64.getEncoder().encode(text.getBytes());
        byte[] buffer = new byte[65535];
        System.arraycopy(encodedText, 0, buffer, 0, encodedText.length);
        String result = method.invoke(null, (Object) buffer).toString();

        assertEquals(text, result);
    }

    @Test
    @DisplayName("Test BlockChain merge(final BlockChain blockChain, final BlockChain newBlockChain) method")
    void testMergeBlockChain() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ReceiverThread.class.getDeclaredMethod("merge", BlockChain.class, BlockChain.class);
        method.setAccessible(true);

        ReceiverThread receiverThread = new ReceiverThread();

        BlockChain blockChain = new BlockChain();
        Block block = mock(Block.class);
        blockChain.add(block);

        BlockChain blockChain2 = new BlockChain();
        blockChain2.add(block);
        Block block2 = mock(Block.class);
        blockChain2.add(block2);

        BlockChain mergedBlockChains = (BlockChain) method.invoke(receiverThread, blockChain, blockChain2);

        assertEquals(2, mergedBlockChains.size());
        assertEquals(blockChain2, mergedBlockChains);
    }

    @Test
    @DisplayName("Test ArrayList<Transaction> merge(final Block block, final Block newBlock) method")
    void testMerge() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ReceiverThread.class.getDeclaredMethod("merge", Block.class, Block.class);
        method.setAccessible(true);

        ReceiverThread receiverThread = new ReceiverThread();

        Transaction transaction = mock(Transaction.class);
        when(transaction.getTimeStamp()).thenReturn(100L);

        Transaction transaction2 = mock(Transaction.class);
        when(transaction2.getTimeStamp()).thenReturn(200L);

        Block block = mock(Block.class);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction2);
        when(block.getTransactions()).thenReturn(transactions);

        Block block2 = mock(Block.class);
        ArrayList<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(transaction);
        when(block2.getTransactions()).thenReturn(transactions2);

        ArrayList<Transaction> mergedTransactions = (ArrayList<Transaction>) method.invoke(receiverThread, block, block2);

        assertAll("Test merge by timestamp",
                () -> assertEquals(2, mergedTransactions.size()),
                () -> assertEquals(transaction, mergedTransactions.get(0)),
                () -> assertEquals(transaction2, mergedTransactions.get(1))
        );
    }

    @Test
    @DisplayName("Test mergeLastBlock method")
    void testMergeLastBlock() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ReceiverThread.class.getDeclaredMethod("mergeLastBlock", BlockChain.class, BlockChain.class);
        method.setAccessible(true);

        ReceiverThread receiverThread = new ReceiverThread();

        Transaction transaction = mock(Transaction.class);
        when(transaction.getTimeStamp()).thenReturn(100L);

        Transaction transaction2 = mock(Transaction.class);
        when(transaction2.getTimeStamp()).thenReturn(200L);

        Block block = mock(Block.class);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        when(block.getTransactions()).thenReturn(transactions);

        Block block2 = mock(Block.class);
        ArrayList<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(transaction2);
        when(block2.getTransactions()).thenReturn(transactions2);

        BlockChain blockChain = new BlockChain();
        blockChain.add(block);

        BlockChain blockChain2 = new BlockChain();
        blockChain2.add(block2);

        BlockChain mergedResult = (BlockChain) method.invoke(receiverThread, blockChain, blockChain2);

        ArgumentCaptor<ArrayList<Transaction>> captor = ArgumentCaptor.forClass(ArrayList.class);
        verify(block).setTransactions(captor.capture());
        ArrayList<Transaction> transactionsCaptured = captor.getValue();

        assertAll("Test merge by timestamp",
                () -> assertEquals(1, mergedResult.size()),
                () -> assertEquals(2, transactionsCaptured.size()),
                () -> assertEquals(transaction, transactionsCaptured.get(0)),
                () -> assertEquals(transaction2, transactionsCaptured.get(1))
        );
    }

}
