package multiton;

import java.util.concurrent.CopyOnWriteArrayList;

import multiton.Store.*;

public class Main {
  public static CopyOnWriteArrayList<Customer> customerList = new CopyOnWriteArrayList<Customer>();
  public static void main(String[] args) {
    final int numCustomers = 250;
    for(int i = 0; i < numCustomers; i++) {
      customerList.add(new Customer());
    }
    while(!customerList.isEmpty());
    for(Location i : Location.values()) {
      System.out.printf("Store %s made %d sales.\n", i.toString(), Store.getStore(i).getSales());
    }
    System.out.println("PROGRAM END");
  }
}