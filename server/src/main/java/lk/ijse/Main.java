package lk.ijse;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<>();
        ServerSocket serversocket;
        try {
            serversocket =new ServerSocket(3991);
            while(!serversocket.isClosed()) {
                Socket socket = serversocket.accept();
                ServerThread serverThread = new ServerThread(socket, threadList);
                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }finally {
            System.out.println("main");
        }
    }
}