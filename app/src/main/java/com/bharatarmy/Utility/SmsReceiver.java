package com.bharatarmy.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.bharatarmy.Interfaces.SmsListener;

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListener mListener;
    Boolean b;
    String abcd,xyz;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            // b=sender.endsWith("WNRCRP");  //Just to fetch otp sent from WNRCRP
            String messageBody = smsMessage.getMessageBody();
            abcd=messageBody.replaceAll("[^0-9]","");   // here abcd contains otp

//            if(b==true) {
//                mListener.messageReceived(abcd);  // attach value to interface
//            }
//            else
//            {
//            }
        }
    }

//     if (intent.getAction().equals(
//                    "android.provider.Telephony.SMS_RECEIVED")) {
//        Bundle bundle = intent.getExtras(); // ---get the SMS message
//        // passed in---
//        SmsMessage[] msgs = null;
//        // String msg_from;
//        if (bundle != null) {
//            // ---retrieve the SMS message received---
//            try {
//                Object[] pdus = (Object[]) bundle.get("pdus");
//                msgs = new SmsMessage[pdus.length];
//                for (int i = 0; i < msgs.length; i++) {
//                    msgs[i] = SmsMessage
//                            .createFromPdu((byte[]) pdus[i]);
//                    // msg_from = msgs[i].getOriginatingAddress();
//                    String msgBody = msgs[i].getMessageBody();
//                    // do your stuff
//                }
//            } catch (Exception e) {
//                // Log.d("Exception caught",e.getMessage());
//            }
//        }
//    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
