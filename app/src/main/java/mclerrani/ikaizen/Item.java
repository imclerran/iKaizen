package mclerrani.ikaizen;

/**
 * Created by Ian on 1/12/2016.
 */
public class Item {
    private String name;
    private int quantity;
    private int cost;

    public Item() {
    }

    public Item(String name, int quantity, int cost) {
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Item getTestItem() {
        Item tmp = new Item("item name", 1, 5);
        return tmp;
    }
}
