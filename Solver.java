import java.util.Scanner;

public class Solver{
    private String toSolve;
    private Complex value;
    private int curPos;
    private int ch;

    public Solver(String equation){
        if(equation == null || equation.equals("")) throw new IllegalArgumentException();
        toSolve = equation;
        while(toSolve.charAt(0) == ' ') toSolve = toSolve.substring(1, toSolve.length());
        curPos = 0;
        ch = toSolve.charAt(curPos);
        value = addTogether();
    }

    public Complex getValue(){
        return value;
    }

    private void advance(){
        if(curPos + 1 < toSolve.length()){
            curPos++;
            ch = toSolve.charAt(curPos);
        }
        else ch = 0;
        while(ch == ' ') advance();
    }

    //Moves backwards on the string
    private void back(){
        if(curPos - 1 >= 0){
            curPos--;
            ch = toSolve.charAt(curPos);
        }
        while(ch == ' ') back();
    }

    //Tries to scan for a character
    private boolean lookFor(char toLook){
        if(ch == toLook) return true;
        else return false;
    }

    //Main eqSolver; calls multiplyTogether and adds/subtracts results
    private Complex addTogether(){
        Complex complexAnswer = multiplyTogether();
        while (true){
            advance();
            if(lookFor('+')){
                advance();
                complexAnswer = Complex.add(complexAnswer, multiplyTogether());
            }
            else if(lookFor('-')){
                advance();
                complexAnswer = Complex.sub(complexAnswer, multiplyTogether());
            }
            else{
                back();
                return complexAnswer;
            }
        }
    }

    //Multiplies or divides results of functionCompute
    private Complex multiplyTogether(){
        Complex complexAnswer = functionCompute();
        while (true){
            advance();
            if(lookFor('*')){
                advance();
                complexAnswer = Complex.mult(complexAnswer, functionCompute());
            }
            else if(lookFor('/')){
                advance();
                complexAnswer = Complex.div(complexAnswer, functionCompute());
            }
            else{
                back();
                return complexAnswer;
            }
        }

    }

    private Complex functionCompute(){
        double answer;
        Complex complexAnswer = new Complex(0);
        int startPos = curPos; //Used to find full value of functions or numbers

        //For direct modifiers to a number
        if(lookFor('+')){
            advance();
            return functionCompute();
        }
        else if(lookFor('-')){
            advance();
            return Complex.mult(functionCompute(), new Complex(-1));
        }
        //For parentheses
        else if(lookFor('(')){
            advance();
            complexAnswer = addTogether();
            advance();
        }

        else if ((ch >= '0' && ch <= '9') || ch == '.') {
            //Looks for digits
            while ((ch >= '0' && ch <= '9') || ch == '.') {
                advance();
            }

            //Automatically multiplies functions eg 3sin(30) = 3/2
            if(ch >= 'a' && ch <= 'z' || ch == '(') {
                back();
                answer = Double.parseDouble(toSolve.substring(startPos, curPos + 1));
                complexAnswer = new Complex(answer);
                advance();
                if(ch =='('){
                    advance();
                    complexAnswer = Complex.mult(complexAnswer, addTogether());
                }
                else complexAnswer = Complex.mult(complexAnswer, functionCompute());
                return complexAnswer;
            }
            else if(ch != 0){
                back();
            }

            answer = Double.parseDouble(toSolve.substring(startPos, curPos + 1));
            complexAnswer = new Complex(answer);

        }

        else if (ch >= 'a' && ch <= 'z') {

            //Looks for the names of functions
            while (ch >= 'a' && ch <= 'z'){
                advance();
            }

            if(ch != 0)back();

            String func = toSolve.substring(startPos, curPos + 1);
            boolean sp = false;
            if (func.equals("e")){
                answer = Math.E;
                complexAnswer = new Complex(answer);
                sp = true;
            }
            else if (func.equals("pi")){
                answer = Math.PI;
                complexAnswer = new Complex(answer);
                sp = true;
            }
            else if(func.equals("i")){
                complexAnswer = new Complex(0, 1);
                sp = true;
            }

            if (!sp){
                advance();
                if(lookFor('(')){
                    advance();

                }
                if(ch == 0){
                    throw new IllegalArgumentException ("No input for function: " + func + "()");

                }
                else {
                    complexAnswer = addTogether();
                    advance();
                }


                //List of recognized functions

                if (func.equals("sqrt")) complexAnswer = Complex.sqrt(complexAnswer);
                else if (func.equals("sin")) complexAnswer = Complex.sin(complexAnswer);
                else if (func.equals("cos")) complexAnswer = Complex.cos(complexAnswer);
                else if (func.equals("tan")) complexAnswer = Complex.tan(complexAnswer);
                else if (func.equals("ln")) complexAnswer = Complex.ln(complexAnswer);
                else if (func.equals("torad")) complexAnswer = Complex.mult(complexAnswer, new Complex(Math.PI /180));
                else throw new IllegalArgumentException("Unknown function: " + func + "()");
            }


        }
        else {
            complexAnswer = new Complex(0);
            throw new IllegalArgumentException("Unexpected character: " + toSolve.charAt(curPos));
        }

        //For exponents
        advance();
        if (lookFor('^')) {
            advance();
            complexAnswer = Complex.pow(complexAnswer, functionCompute());
        }
        else if(lookFor('&')){
            advance();
            complexAnswer = Complex.logBase(complexAnswer, functionCompute());
        }
        else back();

        return complexAnswer;

    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Type exit to leave");

        while (true){

            System.out.println("***************************");
            System.out.println("Enter an expression");
            String eq = input.nextLine();

            if(eq.toLowerCase().equals("exit")){
                break;
            }


            System.out.print("Evaluation: ");
            System.out.println(new Solver(eq).getValue());



        }
    }
}
