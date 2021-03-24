import java.util.*;

public class Polynomial{

    private Complex[] terms;

    public Polynomial(Complex[] terms){
        if(terms.length != 5) throw new IllegalArgumentException();
        this.terms = terms;
    }

    public Complex[] getRoots(){
        Complex z = new Complex(0);

        if(terms[0].equals(z)){
            if(terms[1].equals(z)){
                if(terms[2].equals(z)){
                    if(terms[3].equals(z)){
                        throw new IllegalArgumentException();
                    }else return linearRoots();
                }else return quadraticRoots();
            } else return cubicRoots();
        } else return quarticRoots();
    }

    public Polynomial getDerivative(){
        Complex[] nt = new Complex[5];
        nt[0] = new Complex(0);

        for(int i = 1; i < 5; i++){
            nt[i] = Complex.mult(terms[i - 1], new Complex(5-i));
        }

        return new Polynomial(nt);
    }

    private Complex[] quarticRoots(){
        Complex A = terms[0]; Complex B = terms[1]; Complex C = terms[2]; Complex D = terms[3]; Complex E = terms[4];

        Complex al = Complex.sub(
            Complex.div(C,A),
            Complex.div(
                Complex.mult(
                    new Complex(3),
                    Complex.pow(B, new Complex(2))
                ),
                Complex.mult(
                    new Complex(8),
                    Complex.pow(A, new Complex(2))
                )
            )
        );
        Complex be = Complex.sub(
            Complex.div(
                Complex.pow(B,new Complex(3)),
                Complex.mult(
                    new Complex(8),
                    Complex.pow(A, new Complex(3))
                )
            ),
            Complex.div(
                Complex.mult(B,C),
                Complex.mult(
                    new Complex(2),
                    Complex.pow(A, new Complex(2))
                )
            )
        );
        be = Complex.add(be,
            Complex.div(D,A)
        );

        Complex ga1 = Complex.sub(
            Complex.div(
                Complex.mult(
                    C,
                    Complex.pow(B, new Complex(2))
                ),
                Complex.mult(
                    new Complex(16),
                    Complex.pow(A, new Complex(3))
                )
            ),
            Complex.div(
                Complex.mult(
                    new Complex(3),
                    Complex.pow(B, new Complex(4))
                ),
                Complex.mult(
                    new Complex(256),
                    Complex.pow(A, new Complex(4))
                )
            )
        );
        Complex ga2 = Complex.sub(
            Complex.div(E,A),
            Complex.div(
                Complex.mult(B,D),
                Complex.mult(
                    new Complex(4),
                    Complex.pow(A, new Complex(2))
                )
            )
        );
        Complex ga = Complex.add(ga1, ga2);

        if(be.equals(new Complex(0))){
            Complex[] roots = new Complex[]{
                new Complex(0), new Complex(0), new Complex(0), new Complex(0)
            };
            for(int i = 0; i <= 3; i ++){
                int s = (int) Math.pow(-1, i);
                int t = (i/2 * 2) -1;

                Complex con = Complex.div(Complex.mult(new Complex(-1), B), Complex.mult(new Complex(4), A));
                Complex dis = Complex.sqrt(Complex.sub(Complex.pow(al, new Complex(2)), Complex.mult(new Complex(4), ga)));
                dis = Complex.mult(dis, new Complex(t));
                dis = Complex.add(Complex.mult(new Complex(-1), al), dis);
                dis = Complex.mult(new Complex(s), Complex.sqrt(Complex.div(dis, new Complex(2))));
                roots[i] = Complex.add(con, dis);
            }
            return roots;
        }

        Complex P = Complex.add(
            Complex.div(
                Complex.pow(al,new Complex(2)),
                new Complex(12)
            ),
            ga
        );
        P = Complex.mult(new Complex(-1), P);

        Complex Q = Complex.sub(
            Complex.div(
                Complex.mult(al, ga),
                new Complex(3)
            ),
            Complex.div(
                Complex.pow(be, new Complex(2)),
                new Complex(8)
            )
        );
        Q = Complex.sub(Q,
            Complex.div(Complex.pow(al, new Complex(3)), new Complex(108))
        );

        Complex R = Complex.add(
            Complex.div(Complex.pow(Q, new Complex(2)), new Complex(4)),
            Complex.div(
                Complex.pow(P, new Complex(3)),
                new Complex(27)
            )
        );
        R = Complex.sub(
            Complex.sqrt(R),
            Complex.div(Q, new Complex(2))
        );

        Complex U = Complex.pow(R, new Complex(1.0/3.0));

        Complex y = Complex.mult(new Complex(-5.0/6.0), al);
        if(U.equals(new Complex(0))) y = Complex.sub(y, Complex.pow(Q, new Complex(1.0/3.0)));
        else {
            y = Complex.sub(
                y,
                Complex.div(P, Complex.mult(new Complex(3), U))
            );
            y = Complex.add(y, U);
        }

        Complex W = Complex.sqrt(Complex.add(al, Complex.mult(new Complex(2), y)));

        Complex[] roots = new Complex[]{
            new Complex(0), new Complex(0), new Complex(0), new Complex(0)
        };
        for(int i = 0; i <= 3; i++){
            int s = (int) Math.pow(-1, i);
            int t = (i/2 * 2) -1;

            Complex out = Complex.mult(
                new Complex(s),
                Complex.div(
                    Complex.mult(new Complex(2), be),
                    W
                )
            );
            out = Complex.add(
                Complex.add(
                    Complex.mult(new Complex(3), al),
                    Complex.mult(new Complex(2), y)
                ),
                out
            );
            out = Complex.mult(new Complex(t), Complex.sqrt(Complex.mult(new Complex(-1), out)));
            out = Complex.div(
                Complex.add(
                    Complex.mult(new Complex(s), W),
                    out
                ),
                new Complex(2)
            );
            out = Complex.sub(
                out,
                Complex.div(
                    B,
                    Complex.mult(new Complex(4), A)
                )
            );
            roots[i] = out;
        }

        return roots;
    }

