
package com.abouna.lacussms.sms;


import java.util.Calendar;

/**
 * 
 * Classe representant un SMS sortant
 * (Based upon Java source code written by Stefan Moscibroda, 2001) 
 *
 * @author Franck Andriano 2007
 * @version 1.0
 */
public class OutgoingSms 
{
    public final int SMS_MSG_ENCODING_7BIT = 0;

    private int    smscAddressLength= 0x00;
    private int    smscAddressType = 0x91;
    public String smscAddress;
    private String smscAddressEnc;
    private int    smsSubmitCode = 0x11; // 0x11;
    private int    tpMsgRef = 0;
    private int    recipientAddressLength;
    private int    recipientAddressType = 0x91;
    public String recipientAddress;
    private String recipientAddressEnc;
    public int    tpPid;
    public int    tpDcs;
    private int    tpValidity = 0xaa;
    private int    tpUdl;      // message length.
    private String tpUd;       // user message (as sent).
    public String msg;        // user message (unencoded).

    private String pdu;

    public OutgoingSms(String recipientAddress, String smscAddress, String msg, int tpPid, int tpDcs) 
    {
    	this.recipientAddress = recipientAddress;
        this.smscAddress= smscAddress;
        this.msg = msg;
        this.tpPid = tpPid;
        this.tpDcs = tpDcs;
        StringBuffer sb = new StringBuffer(320);
        smscAddressLength = smscAddress.length();

        // Internal smsc Adress settings taken from mobile
        // if other add smscAddress and smscAddressType to sb.
        if(smscAddressLength == 0) 
        {
        	sb.append("00");   // Optional, some mobile don't work if added
        }
        else  
        {

            // add smscAddress and smscAddressType to sb.
            sb.append(PduCodec.toHexString(0x07));

            if( (smscAddressLength%2) == 1 )
            	smscAddressEnc = PduCodec.swapDigits(smscAddress+"F");
            else
            	smscAddressEnc = PduCodec.swapDigits(smscAddress);

            sb.append(PduCodec.toHexString(smscAddressType));
            sb.append(smscAddressEnc);
        }

        sb.append(PduCodec.toHexString(smsSubmitCode)); 
		sb.append(PduCodec.toHexString(tpMsgRef)); //tpMsgRef
	
		// recipientAddress
		recipientAddressLength = recipientAddress.length();
		if ((recipientAddress.length() % 2) == 1 )
		    recipientAddressEnc = PduCodec.swapDigits(recipientAddress+"F");
		else
		    recipientAddressEnc = PduCodec.swapDigits(recipientAddress);
	
		sb.append(PduCodec.toHexString(recipientAddressLength));
		sb.append(PduCodec.toHexString(recipientAddressType));
		sb.append(recipientAddressEnc);
		sb.append(PduCodec.toHexString(tpPid));
		sb.append(PduCodec.toHexString(tpDcs));
		sb.append(PduCodec.toHexString(tpValidity));

		// encode message and calculate message length.
	    if ((tpDcs & 4) == 0) 
	    {
		   tpUd = PduCodec.sevenBitEncode(msg);
	       tpUdl = msg.length();
	    }
	    else 
	    {
	    	tpUd = msg;
	    	tpUdl = msg.length()/2;
	    }
	    
		sb.append(PduCodec.toHexString(tpUdl));
		sb.append(tpUd);
		
		pdu = sb.toString();
    }


    public String toString() 
    {
      return pdu.toUpperCase();
    }

	public int length() 
	{
		if ((pdu.substring(0,2)).equals("00")) return (pdu.length()-2)/2;
		else return (pdu.length()-16)/2;
    }


    // Transforms an outgoing to an incoming pdu
    // contains no SMCS
	public void transform_to_received_SMS(Calendar time) 
	{

		int length;
		length=pdu.length();

		// if no SMCS specified, otherwise adaption needed
		int offset1;
		String beginning;
		
		if ((pdu.substring(0,2)).equals("00")) 
		{
			// if transmit without smsc adress enter dummy some smsc address
			beginning="07911497949900F0";
			offset1=6;
		}
		else 
		{
		   beginning=pdu.substring(0,16);
		   offset1=20;
		}
		
		String addressLenStr = pdu.substring(offset1, offset1+2);
		int senderAddressLength = Integer.parseInt(addressLenStr, 16);
		
		int offset2= offset1 + 4 + senderAddressLength+senderAddressLength%2;
		
		String pdu_r=beginning+"04"+pdu.substring(offset1, offset2+4);
		
		//System.out.println(SmsPduCodec.TimeStampEncode(time));
		pdu_r=pdu_r+ PduCodec.TimeStampEncode(time) +pdu.substring(offset2+6,length);
		
		pdu=pdu_r;
	}

}
