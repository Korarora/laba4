package tcpWork.Operations;

import tcpWork.MetroCard;

public class AddMetroCardOperation extends CardOperation {
    private MetroCard crd = null;

    public AddMetroCardOperation(){
        this(new MetroCard());
    }

    public AddMetroCardOperation(MetroCard crd){
        this.crd = crd;
    }

    public MetroCard getMetroCard(){
        return crd;
    }

    public void setMetroCard(MetroCard crd){
        this.crd = crd;
    }
}
