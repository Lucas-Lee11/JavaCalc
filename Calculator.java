import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Calculator implements WindowListener{

    private JFrame f;
    private JLabel des;
    private ArrayList<JButton> options;

    public void windowClosed(WindowEvent e){
        f.setVisible(true);
    }

    public void windowDeactivated(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeconfined(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}

    public Calculator(){
        WindowListener w = this;
        options = new ArrayList<JButton>();
        f = new JFrame("Calculator Main Menu");

        options.add(new JButton("Evaluator"));
        options.get(0).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                JFrame eval = new Evaluator();
                eval.addWindowListener(w);
            }
        });
        options.add(new JButton("Polynomial Equations"));
        options.get(1).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                JFrame eq = new Equation();
                eq.addWindowListener(w);
            }
        });

        des = new JLabel("Calculator Options");
        des.setBounds(10, -5, 500, 50);
        f.add(des);
        int track = 50;

        for(JButton option : options){
            f.add(option);
            option.setBounds(5, track, 200, 30);
            track += 30;
        }

        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


    public static void main(String[] args) {
        new Calculator();
    }





}
