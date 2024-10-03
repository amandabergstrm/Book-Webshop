package businessObjects;

public class OrderItem {

    private final String itemId;

    private final int nrOfItems;

    public OrderItem(String itemId, int nrOfItems){
        this.itemId = itemId;
        this.nrOfItems = nrOfItems;
    }
}
