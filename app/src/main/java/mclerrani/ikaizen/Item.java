package mclerrani.ikaizen;

/**
 * Created by Ian on 1/12/2016.
 */
public class Item {
    private String type;
    private int quantity;
    private int cost;

    public Item() {
    }

    public Item(String type, int quantity, int cost) {
        this.type = type;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public static Item getTestItem() {
        Item tmp = new Item("item type", 1, 5);
        return tmp;
    }
}
