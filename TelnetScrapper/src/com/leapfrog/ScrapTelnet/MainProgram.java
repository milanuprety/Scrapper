/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.ScrapTelnet;

import com.leapfrog.ScrapTelnet.handler.ClientListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author milan
 */
public class MainProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 int port = 9000;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is running at port : " + port);
            
            while (true) {
                Socket client = server.accept();
                
                ClientListener listener = new ClientListener(client);
                listener.run();
                System.out.println("Incoming connection request from" + client.getInetAddress().getHostName() + " with ip: " + client.getInetAddress().getHostAddress());
             
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}