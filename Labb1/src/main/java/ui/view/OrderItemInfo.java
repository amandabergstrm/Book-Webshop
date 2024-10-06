package ui.view;

public class OrderItemInfo {
    private final int itemId;
    private int nrOfItems;
    private BookInfo item;

    public OrderItemInfo(int itemId, int nrOfItems){
        this.itemId = itemId;
        this.nrOfItems = nrOfItems;
    }

    public OrderItemInfo(BookInfo item, int itemId, int nrOfItems) {
        this(itemId, nrOfItems);
        this.item = item;
    }

    public int getItemId() {
        return itemId;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public BookInfo getItem() {
        return item;
    }

    public void setNrOfItems(int nrOfItems) {
        this.nrOfItems = nrOfItems;
    }

    @Override
    public String toString() {
        return "OrderItemInfo {" +
                "itemId'" + itemId + '\'' +
                ", nrOfItems='" + nrOfItems + '\'' +
                '}';
    }
}