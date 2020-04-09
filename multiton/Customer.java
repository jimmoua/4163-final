package multiton;

import java.lang.Math;
import multiton.Store.*;

public class Customer extends Thread {
  private static int CustomerIDCount = 0;
  private int customerID;
  private int purchased = 0;

  public Customer() {
    customerID = ++CustomerIDCount;
    start();
  }

  private void goShopping() {
    // Randomly choose item quantity to buy.
    final int itemsToPurchase = (int) (Math.random() * 101 + 50);
    boolean transactionComplete = false;
    while (!transactionComplete) {
      for (Location i : Location.values()) {
        if (Store.getStore(i).getItemStock() >= itemsToPurchase) {
          System.out.printf("Customer %d purchased %d unites.\n", customerID, itemsToPurchase);
          Store.getStore(i).sellItems(itemsToPurchase);
          transactionComplete = true;
          break;
        }
      }
    }
  }

  public void run() {
    while (purchased < 5) {
      final int numGen = (int) (Math.random() * 5 + 1);
      if (numGen != 1) {
        goShopping();
        purchased++;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    Main.customerList.remove(this);
    System.out.printf("Customer %d has left the store.\n", customerID);
    System.out.printf("There are %d customers left.\n", Main.customerList.size());
  }

}