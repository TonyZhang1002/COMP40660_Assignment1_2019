import standrad.Standard_11a;
import standrad.Standard_11ac_w1;
import standrad.Standard_11g;
import standrad.Standard_11n;

import java.util.Scanner;

/*
@author Qinyuan Zhang
@date 04/02/2019
*/
public class Calculator {

   private static double getBytesTime(String standard, String protocol, double dataRate, boolean aggregation) {
      if (standard.equals("a")) {
         Standard_11a standard_11a = new Standard_11a(4, 14, 14, 20, 16, 9, 34, 0);
         return standard_11a.time_calculator("ag", protocol, dataRate, aggregation);
      } else if (standard.equals("g")) {
         Standard_11g standard_11g = new Standard_11g(4, 14, 14, 20, 10, 9, 34, 6);
         return standard_11g.time_calculator("ag", protocol, dataRate, aggregation);
      } else if (standard.equals("n")) {
         Standard_11n standard_11n = new Standard_11n(3.6, 14, 14, 46, 16, 9, 40, 0);
         return standard_11n.time_calculator("n", protocol, dataRate, aggregation);
      } else if (standard.equals("ac_w1")) {
         Standard_11ac_w1 standard_11ac_w1 = new Standard_11ac_w1(3.6, 14, 14, 56.8, 16, 9, 40, 0);
         return standard_11ac_w1.time_calculator("ac_w1", protocol, dataRate, aggregation);
      }
      return 0;
   }

   private static void standardPrint(double bytesTime_1500) {
      System.out.println("The actual throughput with RTS/CTS and without Aggregation: " +
              String.format("%.3f", Math.pow(10, 6) / bytesTime_1500 * 8 * 1500 / Math.pow(1000, 2)) +
              " Mbps\n" + "The amount of time needed to transfer 10GB of data with RTS/CTS and without Aggregation: " +
              String.format("%.3f", 10 * Math.pow(1024, 3) / 1500 * bytesTime_1500 / Math.pow(10, 6)) + " Seconds");
   }

   private static void aggregationPrint(String standard, double aggregationTime) {
      int MPDU_payload_length = 1500;
      int MPDU_header_length = 40;
      int MPDU_delimiter_length = 4;
      int actualData = 0;
      if (standard.equals("n")) {
         int MPDU_numbers = 65535 / (MPDU_payload_length + MPDU_header_length + MPDU_delimiter_length);
         actualData = MPDU_numbers * 1500 + (65535 - MPDU_numbers * MPDU_payload_length) -
                 MPDU_header_length - MPDU_delimiter_length;
      } else if (standard.equals("ac_w1")) {
         int MPDU_numbers = 1048576 / (MPDU_payload_length + MPDU_header_length + MPDU_delimiter_length);
         actualData = MPDU_numbers * 1500 + (1048576 - MPDU_numbers * MPDU_payload_length) -
                 MPDU_header_length - MPDU_delimiter_length;
      }
      System.out.println("The actual throughput with RTS/CTS and with Aggregation: " +
              String.format("%.3f", Math.pow(10, 6) / aggregationTime * 8 * actualData / Math.pow(1000, 2)) +
              " Mbps\n" + "The amount of time needed to transfer 10GB of data with RTS/CTS and with Aggregation: " +
              String.format("%.3f", 10 * Math.pow(1000, 3) / actualData * aggregationTime / Math.pow(10, 6)) + " Seconds");
   }

   public static void main(String[] args) {

      Scanner scanner = new Scanner(System.in);
      System.out.println("----- Please enter the Transmission Control Protocol ('UDP' or 'TCP') ----- ");
      String udp_tcp = scanner.nextLine();

      System.out.println("----- Please enter the Data Rate ----- ");
      double dataRate = scanner.nextDouble();

      System.out.println("----- Please enter the Standard ('a' or 'g' or 'n' or 'ac_w1' or 'ac_w2') ----- ");
      String standard = scanner.next();

      if (standard.equals("a") || standard.equals("g") || standard.equals("n") || standard.equals("ac_w1")) {
         try {
            if (udp_tcp.toLowerCase().equals("udp") || udp_tcp.toLowerCase().equals("tcp")) {
               double bytesTime_1500 = getBytesTime(standard, udp_tcp.toLowerCase(), dataRate, false);
               standardPrint(bytesTime_1500);
               if (standard.equals("n") || standard.equals("ac_w1")) {
                  double bytesTime_aggregation = getBytesTime(standard, udp_tcp.toLowerCase(), dataRate, true);
                  aggregationPrint(standard, bytesTime_aggregation);
               }
            } else {
               System.out.println("||||| Your protocol input is wrong! |||||");
            }
         } catch (Exception e) {
            System.out.println("||||| Your data rate input is wrong! |||||");
         }
      }

   }
}
