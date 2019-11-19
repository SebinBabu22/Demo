package sample1;
 import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
 import java.net.*;
 import javax.swing.*;
 
 class SquareClient extends JFrame implements ActionListener
 {
 JTextField serverAddress = new JTextField("127.0.0.1");
 JTextField input = new JTextField();
 JButton b = new JButton("Get Square");
 JLabel result = new JLabel(" ");

 public SquareClient() {
    super("Square Client");
   JPanel p = new JPanel(new GridLayout(4,1)),
       p1 = new JPanel(new FlowLayout());
    p.add(serverAddress);
    p.add(input);
    p1.add(b);
    p.add(p1);
    p.add(result);
    serverAddress.setBorder(BorderFactory.createTitledBorder("Server Address"));
   input.setBorder(BorderFactory.createTitledBorder("Input Number"));
    result.setBorder(BorderFactory.createTitledBorder("Result"));
    b.addActionListener(this);
    this.getContentPane().add(p);
    this.pack();
    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
   this.setVisible(true);
   }
 
public void actionPerformed(ActionEvent ae) {
   String txt = input.getText();
    if(txt == null)
      return;
    byte b[] = new byte[256];
    txt = txt.trim().replaceAll(",","");
    try {
      double in = Double.parseDouble(txt);
       Socket s = new Socket(serverAddress.getText(), 12345);
       s.getOutputStream().write(txt.getBytes());
       s.getOutputStream().flush();
       int n = s.getInputStream().read(b);
       txt = new String(b, 0, n).replaceAll(",","");
       double res = Double.parseDouble(txt);
       result.setText(in+" ^ 2 = "+res);
      } catch(Exception ex) { ex.printStackTrace(); }
    }
 
 public static void main(String arg[]) {
   new SquareClient();
    }
 }