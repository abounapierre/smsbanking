/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.sms;

/**
 *
 * @author SATELLITE
 */
import com.abouna.lacussms.entities.BkEve;
import com.abouna.lacussms.entities.Message;
import com.abouna.lacussms.entities.MessageFormat;
import com.abouna.lacussms.main.App;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.tools.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TooManyListenersException;
import java.util.logging.Level;

import javax.comm.CommDriver;
import javax.comm.CommPortIdentifier;
import javax.comm.CommPortOwnershipListener;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import org.apache.log4j.Logger;

/**
 * Classe permettant de communiquer avec un Port/Serie ou RS-232 hardware 
 * Accepte les commandes 3GPP 27.007 et les recommandations 27.005 GSM
 * pour une carte SIM...
 * 
 * La communication en elle m�me peut se faire par cable Port Serie, IR ou Bluetooth
 * 
 * T�l�charger Java Communications 3.0 API :
 * http://java.sun.com/products/javacomm/
 * 
 * - Installer le jar javax.comm dans le classpath de votre application (comm.jar)
 *  et le fichier de configuration javax.comm.properties aussi
 * 
 * - Installer la dll pour Win32 seulement 'win32com.dll' dans le r�pertoire 'bin'
 *  de votre JRE 
 *  
 * Le "Pairing" de votre Adapteur Bluethooth et de votre GSM est indispensable 
 * avant toute communication (via le service Virtual Serial Device Bluetooth)  
 * 
 * @author Franck Andriano 2008 nexus6@altern.org
 * @version 1.0 
 */
public class SmsFinal implements SerialPortEventListener, CommPortOwnershipListener
{
	/*
	 * Instance Object Logger
	 */
	private static Logger logger = Logger.getLogger(SmsFinal.class);
        private LacusSmsService service;

	/*
	 * Constance status
	 */
	public static final int SC_OK = 0;
	public static final int SC_ERROR = 1;
	public static final int SC_PDU_PARSE_ERROR = 2;

	/*
	 * Flux I/O
	 */
	private OutputStream outStream;
	private InputStream inStream;

	/*
	 * Read incoming SMS from SIM 
	 */
	public IncomingSms rx_sms = null;

	/*
	 * Config Serial Port
	 */
	private final SerialParameters parameters;
	
	/*
	 * Communication scan port 
	 */
	private CommPortIdentifier portId;
	
	/*
	 * Communication in serial port 
	 */
	private SerialPort sPort;
	
	
	/*
	 * Status comm port
	 */
	public int portStatus = OK;
	private static final Boolean portStatusLock = new Boolean(true);
	private boolean POLLING_FLAG;
	private String portStatusMsg = "";

	/*
	 * Type of response 
	 */
	private static final int OK = 1;
	private static final int WAIT = 2;
	private static final int ERROR = 3;
	private static final int WMSG = 4;
	private static final int RMSG = 5;
	private static final int ECHO = 6;
	private static final int TIMEOUT = 7;
	
	/*
	 * Buffer serial incoming event
	 */
	private final byte[] readBuffer = new byte[20000];
	private int bufferOffset = 0; // serialEvent

	/*
	 * LF CR
	 */
	private static final String lfcr = "\r\n";

	/*
	 * Default index memory is 1
	 */
	private int indexCurrentMemory = 1; 
	
	/*
	 * Default memory is "SM"
	 */
	private String currentMemory = "\"SM\""; 
	
	/**
	 * Constructor!
	 * 
	 * @param parameters
	 */
	public SmsFinal(SerialParameters parameters)
	{
            
            this.parameters = parameters;
	}
        
        public SmsFinal(SerialParameters parameters,LacusSmsService service)
	{
            this.service = service;
            this.parameters = parameters;
	}

        public LacusSmsService getService() {
            return service;
        }

        public void setService(LacusSmsService service) {
            this.service = service;
        }

