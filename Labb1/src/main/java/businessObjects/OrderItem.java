package businessObjects;

public class OrderItem {

    private final int itemId;

    private int nrOfItems;

    public OrderItem(int itemId, int nrOfItems){
        this.itemId = itemId;
        this.nrOfItems = nrOfItems;
    }

    public int getItemId() {
        return itemId;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public void setNrOfItems(int nrOfItems) {
        this.nrOfItems = nrOfItems;
    }
}
