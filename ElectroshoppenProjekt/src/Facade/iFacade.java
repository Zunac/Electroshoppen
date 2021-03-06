/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Authentication.Token;
import WEBSHOP.Product.Product;
import WEBSHOP.Product.ProductCategory;
import java.util.ArrayList;
import WEBSHOP.Address;
import WEBSHOP.Profiles.OrderHistory;
import java.util.HashMap;

/**
 * Interface to link the domain layer of all three packages, PIM, POS, and 
 * WEBSHOP, to the GUI.
 * @author rune_
 */
public interface iFacade {
    String getToken();
    
    long getProductNumber();
    
    double getPiecePrice();
    
    String getProductName();
    
    ProductCategory getProductCategory();
    
    Product searchProduct(long prdNum);
    
    ArrayList<Product> searchProductsFromText(String s);
    
    ArrayList<Product> getProductsFromCat(ProductCategory prdCat);   
    
    Long getOrderNumber();
    
    String getStatus();
    
    void setNewCustomer();
    
    double getTotalPrice();
    
    int getProductAmount();
    
    void addToOrder(Product p, int amount);
    void removeFromOrder(Product p, int amount);
    String showBasket();
    double getSubTotal();
    
  
    
    //Webshop
    void setLoginForCustomer(Token token);
    void updateProfile(String name, String email, String phone, String cvr);
    String[] searchProfile(String email);
    String pay();
    void addToViewedProducts(Product p);
    String[] getAddressArray(String email);
    Address getAddress();
    void setAddress(String streetName, String streetNumber, String secAddress, String zipCode,
            String city);
    
    boolean isValid();
    void setCustomerEmail(String newEmail);
    
    //PIM
    void editName(long id, String newName);
    void editPrice(long id, double newPrice);
    void editDescription(long id, String newDescription);
    void editCategory(long id, String newCategory);
    void newProduct(String name, long number, double price, String description, ProductCategory category);
    boolean addProductToDatabase();
    void removeProduct(Product p);
    
    //POS
    String[] getCustomerInfo(String email);
    OrderHistory getOrderHistory(String email);
    HashMap<String, ArrayList<String>> getOrderLinesByOrder(String email);
    int getAmountForOrderLine(long orderNumber);
    void editAmountForOrderLine(long orderNumber, long productId, int amount);
    
}
