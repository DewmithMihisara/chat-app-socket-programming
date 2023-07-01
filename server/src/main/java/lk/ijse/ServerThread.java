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
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
            }
        } catch (Exception e) {

            System.out.println("Error occured " +e.getStackTrace());
        }finally {
            System.out.println("server thread");
        }
    }
}
