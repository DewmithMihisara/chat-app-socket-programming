package lk.ijse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String message;
    {
        message="";
    }
    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            while(true) {
                message=dataInputStream.readUTF();
                if (message.startsWith("/usrLog//!-> ")){
                    String[] usr=message.split("/usrLog//!-> ");
                    System.out.println(usr[1]+" Log into chat!");
                    System.out.println(threadList.size());
                } else if (message.startsWith("/usrLogOut//!-> ")) {
                    String[]usr=message.split("/usrLogOut//!-> ");
                    System.out.println(usr[1] + " Log Out in Chat!");
//                    threadList.remove(this);
                } else {
                    for (ServerThread serverThread:threadList){
                        serverThread.dataOutputStream.writeUTF(message);
                        serverThread.socket.getOutputStream().flush();
                    }
                }

            }
        } catch (Exception e) {

            System.out.println("Error occured " +e.getStackTrace());
        }finally {
            System.out.println("server thread");
        }
    }
}
