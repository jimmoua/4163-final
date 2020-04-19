package multiton;

import java.util.ArrayList;
import java.util.Iterator;

import multiton.Store.*;

public class Main {
  public static ArrayList<Customer> customerList = new ArrayList<Customer>();
  public static volatile int numCustomers = 0;

  public static void main(String[] args) {
    new GUI();
    while(numCustomers <= 0);
    for(int i = 0; i < numCustomers; i++) {
      customerList.add(new Customer());
    }
    while(!customerList.isEmpty()) {
      Iterator<Customer> iter = customerList.iterator();
      while(iter.hasNext()) {
        Customer c = iter.next();
        c.goShopping();
        if(c.shouldStopShopping()) {
          GUI.getTField().setText(String.valueOf(Main.customerList.size()-1));
          iter.remove();
        }
      }
      try {
        Thread.sleep(700);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.printf("Total sales: %d\n", Store.ttl);
  }
}