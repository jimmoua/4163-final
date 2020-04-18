package multiton.Store;

// import java.util.HashMap;
import java.util.HashMap;

import multiton.Main;

public class Store implements Runnable {

  private Store(final Location key) {
    System.out.printf("A store in %s has been built!\n", key.toString());
    MAX_STOCK = 50000;
    RESTOCK_COUNT = 20000;
    items = RESTOCK_COUNT;
    sales = 0;
    this.key = key;
    Thread storeThread = new Thread(this);
    storeThread.start();
  }

  // Static mapping
  private static HashMap<Location, Store> StoreMap = new HashMap<Location, Store>();

  // Store information
  private int items;
  private int MAX_STOCK;
  private int RESTOCK_COUNT;
  private Location key; // Location of the store
  private int sales;

  private void restockShelf() {
    StoreMap.get(key).items+=RESTOCK_COUNT;
    if(StoreMap.get(key).items > MAX_STOCK) StoreMap.get(key).items = MAX_STOCK;
    updateStock();
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

  public int getItemStock() {
    return StoreMap.get(key).items;
  }

  public void sellItems(final int itemCount) {
    StoreMap.get(key).items-=itemCount;
    updateStock();
    StoreMap.get(key).sales++;
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