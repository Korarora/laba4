package tcpWork;

import tcpWork.Operations.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
    private int port = -1;
    private String server = null;
    private Socket socket = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;

    public Client(String server, int port){
        this.port = port;
        this.server = server;
        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            System.out.println("You successfully connected with the server");
        } catch (SocketTimeoutException e){
            System.out.println("Waiting time is out");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error of connecting to the port");
            System.exit(0);
        }
    }

    public void finish() {
        try {
            os.writeObject(new StopOperation());
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void applyOperation(CardOperation operation){
        try {
            os.writeObject(operation);
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The server is not responding");
            System.exit(0);
        }
    }

    private void printMenu(){
        System.out.println("================= Menu ===================");
        System.out.println("|| 1) Get info about a card            ||");
        System.out.println("|| 2) Top up account                   ||");
        System.out.println("|| 3) Registration of a new card       ||");
        System.out.println("|| 4) Find out the balance on the card ||");
        System.out.println("|| 5) Pay for the fare                 ||");
        System.out.println("|| 6) Delete the card                  ||");
        System.out.println("|| 7) Exit                             ||");
        System.out.println("==========================================");
        System.out.print("  Your choice:\n>");
    }

    public void startInteraction(){
        Scanner in = new Scanner(System.in);
        String serialNumber;
        CardOperation cardOperation = null;
        int choice = -1;
        while (choice != 7){
            printMenu();
            choice = in.nextInt();
            switch (choice){
                case 1:
                    serialNumber = readCardSerialNumber(in);
                    cardOperation = new GetCardInfoOperation(serialNumber);
                    break;
                case 2:
                    serialNumber = readCardSerialNumber(in);
                    double money = readMoney(in);
                    cardOperation = new AddMoneyOperation(serialNumber, money);
                    break;
                case 3:
                    MetroCard metroCard = readCard(in);
                    cardOperation = new AddMetroCardOperation(metroCard);
                    break;
                case 4:
                    serialNumber = readCardSerialNumber(in);
                    cardOperation = new ShowBalanceOperation(serialNumber);
                    break;
                case 5:
                    serialNumber = readCardSerialNumber(in);
                    cardOperation = new PayMoneyOperation(serialNumber, 6);
                    break;
                case 6:
                    serialNumber = readCardSerialNumber(in);
                    cardOperation = new RemoveCardOperation(serialNumber);
                    break;
                case 7:
                    cardOperation = new StopOperation();
                    in.close();
                    break;
                default:
                    System.out.println("Wrong choice. Try again");
                    cardOperation = null;
                    break;
            }
            if (cardOperation != null)
                applyOperation(cardOperation);
        }
    }

    private String readCardSerialNumber(Scanner in){
        System.out.print("Enter number of the card: ");
        return in.next();
    }

    private double readMoney(Scanner in){
        System.out.print("Enter the amount of paying: ");
        return in.nextInt();
    }

    private User readUser(Scanner in){
        System.out.print("Enter your name: ");
        String name = in.next();

        System.out.print("Enter your surname: ");
        String surname = in.next();

        System.out.print("Enter your sex: ");
        String sex = in.next();

        System.out.print("Enter your date of birth(dd.mm.yyyy): ");
        String birthday = in.next();

        return new User(name, surname, sex, birthday);
    }

    private MetroCard readCard(Scanner in){
        User user = readUser(in);
        System.out.print("Enter your colledge: ");
        String university = in.next();
        String serialNumber = readCardSerialNumber(in);

        return new MetroCard(serialNumber, user, university);
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 7891);
        client.startInteraction();
    }
}