package io.montanus.shelves.udp.test;

import io.montanus.shelves.UdpPostOffice;

import java.io.IOException;
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

}
