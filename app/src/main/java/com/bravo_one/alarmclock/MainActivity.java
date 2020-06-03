package com.bravo_one.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    TimePicker timePicker;
    AlarmManager alarmManager;
    TextView updateText;
    Button setAlarm;
    Button turnOffAlarm;
    Calendar calendar = null;
    PendingIntent pendingIntent;
    Intent intent;
    long audioChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Initialize Alarm Manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = findViewById(R.id.time_picker);
        updateText = findViewById(R.id.update_text);
        setAlarm = findViewById(R.id.set_alarm);
        turnOffAlarm = findViewById(R.id.turn_off_alarm);

//        Create an instance of a calendar
        calendar = Calendar.getInstance();
        intent = new Intent(this,AlarmReceiver.class);

        Spinner spinner = findViewById(R.id.audio_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.audios, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @SuppressLint("NewApi")
    public void setAlarm(View view)
    {
        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
        calendar.set(Calendar.MINUTE,timePicker.getMinute());

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String timeSet;
        String minuteString = Integer.toString(minute);

        if (minute < 10)
        {
            minuteString = "0" + minute;
        }

        if (hour >= 0 && hour < 12)
        {
            timeSet = hour + ":" + minuteString + " AM";
        }
        else
        {
            if (hour == 12)
            {
                timeSet = hour + ":" + minuteString + " PM";
            }
            else
            {
                hour = hour - 12;
                timeSet = hour + ":" + minuteString + " PM";
            }
        }

        updateText.setText("Alarm Set to " + timeSet);

//        Put in extra string into intent to tell the receiver that the set alarm button has been pressed
        intent.putExtra("Extra", "Alarm On");
//        Put in a long value into intent. Shows that the user wants a certain value from the spinner
        intent.putExtra("audioChoice",audioChosen);

        pendingIntent = PendingIntent.getBroadcast(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }


    public void turnOffAlarm(View view)
    {
        updateText.setText(R.string.update_text_2);
//      Cancel the alarm
        alarmManager.cancel(pendingIntent);

//      Tell the receiver that alarm off button has been pressed
        intent.putExtra("Extra", "Alarm Off");
//      Put in a long value into intent to prevent crashes in the event of a Null Pointer Exception. Shows that the user wants a certain value from the spinner
        intent.putExtra("audioChoice",audioChosen);
//      Stop the ringtone
        sendBroadcast(intent);
        Intent stopMusicService = new Intent(this,RingtonePlayingService.class);
        stopMusicService.putExtra("stop", true);
        stopService(stopMusicService);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
//      Output whatever ID the user has selected
        audioChosen = id;
//        Toast.makeText(this,"Spinner Item Selected is "+id,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
