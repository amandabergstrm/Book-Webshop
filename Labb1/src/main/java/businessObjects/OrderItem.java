package businessObjects;

public class OrderItem {

    private final String itemId;

    private final int nrOfItems;

    private OrderItem (String itemId, int nrOfItems){
        this.itemId = itemId;
        this.nrOfItems = nrOfItems;
    }
}
