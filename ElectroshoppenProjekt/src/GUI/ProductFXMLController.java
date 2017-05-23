/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Facade.Facade;
import Facade.iFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author MER
 */
public class ProductFXMLController implements Initializable {
    iFacade facade;
    private long productId;
    
    @FXML
    private ImageView productIV;
    @FXML
    private TextArea productTA;
    @FXML
    private Button addBTN;
    @FXML
    private Button gobackBTN;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField amountTF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.facade = new Facade();

    }
    
    
    
    @FXML
    private void addToBasket(ActionEvent event) {
        if (this.amountTF == null) {
            this.facade.addToOrder(this.facade.searchProduct(productId), 1);
        } else {
            this.facade.addToOrder(this.facade.searchProduct(productId), Integer.parseInt(this.amountTF.getText()));
        }
        
    }
    
    public void goBackToProducts (ActionEvent event) {
        AnchorPane pane;
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("webshopFXML.fxml"));
            pane = (AnchorPane) fxmlLoader1.load();
            this.mainPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(webshopFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setProductId(long id) {
        this.productId = id;
        String [] s = facade.searchProduct(productId).toString().split(";");
        
        for (int i = 0; i < s.length -1; i++) {
            this.productTA.appendText(s[i]);
        }
    }
    
    }
