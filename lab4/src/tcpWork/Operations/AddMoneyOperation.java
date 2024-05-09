package tcpWork.Operations;

public class AddMoneyOperation extends CardOperation {
    private String serialNum;
    private double money = 0.0;

    public AddMoneyOperation(String serialNum, double money){
        this.serialNum = serialNum;
        this.money = money;
    }

    public AddMoneyOperation(){
        this("null", 0.0);
    }

    public String getSerialNumber(){
        return serialNum;
    }

    public void setSerialNumber(String serialNum){
        this.serialNum = serialNum;
    }

    public double getMoney(){
        return money;
    }

    public void setMoney(double money){
        this.money = money;
    }
}
