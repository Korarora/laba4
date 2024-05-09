package tcpWork;

import java.io.Serializable;

public class MetroCard implements Serializable {
    private String serialNumber;
    private User user;
    private String university;
    private double balance;

    public MetroCard(String serialNumber, User user, String university){
        this.serialNumber = serialNumber;
        this.user = user;
        this.university = university;
    }

    public MetroCard(){
        this("null", new User(), "null");
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public double getBalance() {
        return balance;
    }

    public void addMoney(double balance) {
        this.balance += balance;
    }

    public String toString(){
        return "№: " + serialNumber + "\nUser:\n" +
                user + "\n" +
                "\nColledge: " + university + "\n" +
                "\nBalance: " + balance;
    }

    public static void main(String[] args) {
        MetroCard metroCard = new MetroCard();
        System.out.println(metroCard);
    }
}