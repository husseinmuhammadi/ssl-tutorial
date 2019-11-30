package com.javastudio.tutorial;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;

public class Server {
    public static void main(String[] args) {
        int port = 12400;

        if (args != null && args.length > 0)
            port = Integer.parseInt(args[0]);

        try {

            System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

            File f = new File("keystore.jks");
            String keyAndTrustStoreClasspathPath = f.getAbsolutePath();
            System.out.println(keyAndTrustStoreClasspathPath);

            File keyStroreFile = new File(keyAndTrustStoreClasspathPath);
            System.out.println(keyStroreFile.getAbsolutePath());
            InputStream keystoreStream = new FileInputStream(keyStroreFile);//NanoHTTPD.class.getResourceAsStream(keyAndTrustStoreClasspathPath);

            char[] passphrase = "123456".toCharArray();
            keystore.load(keystoreStream, passphrase);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystore, passphrase);


            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keystore);
            SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            SSLServerSocketFactory sslServerSocketFactory = ctx.getServerSocketFactory();

            String[] sslProtocols = null;

            SSLServerSocket serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
            serverSocket.setSoTimeout(300000);

            if (sslProtocols != null) {
                serverSocket.setEnabledProtocols(sslProtocols);
            } else {
                serverSocket.setEnabledProtocols(serverSocket.getSupportedProtocols());
            }
            serverSocket.setUseClientMode(false);
            serverSocket.setWantClientAuth(false);
            serverSocket.setNeedClientAuth(false);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("Thank you for connecting to " + socket.getLocalSocketAddress()
                        + "\nGoodbye!");
                socket.close();
            }

        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
