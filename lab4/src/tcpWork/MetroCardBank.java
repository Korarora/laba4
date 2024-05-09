package tcpWork;

import java.util.HashMap;

public class MetroCardBank {
    private HashMap <String, MetroCard> cardMap;

    public MetroCardBank(){
        cardMap = new HashMap<>();
    }

    public boolean findMetroCard(String serialNumber){
        return cardMap.containsKey(serialNumber);
    }

    public int getCardNumber(){
        return cardMap.size();
    }

    public void addCard(MetroCard card){
        if (findMetroCard(card.getSerialNumber())) {
            System.out.println("This card is already registered");
        } else {
            cardMap.put(card.getSerialNumber(), card);
        }
    }

    public boolean removeCard(String serialNumber){
        if (findMetroCard(serialNumber)) {
            cardMap.remove(serialNumber);
            return true;
        } else
            return false;
    }

    public boolean addMoney(String serialNumber, double money){
        if (findMetroCard(serialNumber)) {
            cardMap.get(serialNumber).addMoney(money);
            return true;
        } else
            return false;
    }

    public boolean getMoney(String serialNumber, double money){
        boolean isPayed;
        if (findMetroCard(serialNumber)){
            double currentMoney = cardMap.get(serialNumber).getBalance();
            if (currentMoney >= money) {
                cardMap.get(serialNumber).addMoney(currentMoney - money);
                isPayed = true;
            } else {
                isPayed = false;
            }

        } else {
            isPayed = false;
        }
        return isPayed;
    }

    public double getBalance(String serialNumber){
        if (findMetroCard(serialNumber)){
            return cardMap.get(serialNumber).getBalance();
        } else
            return -1;
    }

    public String getCardInfo(String serialNumber){
        if (findMetroCard(serialNumber)){
            return cardMap.get(serialNumber).toString();
        } else
            return null;
    }

    public String toString(){
        StringBuilder stringBuilder;
        if (cardMap.size() == 0)
            stringBuilder = new StringBuilder("The list of cards is empty");
        else {
            stringBuilder = new StringBuilder("The list of cards: ");

            for (String keys : cardMap.keySet())
                stringBuilder.append("\n\n").append(cardMap.get(keys));
        }

        return stringBuilder.toString();
    }
}