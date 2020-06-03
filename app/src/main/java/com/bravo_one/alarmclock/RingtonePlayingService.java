package com.bravo_one.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class RingtonePlayingService extends Service
{
    MediaPlayer mediaSong;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent.getBooleanExtra("stop", false)) {
            stopForeground(true);
            stopSelf();
        }
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

//      Fetch the extra string for the alarm off/alarm on values
        String state = intent.getStringExtra("Extra");
//      Fetch the extra long for the audio chosen
        int audioChoice = (int) intent.getLongExtra("audioChoice",0);

//      If the  state variable is null, don't go through the if statement
        assert state != null;
        Log.i("Ringtone state",state);
        Log.i("Audio Choice Ringtone", String.valueOf(audioChoice));

//      Setup an intent that goes to the main activity when the Notification is clicked
        Intent intentAlarmOff = new Intent(getApplicationContext(),AlarmOff.class);
//      Setup a pending intent which will be used to open the main activity once the notification is clicked
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intentAlarmOff,0);
//      Setup the Notification Service
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//      Create the notification and its parameters
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Your Alarm is Going Off!!")
                .setSmallIcon(R.drawable.ic_alarm_on_black_24dp)
                .setContentText("Click to turn off alarm")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

//      This converts the the extra strings from the intent to start IDs
        switch (state)
        {
            case "Alarm On":
                startId = 1;
                break;
            case "Alarm Off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

//      If there is no music playing and set alarm is pressed, music should start playing
        if (!this.isRunning && startId == 1)
        {
            Log.i("There is no music","And you want start");
            this.isRunning = true;
            this.startId = 0;

    //      Call the notification to be shown
            assert notificationManager != null;
//            notificationManager.notify(0,notification);
            startForeground(1,notification);

//          Start the ringtone randomly
            int minimumNumber = 1;
            int maximumNumber = 5;
            Random randomNumber = new Random();
            audioChoice = randomNumber.nextInt(maximumNumber + minimumNumber);
            Log.i("Random Number:", String.valueOf(audioChoice));

            if (audioChoice == 1)
            {
                mediaSong = MediaPlayer.create(this, R.raw.david_goggins_wake_up);
                mediaSong.start();
            }
            else if (audioChoice == 2)
            {
                mediaSong = MediaPlayer.create(this, R.raw.david_goggins_never_snooze);
                mediaSong.start();
            }
            else if (audioChoice == 3)
            {
                mediaSong = MediaPlayer.create(this, R.raw.the_ultimate_motivation_clip);
                mediaSong.start();
            }
            else if (audioChoice == 4)
            {
                mediaSong = MediaPlayer.create(this, R.raw.wake_up);
                mediaSong.start();
            }
            else if (audioChoice == 5)
            {
                mediaSong = MediaPlayer.create(this, R.raw.get_out_of_bed);
                mediaSong.start();
            }
            else
            {
                mediaSong = MediaPlayer.create(this, R.raw.alone);
                mediaSong.start();
            }
        }
        else if (this.isRunning && startId == 0)
        {
            Log.i("There is music","And you want end");
//          Stop the  ringtone
            mediaSong.stop();
            mediaSong.reset();
            this.isRunning = false;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0)
        {
            Log.i("There is no music","And you want end");
            this.isRunning = true;
            this.startId = 0;
        }
        else if (this.isRunning && startId == 1)
        {
            Log.i("There is music","And you want start");

            this.isRunning = true;
            this.startId = 1;
        }
        else
        {
            Log.i("Last Case Scenario","Somehow you reached this");
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        // Tell the user we stopped.
        Log.i("On Destroy called","Music stopped");
        Toast.makeText(this, "Sound Off", Toast.LENGTH_SHORT).show();
        mediaSong.stop();
        super.onDestroy();
        this.isRunning = false;
    }
}
