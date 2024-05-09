package tcpWork.Operations;

public class ShowBalanceOperation extends CardOperation{
    private String serialNum;
    private double balance;

    public ShowBalanceOperation(){}

    public ShowBalanceOperation (String serialNum){
        this.serialNum = serialNum;
        this.balance = balance;
    }

    public String getSerialNumber(){
        return serialNum;
    }

    public void setSerialNumber(String serialNum){
        this.serialNum = serialNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
