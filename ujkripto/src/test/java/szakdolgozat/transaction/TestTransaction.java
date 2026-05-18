package szakdolgozat.transaction;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransaction {
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
    }

    @Test
    public void testGetSetTimestamp() {
        transaction.setTimeStamp(1);
        assertEquals(transaction.getTimeStamp(), 1);

        transaction.setTimeStamp(2);
        assertEquals(transaction.getTimeStamp(), 2);
    }

    @Test
    public void testGetSetSender() {
        transaction.setSender("Laci");
        assertEquals(transaction.getSender(), "Laci");

        transaction.setSender("Norbi");
        assertEquals(transaction.getSender(), "Norbi");
    }

    @Test
    public void testGetSetReceiver() {
        transaction.setReceiver("Laci");
        assertEquals(transaction.getReceiver(), "Laci");

        transaction.setReceiver("Norbi");
        assertEquals(transaction.getReceiver(), "Norbi");
    }

    @Test
    public void testGetSetAmount() {
        transaction.setAmount(1.32321);
        assertEquals(transaction.getAmount(), 1.32321, 0.001);

        transaction.setAmount(4.32321);
        assertEquals(transaction.getAmount(), 4.32321, 0.001);
    }

    @Test
    public void testGetSetSignature() {
        transaction.setSignature("Laci");
        assertEquals(transaction.getSignature(), "Laci");

        transaction.setSignature("Norbi");
        assertEquals(transaction.getSignature(), "Norbi");
    }

    @Test
    public void testOarseDataAsStringList() {
        transaction.setTimeStamp(23132132);
        transaction.setSender("Norbi");
        transaction.setReceiver("Laci");
        transaction.setAmount(1.32321);
        transaction.setSignature("Signature");

        ArrayList<String> lista = transaction.oarseDataAsStringList();

        assertEquals(lista.size(), 5);
        assertEquals(lista.get(0), String.valueOf(23132132));
        assertEquals(lista.get(1), "Norbi");
        assertEquals(lista.get(2), "Laci");
        assertEquals(lista.get(3), String.valueOf(1.32321));
        assertEquals(lista.get(4), "Signature");

        transaction = new Transaction();

        transaction.setTimeStamp(2313213);
        transaction.setSender("Laci");
        transaction.setReceiver("Norbi");
        transaction.setAmount(1.3232);
        transaction.setSignature("Szignao");

        lista = transaction.oarseDataAsStringList();

        assertEquals(lista.size(), 5);
        assertEquals(lista.get(0), String.valueOf(2313213));
        assertEquals(lista.get(1), "Laci");
        assertEquals(lista.get(2), "Norbi");
        assertEquals(lista.get(3), String.valueOf(1.3232));
        assertEquals(lista.get(4), "Szignao");
    }

    @Test
    public void testEquals() {
        Transaction transaction2 = new Transaction();

        transaction.setTimeStamp(23132132);
        transaction.setSender("Norbi");
        transaction.setReceiver("Laci");
        transaction.setAmount(1.32321);
        transaction.setSignature("Signature");

        transaction2.setTimeStamp(23132132);
        transaction2.setSender("Norbi");
        transaction2.setReceiver("Laci");
        transaction2.setAmount(1.32321);
        transaction2.setSignature("Signature");

        assertEquals(transaction, transaction2);

        transaction2.setTimeStamp(23132);
        assertNotEquals(transaction, transaction2);
        transaction2.setTimeStamp(23132132);

        transaction2.setSender("Zalan");
        assertNotEquals(transaction, transaction2);

    }

    @Test
    public void testHashCode() {
        Transaction transaction2 = new Transaction();

        transaction.setTimeStamp(23132132);
        transaction.setSender("Norbi");
        transaction.setReceiver("Laci");
        transaction.setAmount(1.32321);
        transaction.setSignature("Signature");

        transaction2.setTimeStamp(23132132);
        transaction2.setSender("Norbi");
        transaction2.setReceiver("Laci");
        transaction2.setAmount(1.32321);
        transaction2.setSignature("Signature");

        assertEquals(transaction.hashCode(), transaction2.hashCode());

        transaction2.setTimeStamp(23132);
        assertNotEquals(transaction.hashCode(), transaction2.hashCode());
        transaction2.setTimeStamp(23132132);

        transaction2.setSender("Zalan");
        assertNotEquals(transaction.hashCode(), transaction2.hashCode());

    }
}
