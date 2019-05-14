/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si_toko;

import java.text.DecimalFormat;

/**
 *
 * @author REY
 */
public class NewClass {
    public static void main(String[] args) {
    String amount = "3,000,000";
    int harga = Integer.parseInt(amount);
    DecimalFormat df = new DecimalFormat("#");
    String value = df.format(harga);
    int amounts = Integer.parseInt(value);
    System.out.println(amounts);   
    }
}
