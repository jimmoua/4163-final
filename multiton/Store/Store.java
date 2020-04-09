package multiton.Store;

import java.util.HashMap;

import multiton.Main;

public class Store extends Thread {

  // Static mapping
  private static HashMap<Location, Store> StoreMap = new HashMap<Location, Store>();

  // Store information
  private int items = 5000;
  private Location key; // Location of the store
  private int sales = 0;


  private synchronized void restockShelf() {
    items+=5000;
    if(items > 20000) items = 20000;
  }

  final public int getItemStock() {
    return items;
  }

  public synchronized void sellItems(final int itemCount) {
    items-=itemCount;
    sales++;
  }

  public static synchronized Store getStore(final Location key) {
    if(StoreMap.get(key) == null) {
      StoreMap.put(key, new Store(key));
    }
    return StoreMap.get(key);
  }

  private Store(final Location key) {
    System.out.printf("A store in %s has been built!\n", key.toString());
    this.key = key;
    start();
  }

  public final int getSales() {
    return sales;
  }

  public void run() {
    long restockTimer = System.currentTimeMillis();
    int customersLeft = Main.customerList.size();
    while(!Main.customerList.isEmpty()) {
      final long nowTimer = System.currentTimeMillis();
      if(nowTimer-restockTimer >= 3000L) {
        restockShelf();
        System.out.printf("Restocking store location at %s...\n", key.toString());
        restockTimer = System.currentTimeMillis();
      }
      customersLeft = Main.customerList.size();
    }
  }
}