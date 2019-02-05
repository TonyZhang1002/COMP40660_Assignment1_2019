package standrad;

import java.util.HashMap;
import java.util.Map;

/*
@author Qinyuan Zhang
@date 04/02/2019
*/
public class Standard_11a extends Standard_802_11{

   public Standard_11a(double symbol_transit_time, int CTS_length, int ACK_length,
                       int preamble, int SIFS_time, int slot_time, int MAC_header) {
      super(symbol_transit_time, CTS_length, ACK_length, preamble, SIFS_time, slot_time, MAC_header);
   }

}
