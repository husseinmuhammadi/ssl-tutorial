package com.javastudio.tutorial;

import com.javastudio.tutorial.ssl.WebInterfaceSSL;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        int port = 12400;

        if (args != null && args.length > 0)
            port = Integer.parseInt(args[0]);

        Thread t = new Thread(new WebInterfaceSSL(port));
        t.start();
    }
}
