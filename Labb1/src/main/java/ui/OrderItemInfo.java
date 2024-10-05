package ui;


public class OrderItemInfo {

    private final int itemId;

    private final int nrOfItems;

    public OrderItemInfo(int itemId, int nrOfItems){
        this.itemId = itemId;
        this.nrOfItems = nrOfItems;
    }

    public int getItemId() {
        return itemId;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    @Override
    public String toString() {
        return "OrderItemInfo {" +
                "itemId'" + itemId + '\'' +
                ", nrOfItems='" + nrOfItems + '\'' +
                '}';
    }
}
