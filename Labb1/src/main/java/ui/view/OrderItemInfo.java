package ui.view;

/**
 * The OrderItemInfo class represents information about an item in an order,
 * including its item ID, number of items, and optional reference to the item details.
 */
public class OrderItemInfo {
    private final int itemId;
    private int nrOfItems;
    private BookInfo item;

    /**
     * Constructs an OrderItemInfo object with the specified item ID and number of items.
     *
     * @param itemId    the unique identifier of the item
     * @param nrOfItems the number of items in the order
     */
    public OrderItemInfo(int itemId, int nrOfItems) {
        this.itemId = itemId;
        this.nrOfItems = nrOfItems;
    }

    /**
     * Constructs an OrderItemInfo object with the specified BookInfo object, item ID, and number of items.
     *
     * @param item      the BookInfo object representing details of the item
     * @param itemId    the unique identifier of the item
     * @param nrOfItems the number of items in the order
     */
    public OrderItemInfo(BookInfo item, int itemId, int nrOfItems) {
        this(itemId, nrOfItems);
        this.item = item;
    }

    /**
     * Returns the item ID.
     *
     * @return the item ID
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Returns the number of items in the order.
     *
     * @return the number of items
     */
    public int getNrOfItems() {
        return nrOfItems;
    }

    /**
     * Returns the BookInfo object representing the item details.
     *
     * @return the BookInfo object, or null if not set
     */
    public BookInfo getItem() {
        return item;
    }

    /**
     * Sets the number of items in the order.
     *
     * @param nrOfItems the new number of items
     */
    public void setNrOfItems(int nrOfItems) {
        this.nrOfItems = nrOfItems;
    }

    /**
     * Returns a string representation of the OrderItemInfo object.
     *
     * @return a string representing the OrderItemInfo
     */
    @Override
    public String toString() {
        return "OrderItemInfo {" +
                "itemId='" + itemId + '\'' +
                ", nrOfItems='" + nrOfItems + '\'' +
                '}';
    }
}
