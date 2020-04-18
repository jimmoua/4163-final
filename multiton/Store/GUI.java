package multiton.Store;

import java.util.concurrent.ConcurrentHashMap;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import multiton.Main;

class Pair {

  Pair(JLabel label, JTextField textField) {
    itemStoreLabel = label;
    itemStoreLabel.setPreferredSize(GUI.dim);
    itemStoreStockTextField = textField;
    itemStoreStockTextField.setEditable(false);
    itemStoreStockTextField.setPreferredSize(GUI.dim);
  }

  public JLabel itemStoreLabel;
  public JTextField itemStoreStockTextField;
}

public class GUI extends JFrame {
  public final static Dimension dim = new Dimension(150, 20);
  private static final long serialVersionUID = 1L;
  public static ConcurrentHashMap<Location, Pair> itemStockGUI = new ConcurrentHashMap<Location, Pair>();
  private JButton simulateButton;
  private static JTextField numCustomersTextField;
  public static JTextField getTField() {
    return numCustomersTextField;
  }

  public GUI() {
    try {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch(InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        e.printStackTrace();
      }
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
    }
    setTitle("Multiton Example");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationByPlatform(true);
    init();
    setVisible(true);
  }

  private void init() {
    JPanel dataPanel = new JPanel();
    dataPanel.setLayout(new GridBagLayout());

    JLabel numCustomerLabel = new JLabel("# of customers");
    numCustomersTextField = new JTextField();
    numCustomersTextField.setPreferredSize(new Dimension(150, 20));
    dataPanel.add(numCustomerLabel, getConstraints(0, 3, GridBagConstraints.LINE_START));
    dataPanel.add(numCustomersTextField, getConstraints(1, 3, GridBagConstraints.LINE_START));

    for(Location i : Location.values()) {
      itemStockGUI.put(i, new Pair(new JLabel(i.toString()), new JTextField("NULL")));
    }
    itemStockGUI.forEach((k, v) -> {
      dataPanel.add(v.itemStoreLabel, getConstraints(k.ordinal(), 0, GridBagConstraints.LINE_START));
      dataPanel.add(v.itemStoreStockTextField, getConstraints(k.ordinal(), 1, GridBagConstraints.LINE_START));
    });
    simulateButton = new JButton("Simulate");

    simulateButton.addActionListener((ev) -> {
      simulateButtonClicked();
    });

    dataPanel.add(simulateButton, getConstraints(2, 3, GridBagConstraints.LINE_START));
    add(dataPanel);
    pack();
  }

  private GridBagConstraints getConstraints(int x, int y, int anchor) {
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5, 5, 0, 5);
    c.gridx = x;
    c.gridy = y;
    c.anchor = anchor;
    return c;
  }

  private void simulateButtonClicked() {
    try {
      Main.numCustomers = Integer.parseInt(getTField().getText());
    } catch(NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Please enter integers only.");
      e.printStackTrace();
      return;
    }
    if(Main.numCustomers < 0) {
      JOptionPane.showMessageDialog(this, "Please enter in positive integer.");
    }
    else {
      getTField().setEditable(false);
      simulateButton.setEnabled(false);
    }
  }

}