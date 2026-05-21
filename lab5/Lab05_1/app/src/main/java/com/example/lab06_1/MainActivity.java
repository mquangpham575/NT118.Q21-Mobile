package com.example.lab06_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    private PowerStateChangeReceiver powerReceiver;

    
    public void processReceive(Context context, Intent intent){

        Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_LONG).show();
        TextView tvContent = (TextView) findViewById(R.id.tv_content);

        final String SMS_EXTRA =  "pdus";
        Bundle bundle = intent.getExtras();

        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
        String sms = "";

        SmsMessage smsMsg;
        System.out.println("999999999999" + messages);
        for (int i=0; i < messages.length; i++) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                String format = bundle.getString("format");
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i], format);
                System.out.println(smsMsg);
            }
            else{

                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);
            }
            
            String msgBody = smsMsg.getMessageBody();

            
            String address = smsMsg.getDisplayOriginatingAddress();
            sms += address +  ":\n" + msgBody + "\n";

        }
        
        tvContent.setText(sms);
    }
    private void initBroadcastReceiver() {

        
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        
        broadcastReceiver = new BroadcastReceiver() {
            
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        
        if (broadcastReceiver == null) initBroadcastReceiver();
        
        registerReceiver(broadcastReceiver, filter);

        
        powerReceiver = new PowerStateChangeReceiver();
        IntentFilter powerFilter = new IntentFilter();
        powerFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        powerFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(powerReceiver, powerFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        
        unregisterReceiver(broadcastReceiver);
        if (powerReceiver != null) {
            unregisterReceiver(powerReceiver);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBroadcastReceiver();
    }
}