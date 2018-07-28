package io.montanus.shelves.udp.test;

import io.montanus.shelves.PostOffice;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Date;

public class LearnUdpClientTest {
    public static void main(String[] args) throws IOException {
        final String host = "localhost";
        final int port = 5358;
        final Charset charset = Charset.defaultCharset();

        final String text = "Hello, world. " + new Date();

        new UdpPostOffice(host, port, charset).sendMessage(text);
    }

    private static class UdpPostOffice implements PostOffice {
        private final String host;
        private final int port;
        private final Charset charset;

        private UdpPostOffice(String host, int port, Charset charset) {
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
}
