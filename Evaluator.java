import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Evaluator extends JFrame implements ActionListener{

    private JButton solve, back;
    private JLabel intro, out, ans;
    private JTextField enter;
    private Solver s;

    public Evaluator(){
        super("Expression Evaluator");
        JFrame f = this;

        solve = new JButton("Evaulate");
        back = new JButton("Back");
        intro = new JLabel("Enter an expression to evaluate");
        ans = new JLabel("Answer:");
        out = new JLabel(" ");
        enter = new JTextField();

        solve.setBounds(4, 75, 100, 30);
        back.setBounds(4, 5, 75, 30);
        enter.setBounds(150, 75, 500, 30);
        intro.setBounds(10, 25, 500, 50);
        out.setBounds(10, 115, 500, 50);
        ans.setBounds(10, 95, 500, 50);

        solve.addActionListener(this);
        enter.addActionListener(this);
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.dispose();
            }
        });


        add(solve);
        add(enter);
        add(intro);
        add(out);
        add(ans);
        add(back);

        setSize(675,200);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        String exp = enter.getText();
        try{
            s = new Solver(exp);
            out.setText(s.getValue().toString());
        }
        catch(IllegalArgumentException a){
            out.setText("Invalid expression");
        }
    }


    public static void main(String[] args) {
        new Evaluator();
    }

}
