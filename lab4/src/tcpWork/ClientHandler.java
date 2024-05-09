package tcpWork;

import tcpWork.Operations.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private boolean work = true;
    private MetroCardBank bnk = null;
    private Socket s = null;
    private Thread t;

    public ClientHandler(MetroCardBank bnk, Socket s) {
        this.bnk = bnk;
        this.s = s;
        this.work = true;
        try{
            this.is = new ObjectInputStream(s.getInputStream());
            this.os = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        t = new Thread(this);
    }

    @Override
    public void run(){
        synchronized (bnk) {
            System.out.println("Client Handler Started for: " + getSocketInfo());
            while (work){
                Object obj;
                try {
                    obj = is.readObject();
                    processOperation(obj);
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Error: " + e);
                }
            }
            try {
                System.out.println("Client Handler Stopped for: " + s);
                s.close();
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
    }

    public String getSocketInfo(){
        return  s.getInetAddress().getCanonicalHostName() + " on the port " + s.getPort();
    }

    private void processOperation(Object obj) throws IOException, ClassNotFoundException {
        if (obj instanceof StopOperation) {
            finish();
        } else if (obj instanceof AddMetroCardOperation) {
            addCard(obj);
        } else if (obj instanceof AddMoneyOperation) {
            addMoney(obj);
        } else if (obj instanceof PayMoneyOperation) {
            payMoney(obj);
        } else if (obj instanceof RemoveCardOperation) {
            removeCard(obj);
        } else if (obj instanceof ShowBalanceOperation) {
            showBalance(obj);
        } else if (obj instanceof GetCardInfoOperation) {
            getCardInfo(obj);
        } else {
            error();
        }

    }

    private void finish() throws IOException {
        work = false;
        os.writeObject("Finish Work " + getSocketInfo());
        os.flush();
    }

    private void addCard(Object obj) throws IOException, ClassNotFoundException{
        AddMetroCardOperation addMetroCardOperation = (AddMetroCardOperation) obj;
        bnk.addCard(addMetroCardOperation.getMetroCard());
        os.writeObject("Card Added");
        os.flush();
    }

    private void addMoney(Object obj) throws IOException {
        AddMoneyOperation op = (AddMoneyOperation) obj;
        boolean res = bnk.addMoney(op.getSerialNumber(), op.getMoney());
        if (res) {
            os.writeObject("Balance Added");
        } else {
            os.writeObject("Cannot Balance Added");
        }
        os.flush();
    }

    private void payMoney(Object obj) throws IOException {
        PayMoneyOperation op = (PayMoneyOperation) obj;
        boolean res = bnk.getMoney(op.getSerialNumber(), op.getMoney());
        if (res) {
            os.writeObject("Money Payed");
            os.flush();
        } else {
            os.writeObject("Cannot Pay Money");
            os.flush();
        }
        os.flush();
    }

    private void removeCard(Object obj) throws IOException {
        RemoveCardOperation op = (RemoveCardOperation) obj;
        boolean res = bnk.removeCard(op.getSerialNumber());
        if (res) {
            os.writeObject("Metro Card Successfully Remove: " + op.getSerialNumber());
        } else {
            os.writeObject("Cannot Remove Card" + op.getSerialNumber());
        }
        os.flush();
    }

    private void showBalance(Object obj) throws IOException {
        ShowBalanceOperation op = (ShowBalanceOperation) obj;
        double balance = bnk.getBalance(op.getSerialNumber());
        if (balance == -1){
            os.writeObject("Cannot Show Balance for Card: " + op.getSerialNumber());
        } else {
            os.writeObject("Card : " + op.getSerialNumber() + " balance: "
                    + balance);
        }
        os.flush();
    }

    private void error() throws IOException {
        os.writeObject("Bad Operation");
        os.flush();
    }

    private void getCardInfo(Object obj) throws IOException {
        GetCardInfoOperation cio = (GetCardInfoOperation) obj;
        String info = bnk.getCardInfo(cio.getSerialNumber());
        if (info == null)
            os.writeObject("There is no card with this number");
        else
            os.writeObject(info);
        os.flush();
    }

    public void startService() {
        t.start();
    }
}