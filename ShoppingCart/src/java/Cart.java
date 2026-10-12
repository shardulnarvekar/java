/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Shardul
 */
public class Cart {
    private String pname;
    private int quantity;
    private int rate;

    public Cart(String pname, int quantity, int rate) {
        this.pname = pname;
        this.quantity = quantity;
        this.rate = rate;
    }

    public String getPname() {
        return pname;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRate() {
        return rate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

