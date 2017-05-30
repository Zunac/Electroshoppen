/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elecetroshoppenprojekt;

import Authentication.Token;
import DBManager.DBConnection;
import WEBSHOP.Product.Product;
import WEBSHOP.Product.ProductCategory;
import WEBSHOP.Address;
import WEBSHOP.Order.OrderLine;
import WEBSHOP.Profiles.CustomerProfile;
import WEBSHOP.Profiles.Profile;
import WEBSHOP.iWebshopLogin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kasper
 */
public class Webshop implements iWebshopLogin {

    private CustomerProfile customer;
    private String customerUserName;
    private OrderLine orderLine;

    public Webshop() {
	this.customer = new CustomerProfile();

    }

    //MARK: Login stuff
    
    /**
     * THIS IS WHERE THE PROBLEM IS!!!
     * @param token 
     */
    @Override
    public void setLoginForCustomer(Token token) {
	this.customer.setToken(token);
	
	this.customer.getOrder().setEmail(this.customer.geteMail());
	System.out.println(this.customer.geteMail());
	String query = "SELECT p.product_id, p.name, p.category, p.description, p.price, p.amount \n"
		+ "FROM product p INNER JOIN order_line ol ON p.product_id = ol.product_id\n"
		+ "INNER JOIN orders o ON ol.order_number = o.order_number AND o.email = '" 
		+ this.customer.geteMail() + "';";
	DBConnection dbc = new DBConnection();
	try (ResultSet select = dbc.runQueryExcecute(query);) {
	    while (select.next()) {
		String name = select.getString("name");
		String category = select.getString("category");
		int amount = select.getInt("amount");
		String description = select.getString("description");
		double price = select.getDouble("price");
		Long productId = select.getLong("product_id");
		System.out.println("amount " + amount);
		OrderLine n = new OrderLine(name, productId, price, description, category);
		this.customer.getOrder().addOrderLine(n.getExistingProduct(), amount);

	    }
	} catch (SQLException ex) {
	    Logger.getLogger(Webshop.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public String pay() {
	return this.customer.getOrder().pay();
    }

    //MARK: Customer methods
    public void addToViewedProducts(Product p) {
	this.customer.addToViewedProducts(p);
    }

    public void addToOrder(Product p, int amount) {

	if ("Expired".equals(this.customer.getToken())) {
	    this.customer.getOrder().resetOrder();
	}

	this.customer.getOrder().addOrderLine(p, amount);
    }

    public void removeFromOrder(Product p, int amount) {
	if (this.customer.getToken().equals("Expired")) {
	    this.customer.getOrder().resetOrder();
	}

	this.customer.getOrder().removeOrderLine(p, amount);
    }

    public String showBasket() {
	if (this.customer.getToken().equals("Expired")) {
	    this.customer.getOrder().resetOrder();
	}

	return this.customer.getOrder().showBasket();
    }

    public void updateProfile(String name, String email, String phone, String cvr) {
	this.customer.updateProfile(name, email, phone, cvr);
    }

    public String[] searchProfile(String email) {
	return this.customer.searchProfile(email);
    }

    public String[] getAddressArray(String email) {
	return this.customer.getAddressArray(email);
    }

    public Address getAddress() {
	return this.customer.getAddress();
    }

    public void setAddress(String streetName, String streetNumber, String secAddress, String zipCode,
	    String city) {
	this.customer.setAddress(streetName, streetNumber, secAddress, zipCode, city);
    }

    public boolean isValid() {
	return this.customer.isValid();
    }

    public void setNewCustomer() {
	this.customer.getOrder().resetOrder();
	this.customer = new CustomerProfile();
    }
    
    public Profile getCustomerProfile(){
	return this.customer;
    }

    public static void main(String[] args) {
	Webshop webshop = new Webshop();

	webshop.addToOrder(new Product("testProdukt1", 12, 1500, "Dette er et test produkt nummer 1.", ProductCategory.COMPUTER), 1);
	webshop.addToOrder(new Product("testProdukt2", 13, 800, "Dette er et test produkt nummer 2.", ProductCategory.COMPUTER), 3);

	webshop.removeFromOrder(new Product("testProdukt2", 13, 800, "Dette er et test produkt nummer 2.", ProductCategory.COMPUTER), 1);

    }
}
