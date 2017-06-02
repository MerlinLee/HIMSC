package cn.edu.jlu.limf.himsc.controller.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edu.jlu.limf.himsc.controller.services.AlarmService;

/**
 * Created by merlin on 17-5-6.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);
    }

}
