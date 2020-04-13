package multiton;

import java.util.concurrent.CopyOnWriteArrayList;

import multiton.Store.*;

public class Main {
  private static CopyOnWriteArrayList<Customer> customerList = new CopyOnWriteArrayList<Customer>();

  public static synchronized CopyOnWriteArrayList<Customer> getCustomerList() {
    return customerList;
  }

  public static volatile int numCustomers = -1;

  public static void main(String[] args) {
    new GUI();
    while(numCustomers < 0);
    for(int i = 0; i < numCustomers; i++) {
      customerList.add(new Customer());
    }
    for(var i : getCustomerList()) {
      i.start();
    }
    while(!customerList.isEmpty());
  }
}