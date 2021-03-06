package org.academiadecodigo.bytenavoid.server;

import org.academiadecodigo.bytenavoid.client.ClientHandler;
import org.academiadecodigo.bytenavoid.facility.FacilityHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public class Server {

    private static final int PORT_NUM = 9090;

    public void init() {

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT_NUM);

        } catch (IOException e) {
            System.out.println("Port not working!");
            e.printStackTrace();

        }

        while (true) {

            waitForClient(serverSocket,socket);

        }
    }

    private void waitForClient(ServerSocket serverSocket, Socket socket) {
        try {
            System.out.println("Server ready...");
            socket = serverSocket.accept();

        } catch (IOException e) {
            System.out.println("Could not get Socket!");
            e.printStackTrace();
        }

        InitHandler initHandler = new InitHandler(socket);
        Thread thread = new Thread(initHandler);
        thread.start();

    }

    private class InitHandler implements Runnable {
        private Socket socket;

        public InitHandler(Socket socket) {
            this.socket = socket;
        }


        @Override
        public void run() {
            System.out.println("Init Handler");
            PrintWriter output = null;
            BufferedReader input = null;
            try {
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String anwser = "";

            output.println("(C)lient or (F)acility? ");
            try {
                anwser = input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (anwser) {
                case "C":
                    ClientHandler clientHandler = new ClientHandler(socket);
                    clientHandler.startClientAccess();
                    break;
/*

                case "E":
                    FacilityHandler facilityHandler = new FacilityHandler(socket);
                    facilityHandler.startFacilityAccess();
                    break;

                default:
                    errorMessage();

*/

            }
        }
    }
}
    /*  private void closeEverything(Socket socket) {

        for (Socket s: socketList) {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/



