package businessObjects;

/**
 * Represents an individual item in an order. This class contains information about the item,
 * including its unique item ID and the number of items ordered.
 */
public class OrderItem {
    private final int itemId;
    private int nrOfItems;

    /**
     * Constructs an {@link OrderItem} with the specified item ID and number of items.
     *
     * @param itemId    the unique ID of the item
     * @param nrOfItems the number of items ordered
     */
    public OrderItem(int itemId, int nrOfItems) {
        this.itemId = itemId;
        this.nrOfItems = nrOfItems;
    }

    /**
     * Gets the item ID of the order item.
     *
     * @return the unique item ID
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Gets the number of items ordered for this item.
     *
     * @return the number of items ordered
     */
    public int getNrOfItems() {
        return nrOfItems;
    }

    /**
     * Sets the number of items ordered for this item.
     *
     * @param nrOfItems the new number of items ordered
     */
    public void setNrOfItems(int nrOfItems) {
        this.nrOfItems = nrOfItems;
    }
}