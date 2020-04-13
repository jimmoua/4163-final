package multiton;

import java.lang.Math;

import multiton.Store.GUI;
import multiton.Store.Location;
import multiton.Store.Store;

public class Customer extends Thread {
  private int purchased = 0;

  public Customer() {
  }

  private static synchronized void goShopping() {
    for(Location i : Location.values()) {
      // Randomly choose item quantity to buy.
      final int itemsToPurchase = (int) (Math.random() * 101 + 50);
      if(Store.getStore(i).getItemStock() >= itemsToPurchase) {
        Store.getStore(i).sellItems(itemsToPurchase);
        return;
      } else if(Store.getStore(i).getItemStock() < 0) {
        System.out.printf("Error: negative number reached.\n");
        System.exit(1);
      }
    }
  }

  public void run() {
    while(Main.getCustomerList().size() < Main.numCustomers);
    while(purchased < 10) {
      final int numGen = (int) (Math.random() * 5 + 1);
      if(numGen != 1) {
        goShopping();
        purchased++;
      }
      try {
        sleep(1500);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    GUI.getTField().setText(String.valueOf(Main.getCustomerList().size()-1));
    Main.getCustomerList().remove(this);
  }

}