package com.bravo_one.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
//      Shows that the receiver has been reached successfully
        Log.i("Yay","We are in the receiver");

//      Tells the app whether set alarm button was pressed or turn off alarm was pressed
        String stringExtra = intent.getStringExtra("Extra");
        assert stringExtra != null;
        Log.i("Key: ",stringExtra);

//      Pass the extra string from Main Activity to the Ringtone Playing Service
        Long audioChoice = intent.getLongExtra("audioChoice",1000);
        Log.i("Audio chosen: ", String.valueOf(audioChoice));

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("Extra",stringExtra);
        serviceIntent.putExtra("audioChoice",audioChoice);

//      Start the ringtone service
        context.startService(serviceIntent);
    }
}