	/**
	 * Initialize driver to be able to connect to serial port
	 * incase application is running from Windows
	 * as u might expect no driver initialization is required on linux
	 * ensure you initialize only once on Windows so as to avoid multiple port enumeration
	 *
	 * @return String 	"suncessful" or "failure" 
	 */
	public String initializeWinDrivers()
	{
		String drivername = "com.sun.comm.Win32Driver";
		try
		{
			CommDriver driver = (CommDriver) Class.forName(drivername).newInstance();
			driver.initialize();
			return "successful";
		}
		catch (Throwable th)
		{
			// Discard it
			return "failure";
		}
	}

	/**
	 * Return type of serial port (depend type of driver!) Driver=com.sun.comm.Win32Driver (window)
	 * Driver=gnu.io.RXTXCommDriver (all platform)
	 * 
	 * @param portType
	 * @return String with driver type
	 */
	static String getPortTypeName(int portType)
	{
		// we use on window...
		switch (portType)
		{
			case CommPortIdentifier.PORT_PARALLEL :
				return "Parallel";
			case CommPortIdentifier.PORT_SERIAL :
				return "Serial";
			default :
				return "unknown type";
		}
	}

	/**
	 * Open serial connection with COM port
	 * 
	 * @param _port
	 * @throws IOException
	 */
	public void openConnection(String _port) throws IOException
	{
		openConnection(_port, null);
	}

