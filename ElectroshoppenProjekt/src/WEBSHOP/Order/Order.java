/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WEBSHOP.Order;
import ProductStuff.Product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Bruger
 */
public class Order {
    
    //Attributes
    private int orderNumber;
    private Status status;
    private double totalPrice;
    private ArrayList<OrderLine> orderlines;
    private LocalDateTime date;
    private OrderLine nextOrderLine;
    
    
    //Constructor
    public Order() {
        this.status = Status.STATUS1;        
        this.orderlines = new ArrayList();
    }
    
    
    //Add and remove methods
    /**
     * Use this method whether or not the product already is in this Order.
     * Creates a new OrderLine and checks if it exists in this Order based on productNumber.
     * It then adds the given amount to the OrderLine if it exists, or else it adds the new
     * OrderLine to its array.
     * @param p
     * @param amount 
     */
    public void addOrderLine(Product p, int amount) {
        this.nextOrderLine = new OrderLine(p, amount);
        
        if (!this.addToOrderLine(this.nextOrderLine.getProductNumber(), amount)) {
            this.orderlines.add(this.nextOrderLine);
        }
       
    }
    
    /**
     * Use this method to remove an amount or the orderline entirely.
     * @param p
     * @param amount 
     */
    public void removeOrderLine(Product p, int amount) {
        this.nextOrderLine = new OrderLine(p, amount);        
        this.removeFromOrderLine(this.nextOrderLine.getProductNumber(), amount);
    }
    
    /**
     * Helper method for addOrderLine.
     * @param productNumber
     * @param amount
     * @return 
     */
    private boolean addToOrderLine(long productNumber, int amount) {
        for (OrderLine o : this.orderlines) {
            if (o.getProductNumber() == productNumber) {
                o.addProductAmount(amount);
                return true;
            }
        }       
        return false;
    }
    
    /**
     * Helper method for removeOrderLine.
     * @param productNumber
     * @param amount
     * @return 
     */
    private boolean removeFromOrderLine(long productNumber, int amount){
        for (int i = 0; i < this.orderlines.size(); i++) {
            if (this.orderlines.get(i).getProductNumber() == productNumber) {  
                
                if (this.orderlines.get(i).removeProductAmount(amount)) {
                    
                    if (this.orderlines.get(i).getProductAmount() == 0) {
                        this.orderlines.remove(i);
                    }
                    
                    return true; //if the wished amount was removed or the orderline was removed entirely
                    
                } else {
                    return false; //if the wished amount removed would result in a negative value.
                }              
            }
        }
        
        return false; //if the orderline was never found on the order
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    public String getStatus() {
        return status.toString();
    }

    public double getTotalPrice() {
        
        for(OrderLine orderline: orderlines) {
            
            this.totalPrice += orderline.getSubTotal();
        }
        
        return totalPrice;
    }

    

       
    @Override 
    public String toString() {
        LocalDateTime time = LocalDateTime.now();
        String printTime = "" + time.getYear() + time.getMonth() + time.getDayOfMonth();
        
        this.orderNumber = 4; //Some sql to find the next ordernumber??
        
        return "Order: \t" + getOrderNumber() + "\t" + printTime + "\t" + getTotalPrice();
    }
}