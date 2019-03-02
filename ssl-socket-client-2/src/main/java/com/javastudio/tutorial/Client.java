package com.javastudio.tutorial;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        System.out.println("Hello");

        String serverName = "localhost";
        if (args != null && args.length > 0)
            serverName = args[0];

        int port = 12400;
        if (args != null && args.length > 1)
            port = Integer.parseInt(args[1]);

        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
