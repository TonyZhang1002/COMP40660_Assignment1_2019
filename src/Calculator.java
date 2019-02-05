import standrad.Standard_11a;
import standrad.Standard_11g;
import standrad.Standard_11n;

/*
@author Qinyuan Zhang
@date 04/02/2019
*/
public class Calculator {

   public static void main(String[] args) {
      Standard_11a standard_11a = new Standard_11a(4, 14, 14, 20, 16, 9, 34);
      System.out.println(standard_11a.time_calculator_1500bytes("ag", "udp", 54.0));

      Standard_11g standard_11g = new Standard_11g(4, 14, 14, 6, 10, 9, 34);
      System.out.println(standard_11g.time_calculator_1500bytes("ag", "udp", 54.0));

//      Standard_11n standard_11n = new Standard_11n(3.6, 14, 14, 46, 16, 9, 40);
//      System.out.println(standard_11g.time_calculator_1500bytes("n", "udp", 96.3));
   }
}
