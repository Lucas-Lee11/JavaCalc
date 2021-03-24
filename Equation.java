import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Equation extends JFrame implements ActionListener{

    private JButton solve, back;
    private JLabel intro, out, ans, LHS, RHS, eqn, zero, der;
    private HashMap<JLabel, JTextField> side1, side2;
    private Solver s;

    public Equation(){
        super("Intersection Finder");
        JFrame f = this;

        solve = new JButton("Solution");
        back = new JButton("Back");
        intro = new JLabel("Enter terms for each side");
        LHS = new JLabel("Left side");
        RHS = new JLabel("Right side");
        ans = new JLabel("Solution set:");
        out = new JLabel("");
        eqn = new JLabel("");
        zero = new JLabel("");
        der = new JLabel("");
        side1 = new LinkedHashMap<JLabel, JTextField>();
        side2 = new LinkedHashMap<JLabel, JTextField>();
        for(int i = 4; i >= 0; i--){
            side1.put(new JLabel("x^" + i + " term"), new JTextField("0"));
            side2.put(new JLabel("x^" + i + " term"), new JTextField("0"));
        }

        solve.setBounds(4, 75, 100, 30);
        back.setBounds(4, 5, 75, 30);
        intro.setBounds(10, 25, 500, 30);
        LHS.setBounds(10, 95, 100, 30);
        RHS.setBounds(300, 95, 100, 30);
        eqn.setBounds(10, 400, 1000, 30);
        zero.setBounds(10, 420, 1000, 30);
        ans.setBounds(10, 500, 100, 30);
        out.setBounds(10, 520, 1000, 30);
        der.setBounds(10, 540, 1000, 30);

        int track = 120;
        for (Map.Entry<JLabel,JTextField> row : side1.entrySet()){
            row.getKey().setBounds(10, track, 100, 30);
            row.getValue().setBounds(150, track, 100, 30);
            track += 40;
        }
        track = 120;
        for (Map.Entry<JLabel,JTextField> row : side2.entrySet()){
            row.getKey().setBounds(300, track, 100, 30);
            row.getValue().setBounds(450, track, 100, 30);
            track += 40;
        }

        solve.addActionListener(this);
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.dispose();
            }
        });

        add(solve); add(back); add(intro); add(LHS); add(RHS); add(ans); add(out); add(zero); add(eqn); add(der);
        for (Map.Entry<JLabel,JTextField> row : side1.entrySet()) {add(row.getKey()); add(row.getValue());}
        for (Map.Entry<JLabel,JTextField> row : side2.entrySet()) {add(row.getKey()); add(row.getValue());}

        setSize(800,1100);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e){
        Complex [] first = new Complex[5];
        Complex [] second = new Complex[5];
        String output = "";

        try{
            Iterator<Map.Entry<JLabel,JTextField>> s1 = side1.entrySet().iterator();
            Iterator<Map.Entry<JLabel,JTextField>> s2 = side2.entrySet().iterator();

            for(int i = 0; i < 5; i++){
                s = new Solver(s1.next().getValue().getText());
                first[i] = s.getValue();
                s = new Solver(s2.next().getValue().getText());
                second[i] = s.getValue();
            }
            Polynomial f = new Polynomial(first);
            Polynomial g = new Polynomial(second);
            Polynomial s = Polynomial.sub(f, g);
            for(Complex rt : s.getRoots())
                output += (rt.toString() + ", ");
            out.setText("{" + output.substring(0, output.length()-2) + "}");
            eqn.setText(f + " = " + g);
            zero.setText(s + " = 0.0");
            der.setText("If f(x) = " + s + " ,then d f(x)/dx = " + s.getDerivative());

        }
        catch(IllegalArgumentException a){
            out.setText("Invalid Input ");
        }

    }

    public static void main(String[] args) {
        new Equation();
    }
}
