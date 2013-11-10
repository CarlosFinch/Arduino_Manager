/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino_manager;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 *
 * @author Sebastian
 */
class SerialListener implements SerialPortEventListener {
    public void serialEvent(SerialPortEvent event) {
    switch (event.getEventType()) {
        case SerialPortEvent.DATA_AVAILABLE:
            try {                                  
                Thread.sleep(500);
                int available=RXTX.input.available();
                byte chunk[]=new byte[available];
                RXTX.input.read(chunk,0,available);
                 JFrameMain.writeResponse(new String(chunk).trim());                
            } catch (Exception e) {
            }
            break;
        }
  }
}
