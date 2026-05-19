package szakdolgozat.sender;

import szakdolgozat.block.chain.BlockChain;
import szakdolgozat.config.Config;
import szakdolgozat.ips.controller.IpsController;
import szakdolgozat.property.loader.PropertyLoader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

import static szakdolgozat.ujkripto.server.UjkriptoServer.BLOCK_CHAIN_END_TOKEN;

public class Sender implements SenderInterface{
    private IpsController ipsController = IpsController.getInstance();
    private String ipAddress;
    private int port = Integer.parseInt(PropertyLoader.load(Config.PATH).getProperty(Config.Properties.PORT));
    private String myIpWithPort;

    public Sender() {
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        myIpWithPort = ipAddress + ":" + (port + 1);
    }

    public void send(final BlockChain blockChain) {
        ipsController.getIps().forEach(ip -> {
            createAndSend(ip, myIpWithPort);
            blockChain.parseDataAsStringList().forEach(data -> createAndSend(ip, data));
            createAndSend(ip, BLOCK_CHAIN_END_TOKEN);
        });
    }

    private synchronized void createAndSend(final String ip, final String data) {
        try {
            tryCreateAndSend(ip, data);
        } catch (IOException ignored) {
        }
    }

    private synchronized void tryCreateAndSend(final String ip, final String data) throws IOException {
        DatagramPacket packet = create(ip, data);
        new DatagramSocket().send(packet);
    }

    private synchronized DatagramPacket create(final String ip, final String data) throws UnknownHostException {
        String[] ipParts = ip.split(":");
        byte[] buf = Base64.getEncoder().encode(data.getBytes());
        return new DatagramPacket(buf, buf.length, InetAddress.getByName(ipParts[0]), Integer.parseInt(ipParts[1]));
    }

    public void sendEnd() {
        createAndSend(myIpWithPort, myIpWithPort);
        createAndSend(myIpWithPort, BLOCK_CHAIN_END_TOKEN);
    }
}
