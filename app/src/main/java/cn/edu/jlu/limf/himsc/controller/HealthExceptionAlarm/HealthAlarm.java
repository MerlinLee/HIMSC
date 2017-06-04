package cn.edu.jlu.limf.himsc.controller.HealthExceptionAlarm;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by merlin on 17-5-11.
 */

public class HealthAlarm {
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

    public HealthAlarm() {
    }
    public HealthAlarm(String phnoeNumber_insert){
        setPhoneNumber(phnoeNumber_insert);
    }

    private void sendSMS(String phoneNumber_, String message) {
        Log.d("Merlin", "sendSMS: "+"发送成功");
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber_, null, message, null, null);
    }

    public String sendSMS_status(String message){
        try {
            sendSMS(getPhoneNumber(), message);
            return "SUCCESS";
        }catch (Exception e){
            Log.d("Merlin", "sendSMS_status: "+e.toString());
            return "ERROR";
        }
    }
}
