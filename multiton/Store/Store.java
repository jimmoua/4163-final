package multiton.Store;

import java.util.HashMap;

import multiton.Main;

import java.lang.Math;

public class Store implements Runnable {

  private Store(final Location key) {
    System.out.printf("A store in %s has been built!\n", key.toString());
    maxStock = 50000;
    restockCount = (int)Math.ceil(maxStock*.35);
    items = restockCount;
    sales = 0;
    this.key = key;
    Thread storeThread = new Thread(this);
    storeThread.start();
  }

  // Static mapping
  private static HashMap<Location, Store> StoreMap = new HashMap<Location, Store>();

  // Store information
  private volatile int items;
  private int maxStock;
  private int restockCount;
  private Location key; // Location of the store
  private volatile int sales;

  private synchronized void restockShelf() {
    items+=restockCount;
    if(items > maxStock) {
      items = maxStock;
    }
    updateStockGUI();
  }


  private synchronized void updateStockGUI() {
    GUI.itemStockGUI.get(key).itemStoreStockTextField.setText(String.format("%d items in stock", items));
  }

  public synchronized int getItemStock() {
    return items;
  }

  public synchronized boolean sellItems(final int itemCount) {
    if(items < itemCount) return false;
    items-=itemCount;
    updateStockGUI();
    sales++;
    return true;
  }

  public static synchronized Store getStore(final Location key) {
    if(StoreMap.get(key) == null) {
      StoreMap.put(key, new Store(key));
    }
    return StoreMap.get(key);
  }

  @Override
  public void run() {
    long restockTimer = System.currentTimeMillis();
    while(Main.getNum() > 0) {
      final long nowTimer = System.currentTimeMillis();
      // Restock every second
      if(nowTimer-restockTimer >= 1000) {
        restockShelf();
        restockTimer = System.currentTimeMillis();
      }
    }
    System.out.printf("Store %s made %d sales.\n", key.toString(), sales);
  }
}