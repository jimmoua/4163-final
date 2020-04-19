package multiton;

// import java.util.concurrent.CopyOnWriteArrayList;
// import java.util.Iterator;

import multiton.Store.*;

public class Main {
  // public static CopyOnWriteArrayList<Customer> customerList = new CopyOnWriteArrayList<Customer>();
  private static volatile int numCustomers = 0;
  public static synchronized int getNum() {
    return numCustomers;
  }
  public static synchronized void setNum(int x) {
    numCustomers = x;
  }
  public static synchronized int decNum() {
    return --numCustomers;
  }

  public static void main(String[] args) {
    new GUI();
    while(numCustomers <= 0);
    for(int i = 0; i < numCustomers; i++) {
      new Customer();
    }
  }
}