	/**
	 * Open serial connection with COM port
	 * 
	 * @param _port
	 * @param _pinNumber
	 * @throws IOException
	 */
	public void openConnection(String _port, String _pinNumber) throws IOException
	{
		String port = _port;
		if (_port == null) port = parameters.getPortName();
		
		// Obtain a CommPortIdentifier object for the port you want to open.
		try
		{
			portId = CommPortIdentifier.getPortIdentifier(port);
		}
		catch (NoSuchPortException e)
		{
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		
		// Open the port represented by the CommPortIdentifier object. Give
		// the open call a relatively long timeout of 30 seconds to allow
		// a different application to reliquish the port if the user
		// wants to.
		try
		{
			sPort = (SerialPort) portId.open("MobileAccess", 5000);
		}
		catch (PortInUseException e)
		{
			throw new IOException(e.getMessage());
		}
		
		// Set the parameters of the connection. If they won't set, close the
		// port before throwing an exception.
		try
		{
			setConnectionParameters();
		}
		catch (IOException e)
		{
			sPort.close();
			throw e;
		}
		
		// Open the input and output streams for the connection. If they won't
		// open, close the port before throwing an exception.
		try
		{
			outStream = sPort.getOutputStream();
			inStream = sPort.getInputStream();
		}
		catch (IOException e)
		{
			sPort.close();
			throw new IOException("Error opening i/o streams");
		}
		// Add this object as an event listener for the serial port.
		try
		{
			sPort.addEventListener(this);
		}
		catch (TooManyListenersException e)
		{
			sPort.close();
			throw new IOException("too many listeners added");
		}
		
		// Set notifyOnDataAvailable to true to allow event driven input.
		sPort.notifyOnDataAvailable(true);
		
		// Add ownership listener to allow ownership event handling.
		portId.addPortOwnershipListener(this);
		
		// init modem connection with pin number
		initializeModem(_pinNumber);
	}

	/**
	 * Initialize modem with PIN number
	 * 
	 * @param pinNumber
	 */
	private void initializeModem(String pinNumber)
	{
		atCmd("ATE0", 0, 1000); 		// turn off command echo
		atCmd("AT+CMEE=2", 0, 500); 	// verbose all messages
		atCmd("AT+CMGF=0", 0, 500); 	// set Pdu mode (default binary)
		//atCmd("AT+CNMI=0,0,0,0", 0, 500);// disable indications -direct to TE?
		
		if (pinNumber != null)
		{
			//enter pin number
			atCmd("AT+CPIN=\"" + pinNumber + "\"", 0, 1000);
			if (portStatus == ERROR)
			{
				logger.error("The pin number " + pinNumber + " is INCORRECT. Please try again.");
				// close session!
				this.close();
			}
		}
	}

	/**
	 * List open serial port
	 * 
	 * @return Array String
	 */
	public String[] listPorts()
	{
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		ArrayList portList = new ArrayList();
		String portArray[] = null;
		while (ports.hasMoreElements())
		{
			CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
			if (port.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				portList.add(port.getName());
			}
			portArray = (String[]) portList.toArray(new String[0]);
		}
		return portArray;
	}

	/**
	 * Handles ownership events. If a PORT_OWNERSHIP_REQUESTED event is received a dialog box is created asking the user
	 * if they are willing to give up the port. No action is taken on other types of ownership events.
     * @param type
	 */
        @Override
	public void ownershipChange(int type)
	{
		if (type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED)
		{
			logger.debug("PORT_OWNERSHIP_REQUESTED received : Your port has been requested by an other application...");
			
			this.close();
		}
		else if (type == CommPortOwnershipListener.PORT_OWNED)
		{
			logger.debug("PORT_OWNED received!");
		}
		else if (type == CommPortOwnershipListener.PORT_UNOWNED)
		{
			logger.debug("PORT_UNOWNED received!");
		}
	}

	/**
	 * Sets the connection parameters to the setting in the parameters object. If set fails return the parameters object
	 * to origional settings and throw exception.
	 */
	private void setConnectionParameters() throws IOException
	{
		// Save state of parameters before trying a set.
		int oldBaudRate = sPort.getBaudRate();
		int oldDatabits = sPort.getDataBits();
		int oldStopbits = sPort.getStopBits();
		int oldParity = sPort.getParity();
		int oldFlowControl = sPort.getFlowControlMode();
		
		// Set connection parameters, if set fails return parameters object
		// to original state.
		try
		{
			sPort.setSerialPortParams(parameters.getBaudRate(), parameters.getDatabits(), parameters.getStopbits(),
					parameters.getParity());
		}
		catch (UnsupportedCommOperationException e)
		{
			parameters.setBaudRate(oldBaudRate);
			parameters.setDatabits(oldDatabits);
			parameters.setStopbits(oldStopbits);
			parameters.setParity(oldParity);
			parameters.setFlowControlIn(oldFlowControl);
			parameters.setFlowControlOut(oldFlowControl);
			throw new IOException("Unsupported parameter");
		}
		// Set flow control.
		try
		{
			sPort.setFlowControlMode(parameters.getFlowControlIn() | parameters.getFlowControlOut());
			sPort.enableReceiveThreshold(1);
			sPort.enableReceiveTimeout(2000); // timeout 2s ?!
		}
		catch (UnsupportedCommOperationException e)
		{
			throw new IOException("Unsupported flow control");
		}
	}

	// To be used to send AT command via the IR/serial link to the mobile
	// device (for standard AT commands use mode=0)
        @SuppressWarnings("empty-statement")
	private synchronized int atCmd(String cmd, int mode, int timeout)
	{
		System.err.println(cmd);
		logger.debug(cmd);
		synchronized (portStatusLock)
		{
			portStatus = WAIT;
			try
			{
				// normal end of at command
				if (mode == 0) outStream.write((cmd + lfcr).getBytes()); 	
				
				// end of pdu <CTRL+Z>
				if (mode == 1) 	outStream.write((cmd + "\u001A").getBytes());
				
				// no lfcr: used for polling (just echoed back)
				if (mode == 2) outStream.write((cmd).getBytes());
			}
			catch (IOException e)
			{
				;
			}
			
			// wait for response from device
			try
			{
				// Respond time can vary for different types of AT commands and mobiles!
				portStatusLock.wait(timeout);
			}
			catch (InterruptedException e)
			{
				//...
			}
		}
		return OK;
	}

	/**
	 * Terminates IR/Serial connection to Mobile device
	 */
	public void close()
	{
		// Turn on again command echo
		atCmd("ATE1", 0, 1000);
		if (sPort != null)
		{
			try
			{
				// close the i/o streams.
				outStream.close();
				inStream.close();
			}
			catch (IOException e)
			{
				System.err.println(e);
			}
			
			// Close the port.
			sPort.close();
			
			// Remove the ownership listener.
			portId.removePortOwnershipListener(this);
		}
	}

	/**
	 * Listener Function: Data received from serial link and interpreted
	 */
	public void serialEvent(SerialPortEvent event)
	{
		switch (event.getEventType())
		{
			case SerialPortEvent.BI :
			case SerialPortEvent.OE :
			case SerialPortEvent.FE :
			case SerialPortEvent.PE :
			case SerialPortEvent.CD :
			case SerialPortEvent.CTS :
			case SerialPortEvent.DSR :
			case SerialPortEvent.RI :
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
				break;
			case SerialPortEvent.DATA_AVAILABLE :
				int n;
				try
				{
					if (POLLING_FLAG == false)
					{
						while ((n = inStream.available()) > 0)
						{
							n = inStream.read(readBuffer, bufferOffset, n);
							bufferOffset += n;
							String sbuf = new String(readBuffer, 0, bufferOffset); // bufferOffset-2
			
							// lfcr detected, line ready
							if (((readBuffer[bufferOffset - 1] == 10) && (readBuffer[bufferOffset - 2] == 13)))
							{
								// analyzing mobile response
								lineReceived(sbuf);
								if ("ERROR".equals(portStatusMsg))
								{
									System.out.println(portStatusMsg);
									logger.debug(portStatusMsg);
								}
								bufferOffset = 0;
							}
						}
						
						// delay 1/10 sec
						try { Thread.sleep(100); } catch (Exception ee) {}
					}
					else portStatus = ECHO;
				}
				catch (IOException e)
				{
					;
				}
				break; // end: case SerialPortEvent.DATA_AVAILABLE:
		}
	}

	/**
	 * Used for analyzing mobile response
	 */
	private void lineReceived(String buffer)
	{
		String response;
		StringTokenizer st = new StringTokenizer(buffer, "\r\n");
		rx_sms = null;
		synchronized (portStatusLock)
		{
			while (st.hasMoreTokens())
			{
				response = st.nextToken();
				logger.debug(response);
				System.out.println(response);
				if (response.startsWith("OK"))
				{
					portStatus = OK;
					portStatusLock.notify();
				}
				else if (response.startsWith(">"))
				{
					portStatus = WMSG;
					portStatusMsg = response;
				}
				else if (response.startsWith("ERROR"))
				{
					portStatus = ERROR;
					portStatusMsg = response;
				}
				else if (response.startsWith("+CME ERROR") || response.startsWith("+CMS ERROR"))
				{
					portStatus = ERROR;
					portStatusMsg = response;
				}
				else if (response.startsWith("07") || response.startsWith("00"))
				{
					portStatus = RMSG;
					try
					{
						rx_sms = new IncomingSms(response);
						logger.debug("SMS received: " + rx_sms.toString());
						portStatusLock.notify();
					}
					catch (PduParseException e)
					{
						logger.error("Error receiving SMS message: unable to parse PDU:\r\n" + response);
						portStatus = ERROR;
					}
				}
				// read sms response
				else if (response.startsWith("+CPMS")) // list sms SM, ME or MT
				{
					portStatus = RMSG;
					portStatusMsg = response;
				}
				else if (response.startsWith("+CMGR")) // read sms OK
				{
					portStatus = RMSG;
					portStatusMsg = response;
				}
				// read phonebook response
				else if (response.startsWith("+CPBR")) // read index phonebook OK
				{
					portStatus = RMSG;
					portStatusMsg = response;
				}
				else if (response.startsWith("+CPBS")) // read current phonebook memory OK
				{
					portStatus = RMSG;
					portStatusMsg = response;
				}
				else if (response.startsWith("+CPBF")) // read find phonebook OK
				{
					portStatus = RMSG;
					portStatusMsg = response;
				}
				// other tips
				else if (response.startsWith("ATE0")) // snoop echo
				{
					portStatus = ECHO;
					portStatusMsg = response;
				}
				else
				{
					//...
				}
			}
		}
		return;
	}

	/**
	 * Send an SMS to number (using SMS central having number "smsc_number")
     * @param number
	 */ 
	public synchronized void SendMessage(String number, String smsc_number, String msg)
	{
		// to specify specific SMS settings (character coding, sms type, ...)
		int tpPid = 0x00;
		int tpDcs = 0x00;
		if (number.startsWith("+")) number = number.substring(1);
		if (smsc_number.startsWith("+")) smsc_number = smsc_number.substring(1);
		try
		{
			OutgoingSms pdumsg = new OutgoingSms(number, smsc_number, msg, tpPid, tpDcs);
			String cmd = "AT+CMGS=" + pdumsg.length();
			String pdu = pdumsg.toString();
			
			// Delay needed -> Waiting for ">" Prompt, otherwise Error MSG!
			atCmd(cmd, 0, 500);
			
			// For some mobiles > 1000 ms!
			atCmd(pdu, 1, 3500);
		}
		catch (Exception e)
		{
			logger.error("Irregular SMS format");
		}
	}

	/**
	 * Send an SMS from index memory
	 */ 
	public synchronized void SendMessageStore(int index_mem, String number)
	{
		if (number.startsWith("+")) number = number.substring(1);
		try
		{
			// AT+CMSS= index [,da [,toda]]
			String cmd = "AT+CMSS=" + index_mem + "," + number;
			atCmd(cmd, 0, 3000);
		}
		catch (Exception e)
		{
			logger.error("Irregular SMS format");
		}
	}

	/**
	 *  Write SMS to phone/SIM memory (transmit format)
	 */ 
	public synchronized void WriteTextMessage(String number, String smsc_number, String msg)
	{
		// to specify specific SMS settings (character coding, sms type, ...)
		int tpPid = 0x00;
		int tpDcs = 0x00;
		if (number.startsWith("+")) number = number.substring(1);
		if (smsc_number.startsWith("+")) smsc_number = smsc_number.substring(1);
		try
		{
			OutgoingSms pdumsg = new OutgoingSms(number, smsc_number, msg, tpPid, tpDcs);
			String cmd = "AT+CMGW=" + pdumsg.length();
			String pdu = pdumsg.toString();
			atCmd(cmd, 0, 500);
			atCmd(pdu, 1, 1500);
		}
		catch (Exception e)
		{
			logger.error("Irregular SMS format");
		}
	}

	/**
	 *  Write SMS to phone/SIM memory (received format, write problem with some phone/SIM)
	 */ 
	public synchronized void WriteTextUnReadMessage(String number, String smsc_number, String msg)
	{
		// to specify specific SMS settings (character coding, sms type, ...)
		int tpPid = 0x00;
		int tpDcs = 0x00;
		if (number.startsWith("+")) number = number.substring(1);
		if (smsc_number.startsWith("+")) smsc_number = smsc_number.substring(1);
		try
		{
			OutgoingSms pdumsg = new OutgoingSms(number, smsc_number, msg, tpPid, tpDcs);
			Calendar time = new GregorianCalendar();
			//time.set(Calendar.MINUTE, -5);
			pdumsg.transform_to_received_SMS(time);
			String pdu_r = pdumsg.toString();
			
			// Important: PDU of SMS to be stored in RECEIVED UNREAD (,0) folder
			// must follow received SMS PDU format!
			String cmd_r = "AT+CMGW=" + pdumsg.length() + ",0";
			atCmd(cmd_r, 0, 500);
			atCmd(pdu_r, 1, 1500);
		}
		catch (Exception e)
		{
			logger.error("Irregular SMS format");
		}
	}

	/**
	 * Read SMS from Mobile/SIM storage having index i
	 */ 
	public synchronized void ReadSMS(int i)
	{
		atCmd("AT+CMGR=" + i, 0, 1000); // Receive SMS
	}

	/**
	 * Delete SMS from Mobile/SIM storage having index i
	 */ 
	public synchronized void DeleteSMS(int i)
	{
		atCmd("AT+CMGD=" + i, 0, 1000); // Delete SMS
	}

	/**
	 *  get SMS from Mobile/SIM storage
	 */ 
	public synchronized IncomingSms getRxMS()
	{
		return rx_sms;
	}
	
	/**
	 * get error message & other...
	 */ 
	public synchronized String getPortStatusMsg()
	{
		return portStatusMsg;
	}

	/**
	 * get port status...
	 */ 
	public synchronized int getPortStatus()
	{
		return portStatus;
	}

	/**
	 * Get current size list of SMS strored in particulary memory example = \"ME\",\"SM\",\"MT\"
	 *  "ME": ME phonebook 
	 *  "SM": SIM phonebook 
	 *  "MT": combined ME and SIM phonebook
	 * 
	 * @return int Size list of sms in specific memory
	 */
	public synchronized int getSizeListSMS()
	{
		atCmd("AT+CPMS=" + currentMemory, 0, 500);
		
		// receive error message ?
		if (portStatus == ERROR) return 1; // index list start 1 not 0!
		
		// parse response
		try
		{
			String str = portStatusMsg.substring(portStatusMsg.indexOf(':') + 1, portStatusMsg.length());
			String[] str2 = getArrayString(str);
			if (str2.length >= 2) str = str2[1]; // size used
			str = str.trim();
			
			// index current memory
			indexCurrentMemory = 1; // 1 for Sms
			
			return Integer.parseInt(str);
		}
		catch (java.lang.StringIndexOutOfBoundsException e)
		{
			return 1;
		}
		catch (NumberFormatException ee)
		{
			return 1;
		}
	}
	
	

	/**
	 * Get Size list of PhoneBook strored in particulary memory :
	 * 
	 *  Commonly available phonebooks are: 
	 *	FD : SIM fixdialling-phonebook 
	 *  LD : SIM last-dialling-phonebook 
	 *  ME : ME phonebook 						(the indexes begin from 750 to 999)
	 *  MT : combined ME and SIM phonebook  	(the indexes begin from 1 to 80) 
	 *	SM : SIM phonebook 						(the indexes begin from 1 to 80) 
	 *  TA : TA phonebook
	 * 
	 * @return int Size list of Phone Book in specific memory
	 */
	public synchronized int getSizeListPhoneBook()
	{
		// Check which indexes are available for reading in the selected memory
		atCmd("AT+CPBR=?", 0, 500);
		
		// receive error message ?
		if (portStatus == ERROR) return 1; // index list start 1 not 0!
		
		// parse response
		try
		{
			String str1 = portStatusMsg.substring(portStatusMsg.indexOf('(')+1, portStatusMsg.indexOf('-'));
			str1 = str1.trim(); // System.out.println("str1 : " + str1);
			
			// index current memory
			indexCurrentMemory = Integer.parseInt(str1);
			
			String str2 = portStatusMsg.substring(portStatusMsg.indexOf('-')+1, portStatusMsg.indexOf(')'));
			str2 = str2.trim(); // System.out.println("str2 : "+ str2);
			
			return (Integer.parseInt(str2) - Integer.parseInt(str1));
		}
		catch (java.lang.StringIndexOutOfBoundsException e)
		{
			return 1;
		}
		catch (NumberFormatException ee)
		{
			return 1;
		}
	}
	
	
	
	/**
	 * Read PhoneBook Mobile/SIM storage having index i
	 */ 
	public synchronized String getPhoneBook(int i)
	{
		atCmd("AT+CPBR=" + i, 0, 1000); // get PhoneBook index i  
		
		String book = "";
		
		// receive error message ?
		if (portStatus == ERROR) return book; 
		
		// parse reponse... +CPBR: 750,"33600000000",145,"TOTO"
		try
		{
			book = portStatusMsg.substring(portStatusMsg.indexOf(':')+1, portStatusMsg.length());
			book = book.trim();
		}
		catch (java.lang.StringIndexOutOfBoundsException e)
		{
			return "";
		}
		catch (NumberFormatException ee)
		{
			return "";
		}
		
		// return string of phone book 
		return book;
	}
	
	
	
	/**
	 * Search PhoneBook from Mobile/SIM storage, example : "A"
	 */ 
	public synchronized String SearchPhoneBook(String search_name)
	{
		atCmd("AT+CPBF="+"\""+search_name+"\"", 0, 1000); // Search PhoneBook
		
		String book = "";
		
		// receive error message ?
		if (portStatus == ERROR) return book; 
		
		// parse reponse... +CPBF: 750,"33600000000",145,"TOTO"
		try
		{
			book = portStatusMsg.substring(portStatusMsg.indexOf(':')+1, portStatusMsg.length());
			book = book.trim();
		}
		catch (java.lang.StringIndexOutOfBoundsException e)
		{
			return "";
		}
		catch (NumberFormatException ee)
		{
			return "";
		}
		
		// return string of phone book 
		return book;
	}
	
	
	
	/**
	 *  Write PhoneBook to phone/SIM memory (received format, write problem with some phone/SIM)
	 */ 
	public synchronized boolean WritePhoneBookMessage(int index, String number, int type, String name)
	{
		//if (number.startsWith("+")) number = number.substring(1);
	
		// Write phone number and name in phonebook index
		String cmd_r = "AT+CPBW="+ index+ "," +"\""+ number +"\""+ ","+type+ "," +"\""+name+"\",0";
	
		// Confirm what we have just written
		String cmd_c = "AT+CPBR="+ index;
	
		try
		{
			// send write command phone book
			atCmd(cmd_r, 0, 500);
			
			// receive error message ?
			if (portStatus == ERROR) return false; 
			
			// confirm write command phone book
			atCmd(cmd_c, 0, 500);
			
			// receive error message ?
			if (portStatus == ERROR) return false; 
			
			// parse reponse... +CPBR: 750,"33600000000",145,"TOTO"
			try
			{
				String book = portStatusMsg.substring(portStatusMsg.indexOf(':')+1, portStatusMsg.length());
				book = book.trim();
		
				// same request ?
				if (book.equals(cmd_r)) return true;
			}
			catch (java.lang.StringIndexOutOfBoundsException e)
			{
				return false;
			}
			catch (NumberFormatException ee)
			{
				return false;
			}
		}
		catch (Exception e)
		{
			logger.error("Irregular PhoneBook format");
		}
		
		return false;
	}

	
	/**
	 * Delete PhoneBook from Mobile/SIM storage having index i
	 */ 
	public synchronized void DeletePhoneBook(int i)
	{
		atCmd("AT+CPBW=" + i, 0, 1000); // Delete Phone Book index
	}
	
	
	/**
	 * Method getPhoneBookMemory
	 * 
	 * @return String	Name of current phonebook memory
	 */
	public synchronized String getPhoneBookMemory()
	{
		String memory ="";
		
		// ask memory
		atCmd("AT+CPBS?", 0, 500);
		
		// receive error message ?
		if (portStatus == ERROR) return memory; 
		
		try
		{
			memory = portStatusMsg.substring(portStatusMsg.indexOf('"'), portStatusMsg.indexOf('"')+4);
			memory = memory.trim();
			
			currentMemory = memory;
		}
		catch (java.lang.StringIndexOutOfBoundsException e)
		{
			return "";
		}
		catch (NumberFormatException ee)
		{
			return "";
		}
		
		return memory;
	}
	
	/**
	 * Method getSmsMemory
	 * 
	 * @return String	Name of current sms memory
	 */
	public synchronized String getSmsMemory()
	{
		String memory ="";
		
		// ask memory
		atCmd("AT+CPMS?", 0, 500);
		
		// receive error message ?
		if (portStatus == ERROR) return memory; 
		
		try
		{
			memory = portStatusMsg.substring(portStatusMsg.indexOf('"'), portStatusMsg.indexOf('"')+4);
			memory = memory.trim();
			
			currentMemory = memory;
		}
		catch (java.lang.StringIndexOutOfBoundsException e)
		{
			return "";
		}
		catch (NumberFormatException ee)
		{
			return "";
		}
		
		return memory;
	}
	
			
			
	/**
	 * Init current phone book memory
	 * 
	 * @param typeStore
	 * @return boolean		true if memory is initialized
	 */
	public synchronized boolean initializePhoneBookMemory(String typeStore)
	{
		boolean initOk = true;
		
		// inint memory
		atCmd("AT+CPBS=" + typeStore, 0, 500);
		
		// receive error message ?
		if (portStatus == ERROR) return false; 
		
		// set current type memory
		currentMemory = typeStore;
		
		return initOk;
	}
	
	/**
	 * Init current sms memory
	 * 
	 * @param typeStore
	 * @return boolean		true if memory is initialized
	 */
	public synchronized boolean initializeSmsMemory(String typeStore)
	{
		boolean initOk = true;
		
		// inint memory
		atCmd("AT+CPMS=" + typeStore, 0, 500);
		
		// receive error message ?
		if (portStatus == ERROR) return false; 
		
		// set current type memory
		currentMemory = typeStore;
		
		return initOk;
	}
	
	/**
	 * Get index of the current memory
	 * 
	 * @return int
	 */
	public int getIndexMemory()
	{
		return this.indexCurrentMemory;
	}
	
	/**
	 * Method getArrayString
	 * 
	 * @param response
	 * @return String[]
	 */
	private static String[] getArrayString(String _str)
	{
		// test _str
		if (_str == null) return new String[0];
		StringTokenizer b_stk = new StringTokenizer(_str, ",");
		String[] str = new String[b_stk.countTokens()];
		int count = 0;
		while (b_stk.hasMoreTokens()) str[count++] = b_stk.nextToken();
		return str;
	}
        
        //public boolean verifierConnexion()
        
        public boolean envoieSMS(String numc) throws IOException{
                SerialParameters params = new SerialParameters();
		params.setPortName("COM6"); // default COM1
		params.setBaudRate(115200); // default 115200
		params.setFlowControlIn(SerialPort.FLOWCONTROL_NONE); // default none flowcontrol
		params.setFlowControlOut(SerialPort.FLOWCONTROL_NONE); // default none flowcontrol
		params.setDatabits(SerialPort.DATABITS_8); // default data bits 8
		params.setStopbits(SerialPort.STOPBITS_1); // default stop bits 1
		params.setParity(SerialPort.PARITY_NONE); // default none parity bits 1
		// object sms client
		SmsFinal sms = new SmsFinal(params);
                String[] portArray = sms.listPorts();
                if(portArray.length > 0){
                    while(true){
                        sms.openConnection("COM6");
                        List<BkEve> list = service.getBkEveBySendParam(false);
                        for(BkEve eve : list){
                            MessageFormat mf = new MessageFormat();
                            mf = service.getFormatByBkOpe(eve.getOpe(),eve.getCli().getLangue());
                            String text = Utils.remplacerVariable(eve.getCli(), eve.getOpe(), eve, mf);
                            sms.SendMessage(Long.toString(eve.getCli().getPhone()), numc, text); 
                            Message message = new Message();
                            message.setTitle(eve.getOpe().getLib());
                            message.setContent(text);
                            message.setBkEve(eve);
                            message.setSendDate(new Date());                           
                        }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }   
              }
            return false;
        }
}
	
	
