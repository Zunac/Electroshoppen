/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;
import elecetroshoppenprojekt.*;
import WEBSHOP.Product.Product;
import WEBSHOP.Product.ProductCategory;
import Authentication.*;
import PIM.ProductEditing;
import PIM.ProductManager;
import POS.PointOfSale;
import WEBSHOP.*;
import WEBSHOP.Order.*;
import WEBSHOP.Profiles.*;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * Class holds instances of all the classes in the domain layer which methods
 * will be called during the event-driven run of the program.
 * Should only really hold one controller class for each package; PIM, POS
 * and WEBSHOP....
 * @author rune_
 */
public class Facade implements iFacade{
    private Product product;
    private ProductCategory productCategory;
    private Order order;
    private OrderLine orderLine;
    private OrderHistory orderHistory;
    private Webshop webshop;
    private ProductEditing pimEdit;
    private ProductManager pimMan;
    private PointOfSale pos;
    
    public Facade() {
        this.webshop = new Webshop();
         
        this.pimEdit = new ProductEditing();
        this.pimMan = new ProductManager();
	this.pos = new PointOfSale();
        this.orderHistory = new OrderHistory();
    }
    
    //Webshop
    @Override
    public void addToOrder(Product p, int amount) {
        this.webshop.addToOrder(p, amount);
    }
    @Override
    public void removeFromOrder(Product p, int amount) {
        this.webshop.removeFromOrder(p, amount);
    }
    @Override
    public String showBasket() {
        return this.webshop.showBasket();
    }
    
    

    @Override
    public void setLoginForCustomer(Token token) {
        this.webshop.setLoginForCustomer(token);
    }
    
    @Override
    public void updateProfile(String name, String email, String phone, String cvr) {
        this.webshop.updateProfile(name, email, phone, cvr);
    }
    
    @Override 
    public String[] searchProfile(String email) {
        return this.webshop.searchProfile(email);
    }
    
    @Override
    public String pay() {
        return this.webshop.pay();
    }
    
    @Override
    public void addToViewedProducts(Product p) {
        this.webshop.addToViewedProducts(p);
    }
    @Override
    public String[] getAddressArray(String email) {
        return this.webshop.getAddressArray(email);
    }
    @Override
    public Address getAddress(){
        return this.webshop.getAddress();
    }
    
    @Override
    public boolean isValid() {
        return this.webshop.isValid();
    }

    @Override
    public String getToken(){
	return this.webshop.getToken();
    }
    
    @Override
    public long getProductNumber(){
	return product.getProductNumber();
    }
    
    @Override
    public double getPiecePrice() {
        return product.getPiecePrice();
    }
        
    @Override
    public String getProductName() {
        return product.getProductName();
    }
    
    @Override
    public ProductCategory getProductCategory(){
        return product.getProductCategory();
    }
    
    @Override
    public Product searchProduct(long prdNum){
	return this.webshop.searchProduct(prdNum);
    }
    
    @Override
    public ArrayList<Product> searchProductsFromText (String in) {
        return this.webshop.searchProductsFromText(in);
    }
    
    
    @Override
    public ArrayList<Product> getProductsFromCat(ProductCategory prdCat){
	return this.webshop.getProductsFromCat(prdCat);
    }
    @Override
    public Long getOrderNumber(){
	return order.getId();
    }
    @Override
    public String getStatus(){
	return order.getStatus();
    }
    @Override
    public double getTotalPrice(){
	return order.getTotalPrice();
    }
    @Override
    public int getProductAmount(){
	return orderLine.getProductAmount();
    }
    @Override
    public double getSubTotal(){
	return orderLine.getSubTotal();
    }

    
    
    //PIM
    @Override
    public void editName(long id, String newName) {
        this.pimEdit.editName(id, newName);
    }

    @Override
    public void editPrice(long id, double newPrice) {
        this.pimEdit.editPrice(id, newPrice);
    }

    @Override
    public void editDescription(long id, String newDescription) {
        this.pimEdit.editDescription(id, newDescription);
    }

    @Override
    public void editCategory(long id, String newCategory) {
        this.pimEdit.editCategory(id, productCategory);
    }

    @Override
    public void newProduct(String name, long number, double price, String description, ProductCategory category) {
        this.pimMan.newProduct(name, number, price, description, category);
    }

    @Override
    public boolean addProductToDatabase() {
        return this.pimMan.addProductToDatabase();
    }

    @Override
    public String[] getCustomerInfo(String email) {
	return this.pos.getCustomerInfo(email);
    }

    @Override
    public OrderHistory getOrderHistory(String email) {
	
	return this.orderHistory;
    }

    @Override
    public void removeProduct(Product p) {
        this.pimMan.removeProduct(p);
    }

    @Override
    public void setAddress(String streetName, String streetNumber, String secAddress, String zipCode, String city) {
        this.webshop.setAddress(streetName, streetNumber, secAddress, zipCode, city);
    }

    
    @Override
    public HashMap<String, ArrayList<String>> getOrderLinesByOrder(String email) {
        return this.orderHistory.getOrderLinesByOrder(email);
    }

    @Override
    public int getAmountForOrderLine(long orderNumber) {
        return this.pos.getAmountForOrderLine(orderNumber);
    }

    @Override
    public void editAmountForOrderLine(long orderNumber, long productId, int amount) {
        this.pos.editAmountForOrderLine(orderNumber, productId, amount);
    }

    @Override
    public void setNewCustomer() {
	this.webshop.setNewCustomer();
    }

    @Override
    public void setCustomerEmail(String newEmail) {
	this.webshop.getCustomerProfile().setEmail(newEmail);
    }

}
