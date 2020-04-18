package multiton;

import java.lang.Math;

import multiton.Store.GUI;
import multiton.Store.Location;
import multiton.Store.Store;

public class Customer {
  private int purchased = 0;
  public static int ttlPurchased = 0;

  public Customer() {
  }

  public void goShopping() {
    for(Location i : Location.values()) {
      // Randomly choose item quantity to buy.
      final int itemsToPurchase = (int) (Math.random() * 101 + 50);
      // final int itemsToPurchase = 1;
      if(Store.getStore(i).getItemStock() >= itemsToPurchase) {
        final int numGen = (int) (Math.random() * 5 + 1);
        // Purchase something if numGen != 1
        if(numGen != 1) {
          purchased++;
          ttlPurchased++;
          Store.getStore(i).sellItems(itemsToPurchase);
          if(purchased >= 1) {
            GUI.getTField().setText(String.valueOf(Main.customerList.size()-1));
            Main.customerList.remove(this);
          }
        }
        return;
      }
    }
  }

}