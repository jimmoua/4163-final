package multiton;

import java.util.concurrent.CopyOnWriteArrayList;
// import java.util.Iterator;

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
    while(!customerList.isEmpty());
  }
}