/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;

import DBManager.*;
import WEBSHOP.iWebshopLogin;
import elecetroshoppenprojekt.Webshop;

/**
 * Class responsible for logging users in and out.
 * @author Kasper
 */
public class Login extends Authentication {

    private Token token;
    private iWebshopLogin webshop;
    private boolean isLoggedIn;

    public Login(String userName, String password) {
	super(userName, password);
        webshop = new Webshop();

    }

    /**
     * If the user exists, the method creates and saves a Token to the databse
     * confirming a succesfull login and returns true.
     * Else it returns false.
     * @return
     */
    @Override
    public boolean doLogin() {
	if (super.userExists()) {

	    this.token = new Token(super.email.toUpperCase());
	    DBConnection dbc = new DBConnection();
	    String tokenTok = token.getTok();

	    String query = "do $$\n"
		    + "BEGIN\n"
		    + "IF EXISTS (SELECT * FROM token WHERE tok = '" + tokenTok + "') THEN \n"
		    + "UPDATE token SET millisec = " + token.getCreation() + " WHERE tok = '" + tokenTok + "';\n"
		    + "ELSE \n"
		    + "INSERT INTO token (tok, millisec, email) VALUES ('" + tokenTok + "', " 
		    + token.getCreation() + ", '" + super.email + "');\n"
		    + "END IF;\n"
		    + "END \n"
		    + "$$";
	    dbc.runQueryUpdate(query);
            this.webshop.setLoginForCustomer(this.token);
	    isLoggedIn = true;
	    return true;
	} else {
	    return false;
	}
    }
    
    @Override
    public boolean doLogout(){
	if (super.userExists() && this.token != null) {	    
	    DBConnection dbc = new DBConnection();
	    String query = "DELETE FROM token WHERE tok = '" + token.getTok() + "';";
	    dbc.runQueryUpdate(query);
	    System.out.println("ldkf");
            this.token = null;
	    return true;
	} else {
	    return false;
	}
    }
    
    
    
    /**
     * Do not use this method.
     * Meant for Create class.
     * @param none
     * @return false
     */
    @Override
    public boolean createUser(String none) {
        return false;
    }
    
    /**
     * Do not use this method.
     * Meant for Create class.
     * @param none
     * @return false
     */
    @Override
    public boolean deleteUser(String none) {
        return false;
    }
}
