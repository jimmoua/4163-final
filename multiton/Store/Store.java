package multiton.Store;

import java.util.HashMap;

import multiton.Main;

public class Store extends Thread {

  // Static mapping
  private static HashMap<Location, Store> StoreMap = new HashMap<Location, Store>();

  // Store information
  private int items = RESTOCK_COUNT;
  private final static int MAX_STOCK = 50000;
  private final static int RESTOCK_COUNT = 20000;
  private Location key; // Location of the store
  private int sales = 0;

  private void restockShelf() {
    StoreMap.get(key).items+=RESTOCK_COUNT;
    if(StoreMap.get(key).items > MAX_STOCK) StoreMap.get(key).items = MAX_STOCK;
    updateStock();
  }

  public int getItemStock() {
    return StoreMap.get(key).items;
  }

  public void sellItems(final int itemCount) {
    StoreMap.get(key).items-=itemCount;
    updateStock();
    StoreMap.get(key).sales++;
  }

  public static Store getStore(final Location key) {
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

  public int getSales() {
    return StoreMap.get(key).sales;
  }

  private void updateStock() {
    GUI.itemStockGUI.forEach((k, v) -> {
      if(k == key) {
        v.itemStoreStockTextField.setText(String.format("%d items in stock", items));
      }
    });
  }

  public void run() {
    long restockTimer = System.currentTimeMillis();
    while(!Main.customerList.isEmpty()) {
      final long nowTimer = System.currentTimeMillis();
      // Restock every three seconds
      if(nowTimer-restockTimer >= 1000) {
        restockShelf();
        restockTimer = System.currentTimeMillis();
      }
    }
    System.out.printf("Store %s made %d sales.\n", key.toString(), StoreMap.get(key).sales);
  }
}