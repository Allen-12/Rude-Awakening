package com.bravo_one.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmOff extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Spinner spinnerOne;
    String answerOne;
    Spinner spinnerTwo;
    String answerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_off);

        spinnerOne = findViewById(R.id.answer_1);
        ArrayAdapter<CharSequence> adapterOne = ArrayAdapter.createFromResource(this,R.array.answersOne,android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerOne != null)
        {
            spinnerOne.setOnItemSelectedListener(this);
            spinnerOne.setAdapter(adapterOne);
        }

        spinnerTwo = findViewById(R.id.answer_2);
        ArrayAdapter<CharSequence> adapterTwo = ArrayAdapter.createFromResource(this,R.array.answersTwo,android.R.layout.simple_spinner_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerTwo != null)
        {
            spinnerTwo.setOnItemSelectedListener(this);
            spinnerTwo.setAdapter(adapterTwo);
        }
    }

    public void checkAnswers(View view)
    {
        if (answerOne.equals("Bamako") && answerTwo.equals("Kabul"))
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"WRONG ANSWER!!! WAKE UP",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        answerOne = spinnerOne.getSelectedItem().toString();
        answerTwo = spinnerTwo.getSelectedItem().toString();
        ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
//        Toast.makeText(this,answerTwo,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
