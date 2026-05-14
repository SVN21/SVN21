import java.io.*;
import CalcApp.*;
import CalcApp.CalcPackage.DivisionByZero;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import static java.lang.System.out;

public class CalcClient {

    static Calc calcImpl;
    static BufferedReader br =
        new BufferedReader(new InputStreamReader(System.in));

    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(objRef);

            calcImpl = CalcHelper.narrow(ncRef.resolve_str("Calc"));

            while (true) {
            	out.println("");
                out.println("1. Sum");
                out.println("2. Sub");
                out.println("3. Mul");
                out.println("4. Div");
                out.println("5. exit");
                out.println("----------");
                out.println("choice:");

                String opt = br.readLine();

                if (opt.equals("5")) break;

                try {
                    if (opt.equals("1")) {
                        out.println("a+b= " + calcImpl.sum(getFloat("a"), getFloat("b")));
                    } else if (opt.equals("2")) {
                        out.println("a-b= " + calcImpl.sub(getFloat("a"), getFloat("b")));
                    } else if (opt.equals("3")) {
                        out.println("a*b= " + calcImpl.mul(getFloat("a"), getFloat("b")));
                    } else if (opt.equals("4")) {
                        try {
                            out.println("a/b= " + calcImpl.div(getFloat("a"), getFloat("b")));
                        } catch (DivisionByZero de) {
                            out.println("Division by zero!!!");
                        }
                    }
                } catch (Exception e) {
                    out.println("Invalid input!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static float getFloat(String number) throws Exception {
        out.print(number + ": ");
        String input = br.readLine();

        if (input == null || input.trim().isEmpty()) {
            throw new Exception("Invalid input");
        }

        return Float.parseFloat(input.trim());
    }
}
