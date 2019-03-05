package com.javastudio.tutorial;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Security;

public class Client {
    public static void main(String[] args) {
        Security.setProperty("jdk.tls.disabledAlgorithms", "");
        Security.setProperty("force.http.jre.executor", "true");
        System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        String host = "localhost";
        if (args != null && args.length > 0)
            host = args[0];

        int port = 12400;
        if (args != null && args.length > 1)
            port = Integer.parseInt(args[1]);

        Socket socket = null;

        try {
            System.out.println("Connecting to " + host + " on port " + port);

            socket = SSLSocketFactory.getDefault().createSocket();
            socket.setKeepAlive(true);
            socket.setSoLinger(true, 0);
            socket.setReuseAddress(true);
            socket.setTcpNoDelay(true);
            // socket.bind(new InetSocketAddress(new InetSocketAddress(port).getAddress(), port));
            socket.connect(new InetSocketAddress(host, port));
            ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1.2"});
            ((SSLSocket) socket).startHandshake();

            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);
            socket.getInputStream();
            socket.getOutputStream();
            socket.setSoLinger(true, 0);

            System.out.println("Just connected to " + socket.getRemoteSocketAddress());
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + socket.getLocalSocketAddress());
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


