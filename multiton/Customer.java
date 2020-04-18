package multiton;

import java.lang.Math;

import multiton.Store.GUI;
import multiton.Store.Location;
import multiton.Store.Store;

public class Customer {

  public Customer() {
  }

  public void goShopping() {
    for(Location i : Location.values()) {
      // Generate item quantity [50, 150] to buy.
      final int itemsToPurchase = (int) (Math.random() * 101 + 50);
      if(Store.getStore(i).getItemStock() >= itemsToPurchase) {
        // Generate number [1, 5]
        final int numGen = (int) (Math.random() * 5 + 1);
        // If number isn't 1, purchase the amount specified above.
        if(numGen != 1) {
          Store.getStore(i).sellItems(itemsToPurchase);
          GUI.getTField().setText(String.valueOf(Main.customerList.size()-1));
          Main.customerList.remove(this);
        }
        return;
      }
    }
  }

}