package standrad;

/*
@author Qinyuan Zhang
@date 05/02/2019
*/
public class Standard_11n extends Standard_802_11 {


   public Standard_11n(double symbol_transit_time, int CTS_length, int ACK_length,
                       int preamble, int SIFS_time, int slot_time, int MAC_header) {
      super(symbol_transit_time, CTS_length, ACK_length, preamble, SIFS_time, slot_time, MAC_header);
   }
}
