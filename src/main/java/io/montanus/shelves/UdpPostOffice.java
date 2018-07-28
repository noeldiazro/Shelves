package io.montanus.shelves;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

public class UdpPostOffice implements PostOffice {
    private final String host;
    private final int port;
    private final Charset charset;

    public UdpPostOffice(String host, int port, Charset charset) {
        this.host = host;
        this.port = port;
        this.charset = charset;
    }

    public void sendMessage(String text) {
        try (final DatagramSocket clientSocket = new DatagramSocket()) {
            byte[] payload = text.getBytes(charset);
            final DatagramPacket message =
                    new DatagramPacket(payload, payload.length, InetAddress.getByName(host), port);
            clientSocket.send(message);
        }
        catch (IOException unrecoverable) {
            throw new RuntimeException(String.format("Message '%s' couldn't be sent.", text), unrecoverable);
        }
    }
}
