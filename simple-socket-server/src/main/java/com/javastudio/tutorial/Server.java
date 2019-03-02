package com.javastudio.tutorial;

import com.javastudio.tutorial.server.AsyncSocket;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        int port = 12400;

        if (args != null && args.length > 0)
            port = Integer.parseInt(args[0]);

        try {
            Thread t = new AsyncSocket(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
