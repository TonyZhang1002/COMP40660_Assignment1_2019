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
   double Preamble;
   int SIFS_time;
   int Slot_time;
   int DIFS_time;
   int MAC_header;
   int TCP_ACK_length;
   int Signal_Extension;
   int Spatial_Streams;
   int A_MPDU_n_length = 65535;
   int A_MPDU_ac_length = 1048576;

   Map<Double, Double> Block_size = new HashMap();

   Standard_802_11(double symbol_transit_time, int CTS_length, int ACK_length,
                   double preamble, int SIFS_time, int slot_time, int MAC_header, int Signal_Extension) {
      this.Symbol_transit_time = symbol_transit_time;
      this.CTS_length = CTS_length;
      this.ACK_length = ACK_length;
      this.Preamble = preamble;
      this.SIFS_time = SIFS_time;
      this.Slot_time = slot_time;
      this.DIFS_time = 2 * Slot_time + SIFS_time;
      this.MAC_header = MAC_header;
      this.TCP_ACK_length = MAC_header + 8 + 20 + 20;
      this.Signal_Extension = Signal_Extension;
   }

   private void init_block_size_ag() {
      Block_size.put(6.0, 24.0);
      Block_size.put(9.0, 36.0);
      Block_size.put(12.0, 48.0);
      Block_size.put(18.0, 72.0);
      Block_size.put(24.0, 96.0);
      Block_size.put(36.0, 144.0);
      Block_size.put(48.0, 192.0);
      Block_size.put(54.0, 216.0);
   }

   private void init_block_size_nac() {
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

   public double time_calculator(String standard, String tcp_udp, double data_rate, Boolean aggregation) {
      double bits_per_symbol = 0;
      double Aggregation_DATA_time = 0;
      if (standard.equals("ag")) {
         init_block_size_ag();
         bits_per_symbol = Block_size.get(data_rate);
      }
      else if (standard.equals("n")) {
         init_block_size_nac();
         Block_size.remove(86.7);
         Block_size.remove(96.3);
         Block_size.put(150.0, 260.0);
         Spatial_Streams = 4;
         bits_per_symbol = Block_size.get(data_rate) / 52 * 108 * Spatial_Streams;
         Aggregation_DATA_time = (int)(( A_MPDU_n_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      }
      else if (standard.equals("ac_w1")) {
         init_block_size_nac();
         Block_size.put(200.0, 346.6);
         Block_size.put(433.4, 346.6);
         Block_size.put(866.7, 346.6);
         Spatial_Streams = 3;
         bits_per_symbol = Block_size.get(data_rate) / 52 * 234 * Spatial_Streams;
         Aggregation_DATA_time = (int)(( A_MPDU_ac_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      }
      else if (standard.equals("ac_w2")) {
         init_block_size_nac();
         Block_size.put(200.0, 346.6);
         Block_size.put(433.4, 346.6);
         Block_size.put(866.7, 346.6);
         Spatial_Streams = 8;
         bits_per_symbol = Block_size.get(data_rate) / 52 * 468 * Spatial_Streams;
         Aggregation_DATA_time = (int)(( A_MPDU_ac_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      }

      int RTS_length = 20;
      double RTS_time = (int)((RTS_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double CTS_time = (int)((CTS_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double ACK_time = (int)((ACK_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double TCP_ACK_time = (int)((TCP_ACK_length * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double DATA_time = (int)(((1500 + MAC_header + 8) * 8 + 6) / bits_per_symbol + 1) * Symbol_transit_time;
      double Sum_time = 0;
      if (tcp_udp.equals("udp") && !aggregation) {
         Sum_time = DIFS_time + Preamble + RTS_time + Signal_Extension + SIFS_time + Preamble + CTS_time +
                 Signal_Extension + SIFS_time + Preamble + DATA_time + Signal_Extension + SIFS_time +
                 Preamble + ACK_time + Signal_Extension;
      }
      else if (tcp_udp.equals("tcp") && !aggregation) {
         Sum_time = DIFS_time + Preamble + RTS_time + Signal_Extension + SIFS_time + Preamble +
                 CTS_time + Signal_Extension + SIFS_time + Preamble + DATA_time + Signal_Extension +
                 SIFS_time + Preamble + ACK_time + DIFS_time + Preamble + TCP_ACK_time +
                 Signal_Extension + SIFS_time + Preamble + ACK_time + Signal_Extension;
      }
      else if (tcp_udp.equals("udp") && aggregation) {
         Sum_time = DIFS_time + Preamble + RTS_time + Signal_Extension + SIFS_time + Preamble + CTS_time +
                 Signal_Extension + SIFS_time + Preamble + Aggregation_DATA_time + Signal_Extension + SIFS_time +
                 Preamble + ACK_time + Signal_Extension;
      }
      else if (tcp_udp.equals("tcp") && aggregation) {
         Sum_time = DIFS_time + Preamble + RTS_time + Signal_Extension + SIFS_time + Preamble +
                 CTS_time + Signal_Extension + SIFS_time + Preamble + Aggregation_DATA_time + Signal_Extension +
                 SIFS_time + Preamble + ACK_time + DIFS_time + Preamble + TCP_ACK_time +
                 Signal_Extension + SIFS_time + Preamble + ACK_time + Signal_Extension;
      }
      return Sum_time;
   }

}
