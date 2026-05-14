import CalcApp.*;
import CalcApp.CalcPackage.DivisionByZero;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

class CalcImpl extends CalcPOA {

    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public float sum(float a, float b) {
    	System.out.println("");
        return a + b;
    }

    public float sub(float a, float b) {
        return a - b;
    }

    public float mul(float a, float b) {
        return a * b;
    }

    public float div(float a, float b) throws DivisionByZero {
        if (b == 0) {
            throw new DivisionByZero();
        }
        return a / b;
    }
}

public class CalcServer {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);

            POA rootpoa = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            CalcImpl impl = new CalcImpl();
            impl.setORB(orb);

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(impl);
            Calc href = CalcHelper.narrow(ref);

            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(objRef);

            NameComponent path[] = ncRef.to_name("Calc");
            ncRef.rebind(path, href);

            System.out.println("Server ready...");
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
