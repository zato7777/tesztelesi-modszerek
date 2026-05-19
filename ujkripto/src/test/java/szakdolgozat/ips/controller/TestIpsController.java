package szakdolgozat.ips.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.ips.Ips;
import szakdolgozat.ips.reader.writer.IpsReaderWriter;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestIpsController {
    private IpsController ipsController;
    private IpsReaderWriter mockReaderWriter;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        ipsController = IpsController.getInstance();
        mockReaderWriter = mock(IpsReaderWriter.class);
        Field readerWriterField = IpsController.class.getDeclaredField("ipsReaderWriter");
        readerWriterField.setAccessible(true);
        readerWriterField.set(ipsController, mockReaderWriter);
    }

    @Test
    @DisplayName("Test getIps method")
    void testGetIps() {
        Ips ips = mock(Ips.class);
        when(mockReaderWriter.read()).thenReturn(ips);
        assertEquals(ipsController.getIps(), ips);
    }

    @Test
    @DisplayName("Test add method")
    void testAdd() {
        String ip = "127.0.0.1";
        Ips ips = mock(Ips.class);

        when(ips.contains(ip)).thenReturn(false);
        when(mockReaderWriter.read()).thenReturn(ips);

        ipsController.add(ip);
        verify(mockReaderWriter).add(ip);


        String ip2 = "127.0.0.2";
        Ips ips2 = mock(Ips.class);

        when(ips2.contains(ip2)).thenReturn(true);
        when(mockReaderWriter.read()).thenReturn(ips2);

        ipsController.add(ip2);
        verify(mockReaderWriter, never()).add(ip2);
    }
}
