package com.bravo_one.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class AlarmReceiver extends BroadcastReceiver
{
    @RequiresApi(api = Build.VERSION_CODES.O)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
    //      Start the ringtone service
            context.startForegroundService(serviceIntent);
        }
        else
        {
    //      Start the ringtone service
            context.startService(serviceIntent);
        }
    }
}
