package multiton;

import java.util.concurrent.CopyOnWriteArrayList;

import multiton.Store.*;

public class Main {
  public static CopyOnWriteArrayList<Customer> customerList = new CopyOnWriteArrayList<Customer>();
  public static volatile int numCustomers = 0;

  public static void main(String[] args) {
    new GUI();
    while(numCustomers <= 0);
    for(int i = 0; i < numCustomers; i++) {
      customerList.add(new Customer());
    }
    while(!customerList.isEmpty()) {
      for (Customer i : customerList) {
        i.goShopping();
      }
      // try {
      //   Thread.sleep(1500);
      // } catch (InterruptedException e) {
      //   e.printStackTrace();
      // }
    }
    System.out.printf("Total sales made: %d\n", Store.ttlSales);
  }
}