package tcpWork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MetroServer extends Thread {
    private String appender = "|Повідомлення сервера| ";
    MetroCardBank bnk = null;
    private ServerSocket serverSock = null;
    private int serverPort = -1;

    public MetroServer(int serverPort){
        this.bnk = new MetroCardBank();
        this.serverPort = serverPort;
    }

    @Override
    public void run(){
        try{
            this.serverSock = new ServerSocket(serverPort);
            System.out.println(appender + "Metro Server started");
            while (true){
                System.out.println(appender + "New Client Waiting...");
                Socket socket = serverSock.accept();
                System.out.println(appender + "New client: " + socket.getInetAddress().getCanonicalHostName() + " on the port " + socket.getPort());
                ClientHandler clientHandler = new ClientHandler(bnk, socket);
                clientHandler.startService();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSock.close();
                System.out.println(appender + "Metro Server stopped");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MetroServer metroServer = new MetroServer(7891);
        metroServer.start();
    }
}