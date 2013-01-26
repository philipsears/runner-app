package com.philipsears.runner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.DecimalFormat;

public class RunnerActivity extends Activity implements OnClickListener
{

    private static final String TAG = "runnerTask";
    private EditText hourTimeText;
    private EditText minTimeText;
    private EditText secTimeText;
    private TimeData timeData;
    private EditText distanceText;
    private double distanceData;
    private EditText hourPaceText;
    private EditText minPaceText;
    private EditText secPaceText;
    private TimeData paceData;
    private RunnerCalculator calculator;
    private boolean useMiles = true;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Set up click listeners for all the buttons
        View distanceButton = findViewById(R.id.distance_button);
        distanceButton.setOnClickListener(this);
        View resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        View paceButton = findViewById(R.id.pace_button);
        paceButton.setOnClickListener(this);
        View timeButton = findViewById(R.id.time_button);
        timeButton.setOnClickListener(this);
        initControls();
    }
    
    private void initControls() {
        hourTimeText = (EditText)findViewById(R.id.hour_time);
        minTimeText = (EditText)findViewById(R.id.minute_time);
        secTimeText = (EditText)findViewById(R.id.seconds_time);
        distanceText = (EditText)findViewById(R.id.distance_input);
        hourPaceText = (EditText)findViewById(R.id.hr_pace);
        minPaceText = (EditText)findViewById(R.id.min_pace);
        secPaceText = (EditText)findViewById(R.id.sec_pace);
    }
    
    private void initDistanceType() {
        Spinner distSpinner = (Spinner) findViewById(R.id.distance_spinner);
        String spinnerText = distSpinner.getSelectedItem().toString();
        Log.d(TAG, "Distance spinner text is "+spinnerText);
        useMiles = spinnerText == null || spinnerText.equals("miles");
    }

    private void parsePace() {
        paceData = new TimeData(Integer.parseInt(hourPaceText.getText().toString()),
                Integer.parseInt(minPaceText.getText().toString()),
                Double.parseDouble(secPaceText.getText().toString()));
    }

    private void parseTime() {
        timeData = new TimeData(Integer.parseInt(hourTimeText.getText().toString()),
                        Integer.parseInt(minTimeText.getText().toString()),
                        Double.parseDouble(secTimeText.getText().toString()));
    }

    private void parseDistance() {
        distanceData = Double.parseDouble(distanceText.getText().toString());
    }

    public void onClick(View v) {
        Log.d(TAG, "onClick times");
        DecimalFormat df = new DecimalFormat("##.##");
        
        switch (v.getId()) {
            case R.id.distance_button:
                initDistanceType();
                
                Log.d(TAG,"Distance Button onClick ");
                if (!validateRequiredTime()) break;
                parseTime();
                Log.d(TAG, "Parsed time :" + timeData.getHours() + ":" + timeData.getMinutes() + ":" + timeData.getSeconds());
                
                if (!validateRequiredPace()) break;
                parsePace();
                Log.d(TAG, "Parsed data :"+paceData.getHours()+":"+paceData.getMinutes()+":"+paceData.getSeconds());

                calculator = new RunnerCalculator(timeData,paceData,0, useMiles);
                distanceData = calculator.calculateDistance();
                Log.d(TAG,"Calculated distance "+distanceData);
                
                distanceText.setText("" + df.format(distanceData));

                break;

            case R.id.pace_button:
                initDistanceType();
                
                if (!validateRequiredTime()) break;
                Log.d(TAG, "Pace Button onClick");
                parseTime();
                Log.d(TAG, "Parsed time :" + timeData.getHours() + ":" + timeData.getMinutes() + ":" + timeData.getSeconds());
                if (!validateRequiredDistance()) break;
                parseDistance();
                Log.d(TAG, "Parsed distance :" + distanceData);
                calculator = new RunnerCalculator(timeData,new TimeData(0.0),distanceData, useMiles);
                paceData = calculator.calculatePace();
                Log.d(TAG,"Calculated pace "+paceData.getHours()+":"+paceData.getMinutes()+":"+paceData.getSeconds());
                hourPaceText.setText("" + paceData.getHours());
                minPaceText.setText("" +paceData.getMinutes());
                secPaceText.setText("" +df.format(paceData.getSeconds())); 
                break;

            case R.id.time_button:
                Log.d(TAG, "Time Button onClick");

                initDistanceType();
                
                if (!validateRequiredDistance()) break;
                parseDistance();
                Log.d(TAG, "Parsed distance :" + distanceData);
                
                if (!validateRequiredPace()) break;
                parsePace();
                Log.d(TAG, "Parsed data :"+paceData.getHours()+":"+paceData.getMinutes()+":"+paceData.getSeconds());
              
                calculator = new RunnerCalculator(new TimeData(0.0),paceData,distanceData, useMiles);
                timeData = calculator.calculateTime();

                Log.d(TAG,"Calculated time "+timeData.getHours()+":"+timeData.getMinutes()+":"+timeData.getSeconds());
                hourTimeText.setText("" + timeData.getHours());
                minTimeText.setText("" +timeData.getMinutes());
                secTimeText.setText("" +df.format(timeData.getSeconds()));
                break;

            case R.id.reset_button:
                Log.d(TAG, "Reset Button onClick");

                hourTimeText.setText(null);
                minTimeText.setText(null);
                secTimeText.setText(null);
                distanceText.setText(null);
                hourPaceText.setText(null);
                minPaceText.setText(null);
                secPaceText.setText(null);
                break;
        }
    }

    private boolean validateRequiredTime() {
        if( hourTimeText.getText().length() == 0 ) {
            hourTimeText.setError( "enter hours" );
            Log.d(TAG,"The hour time is required.");
            return false;
        }
        if( minTimeText.getText().length() == 0 ) {
            minTimeText.setError( "enter mins" );
            Log.d(TAG,"The hour time is required.");
            return false;
        }
        if( secTimeText.getText().length() == 0 ) {
            secTimeText.setError( "enter secs" );
            Log.d(TAG,"The second time is required.");
            return false;
        }
        return true;
    }
    
    private boolean validateRequiredDistance() {
        if( distanceText.getText().length() == 0 ) {
            distanceText.setError( "enter distance" );
            Log.d(TAG,"The distance is required.");
            return false;
        }
        return true;
    }
    
    private boolean validateRequiredPace() {
        if( hourPaceText.getText().length() == 0 ) {
            hourPaceText.setError( "enter hours" );
            Log.d(TAG,"The hour pace is required.");
            return false;
        }
        if (minPaceText.getText().length() == 0) {
            minPaceText.setError("enter mins");
            Log.d(TAG,"The minute pace is required.");
            return false;
        }
        if (secPaceText.getText().length() == 0) {
            secPaceText.setError("enter secs");
            Log.d(TAG,"The second pace is required.");
            return false;
        }
        return true;
    }
}


