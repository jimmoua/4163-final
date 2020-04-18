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
      // Randomly choose item quantity to buy.
      final int itemsToPurchase = (int) (Math.random() * 101 + 50); // [50, 150]
      if(Store.getStore(i).getItemStock() >= itemsToPurchase) {
        final int numGen = (int) (Math.random() * 5 + 1);
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