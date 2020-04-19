package multiton;

import java.lang.Math;

import multiton.Store.GUI;
import multiton.Store.Location;
import multiton.Store.Store;

public class Customer implements Runnable {

  private int purchasedCount;
  public boolean shouldStopShopping() {
    return purchasedCount >= 5;
  }

  public Customer() {
    purchasedCount = 0;
    GUI.getTField().setText(String.valueOf(Main.customerList.size()));
    Thread customerThread = new Thread(this);
    customerThread.start();
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
          if(Store.getStore(i).sellItems(itemsToPurchase)) {
            purchasedCount++;
          }
        }
        return;
      }
      else if(Store.getStore(i).getItemStock() < 0) {
        System.out.println("Negative");
        System.exit(1);
      }
    }
  }

  @Override
  public void run() {
    while(!shouldStopShopping()) {
      goShopping();
      try {
        Thread.sleep(700);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    Main.customerList.remove(this);
    GUI.getTField().setText(String.valueOf(Main.customerList.size()));
  }

}