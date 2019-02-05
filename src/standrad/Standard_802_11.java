package standrad;

import java.util.HashMap;
import java.util.Map;

/*
@author Qinyuan Zhang
@date 05/02/2019
*/
public class Standard_802_11 {

   double Symbol_transit_time;
   int CTS_length;
   int ACK_length;
   int Preamble;
   int SIFS_time;
   int Slot_time;
   int DIFS_time;
   int MAC_header;

   Map<Double, Double> Block_size = new HashMap();

   public Standard_802_11(double symbol_transit_time, int CTS_length, int ACK_length,
                          int preamble, int SIFS_time, int slot_time, int MAC_header) {
      this.Symbol_transit_time = symbol_transit_time;
      this.CTS_length = CTS_length;
      this.ACK_length = ACK_length;
      this.Preamble = preamble;
      this.SIFS_time = SIFS_time;
      this.Slot_time = slot_time;
      this.DIFS_time = 2 * Slot_time + SIFS_time;
      this.MAC_header = MAC_header;
   }

   void init_block_size_ag () {
      Block_size.put(6.0, 24.0);
      Block_size.put(9.0, 36.0);
      Block_size.put(12.0, 48.0);
      Block_size.put(18.0, 72.0);
      Block_size.put(24.0, 96.0);
      Block_size.put(36.0, 144.0);
      Block_size.put(48.0, 192.0);
      Block_size.put(54.0, 216.0);
   }

   void init_block_size_n () {
      Block_size.put(7.2, 26.0);
      Block_size.put(14.4, 52.0);
      Block_size.put(21.7, 78.0);
      Block_size.put(28.9, 104.0);
      Block_size.put(43.3, 156.0);
      Block_size.put(57.8, 208.0);
      Block_size.put(65.0, 234.0);
      Block_size.put(72.2, 260.0);
      Block_size.put(86.7, 312.0);
      Block_size.put(96.3, 346.6);
   }

   public double time_calculator_1500bytes(String standard, String tcp_udp, double data_rate) {
      if (standard.equals("ag"))
         init_block_size_ag();
      else if (standard.equals("n"))
         init_block_size_n();
      double bits_per_symbol = Block_size.get(data_rate);
      int RTS_length = 20;
      double RTS_time = (int)((RTS_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double CTS_time = (int)((CTS_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double ACK_time = (int)((ACK_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double DATA_time = (int)(((1500 + MAC_header + 8) * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double Sum_time = 0;
      if (tcp_udp.equals("udp")) {
         Sum_time = DIFS_time + Preamble + RTS_time + SIFS_time + Preamble + CTS_time +
                 SIFS_time + Preamble + DATA_time + SIFS_time + Preamble + ACK_time;
      }
      return Sum_time;
   }

}