    private Complex[] cubicRoots(){
        Complex A = terms[1]; Complex B = terms[2]; Complex C = terms[3]; Complex D = terms[4];

        Complex del0 = Complex.sub(
            Complex.pow(B, new Complex(2)),
            Complex.mult(Complex.mult(A,C), new Complex(3))
        );
        Complex del1 = Complex.add(
            Complex.mult(
                new Complex(2),
                Complex.pow(B, new Complex(3))
            ),
            Complex.mult(
                new Complex(27),
                Complex.mult(D, Complex.pow(A, new Complex(2)))
            )
        );
        del1 = Complex.sub(
            del1,
            Complex.mult(
                new Complex(9),
                Complex.mult(A, Complex.mult(B, C))
            )
        );

        Complex ca = Complex.sqrt(
            Complex.sub(
                Complex.pow(del1, new Complex(2)),
                Complex.mult(new Complex(4), Complex.pow(del0, new Complex(3)))
            )
        );
        if(ca.equals(del1)) ca = Complex.add(ca, del1);
        else ca = Complex.sub(del1, ca);
        ca = Complex.pow(Complex.div(ca, new Complex(2)), new Complex(1.0/3.0));

        Complex z = Complex.div(
            Complex.sub(Complex.sqrt(new Complex(-3)), new Complex(1)),
            new Complex(2)
        );
        Complex[] roots = new Complex[]{
            new Complex(0), new Complex(0), new Complex(0)
        };
        for(int i = 0; i <= 2; i++){
            Complex zeta = Complex.pow(z, new Complex (i));
            ca = Complex.mult(ca, zeta);

            Complex x = Complex.add(
                Complex.div(del0, ca),
                Complex.add(ca, B)
            );
            x = Complex.mult(
                x,
                Complex.div(
                    new Complex(-1),
                    Complex.mult(new Complex(3), A)
                )
            );
            roots[i] = x;
        }

        return roots;

    }

    private Complex[] quadraticRoots(){
        Complex A = terms[2]; Complex B = terms[3]; Complex C = terms[4];

        Complex dis = Complex.sqrt(
            Complex.sub(
                Complex.pow(B, new Complex(2)),
                Complex.mult(new Complex(4), Complex.mult(A,C))
            )
        );

        Complex[] roots = new Complex[]{
            new Complex(0), new Complex(0)
        };
        B = Complex.mult(new Complex(-1), B);

        for(int i = 0; i <= 1; i++){
            Complex x = new Complex(0);
            if (i == 0) x = Complex.add(B, dis);
            else x = Complex.sub(B, dis);

            x = Complex.div(x, Complex.mult(new Complex(2), A));
            roots[i] = x;
        }
        return roots;

    }

    private Complex[] linearRoots(){
        return new Complex[]{
            Complex.div(
                Complex.mult(terms[4], new Complex(-1)),
                terms[3]
            )
        };
    }

    public String toString(){
        String out = "";
        for(int i = 0 ; i < 5; i ++){
            if(!terms[i].equals(new Complex(0))) {
                out += ("(" + terms[i] +  ")");
                if(i != 4) out+= ("x^" + (4-i));
                out += " + ";
            }
        }
        if (out.equals("")) return "0.0";
        out = out.substring(0,out.length() - 3);

        return out;
    }

    public static Polynomial sub(Polynomial f, Polynomial g){
        Polynomial out = new Polynomial(new Complex[5]);
        for(int i = 0; i < 5; i++)
            out.terms[i] = Complex.sub(f.terms[i], g.terms[i]);

        return out;

    }

    public static void main(String[] args) {
        Polynomial f = new Polynomial(new Complex[]{
            new Complex(0),
            new Complex(0),
            new Complex(0),
            new Complex(0),
            new Complex(0)
        });

        //Complex[] rts = f.getRoots();
        System.out.println(f);
        //for(Complex rt: rts) System.out.println(rt);
        System.out.println(f.getDerivative());
    }
}
