package lk.ijse;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    static ServerThread serverThread;
    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<>();
        ServerSocket serversocket;
        try {
            serversocket =new ServerSocket(4029);
            while(!serversocket.isClosed()) {
                Socket socket = serversocket.accept();
                serverThread = new ServerThread(socket, threadList);
                threadList.add(serverThread);
                serverThread.start();
                System.out.println("size "+ threadList.size());
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }finally {
            System.out.println("main");
        }
    }

}