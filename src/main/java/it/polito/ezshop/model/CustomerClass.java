package it.polito.ezshop.model;
import it.polito.ezshop.data.Customer;


public class CustomerClass implements Customer {
    private String customerName;
    private String customerCard;
    private Integer id;
    private Integer points;


    public CustomerClass() {
    }

    public CustomerClass(String customerCard, Integer id) {
        this.customerCard = customerCard;
        this.id = id;
    }

    public CustomerClass(String customerName, String customerCard, Integer id, Integer points) {
        this.customerName = customerName;
        this.customerCard = customerCard;
        this.id = id;
        this.points = points;
    }

    public CustomerClass(String customerName, String customerCard, Integer points) {
        this.customerName = customerName;
        this.customerCard = customerCard;
        this.points = points;
    }

    public CustomerClass(String customerName) {
        this.customerName = customerName;

    }

    @Override
    public String getCustomerName() { return this.customerName; }

    @Override
    public void setCustomerName(String customerName) {this.customerName=customerName;  }

    @Override
    public String getCustomerCard() { return this.customerCard; }

    @Override
    public void setCustomerCard(String customerCard) { this.customerCard=customerCard; }

    @Override
    public Integer getId() { return this.id; }

    @Override
    public void setId(Integer id) { this.id=id; }

    @Override
    public Integer getPoints() { return this.points; }

    @Override
    public void setPoints(Integer points) { this.points=points; }
}
