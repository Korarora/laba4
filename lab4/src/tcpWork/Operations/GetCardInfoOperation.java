package tcpWork.Operations;

public class GetCardInfoOperation extends  CardOperation {
    private String serialNumber;

    public GetCardInfoOperation(String serialNumber){
        this.serialNumber = serialNumber;
    }

    public GetCardInfoOperation(){
        this("null");
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
