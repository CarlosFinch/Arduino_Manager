/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino_manager;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.util.*;
import java.io.*;


/**
 *
 * @author Sebastian
 */
public class RXTX {
    //for containing the ports that will be found
    private Enumeration ports = null;
    //portparams
    public static SerialPort serialPort = null;
    public static String status = "disconnected";
    public static OutputStream output;
    public static InputStream input; 
    /**
     *
     * @return
     */
    
    public List<String> searchForPorts() {
        ports = CommPortIdentifier.getPortIdentifiers();
        while (ports.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();
            //get only serial ports
            List<String> portlist = new ArrayList<>();
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portlist.add(curPort.getName());              
                return portlist;
            }
        }
        return null;
    }
    
  public String connect(String PortNo, String baudRate) throws PortInUseException, UnsupportedCommOperationException, IOException {
        try {  
            if (!"connected".equals(status)) {                
                //First of all, you need to get the serial port’s ID
                CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(PortNo);
                //Then, you’ll need to ask the OS to give the serial port ownership to you.
                serialPort = (SerialPort) portId.open("Arduino_Manager", 5000);
                //you can set the serial port parameters
                serialPort.setSerialPortParams(Integer.parseInt(baudRate), SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                //Don’t forget to set its flow control
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                //Lastly, before you can read or write to serial port. 
                //You’ll need its inputstream and outputstream.
                status = "connected";
                // open the streams            
                input = serialPort.getInputStream();
                output = serialPort.getOutputStream();                
                // add event listeners
                serialPort.addEventListener(new SerialListener());
                serialPort.notifyOnDataAvailable(true);
                return "OK";
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            return "ERROR"; 
        }        
        return "ERROR"; 
  }
  public static synchronized void writeData(String data) {
    if (!"disconnected".equals(status)) {
        JFrameMain.writeResponse("Sending: " + data);
        try {
            output.write(data.getBytes());
        } catch (Exception e) {
            JFrameMain.writeResponse("could not write to port");
        }
    } else {
        JFrameMain.writeResponse("Port is not open");        
    }
  }
    /**
     *Close the connection
     */
    public String diconnect() {      
        if (serialPort != null) { 
            //serialPort.removeEventListener();
            serialPort.close();
            status = "disconnected";
            return "OK";
        } else {
            return "ERROR";        
        }
  }
}
