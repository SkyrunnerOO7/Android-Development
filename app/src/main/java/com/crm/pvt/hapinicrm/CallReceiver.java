package com.crm.pvt.hapinicrm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
//1 more comment

            String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            int flag  =0;
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                //showToast(context,"Call started...");
            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                showToast(context,"Idle Call ended...");


                Intent i = new Intent(context, callingFeedbackActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               i.putExtra("message", "message.getMessageBody()");
               if(flag == 0)
                context.startActivity(i);

            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                //showToast(context,"Incoming call...");
                flag = 1;

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    void showToast(Context context,String message){
        Toast toast= Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}

