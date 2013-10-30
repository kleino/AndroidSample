package com.example.FoodPlanner;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.FoodPlanner.food.FoodItem;
import com.example.FoodPlanner.food.WordList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoiceRecTest extends Activity {
    private static final int REQUEST_CODE = 1234;
    Button speakButton;
    private ListView resultList;
    private WordList wordList;
    private static final String TAG = "VoiceRecTest";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordList = new WordList(this.getAssets());
        setContentView(R.layout.voice_activity);

        speakButton = (Button) findViewById(R.id.speakButton);
        resultList = (ListView) findViewById(R.id.list);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            speakButton.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Recognizer Not Found",
                    1000).show();
        }
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "AndroidBite Voice Recognition...");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "de-de");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


            Log.d(TAG, listToString(matches));
            ArrayList<String> results = new ArrayList<String>();
            String sentence = matches.get(0).toLowerCase();
            for (FoodItem fi : wordList.getFoodList()) {
                if (sentence.contains(fi.getName().toLowerCase())) {
                    results.add(fi.getName());
                }
            }
            String timeOfDay = "mittags";
            for (String tod : wordList.getTimeOfDay()) {
                if (sentence.contains(tod.toLowerCase())) {
                    timeOfDay = tod;
                }
            }
            String day = "Montag";
            for (String tod : wordList.getDays()) {
                if (sentence.contains(tod.toLowerCase())) {
                    day = tod;
                }
            }
            Log.d(TAG, "Food:" + listToString(results));

            TextView dayOfWeekView = (TextView)findViewById(R.id.day_of_week);
            dayOfWeekView.setText(day);
            TextView timeOfDayView = (TextView)findViewById(R.id.time_of_day);
            timeOfDayView.setText(timeOfDay);
            TextView unparsed = (TextView)findViewById(R.id.parse_result);
            unparsed.setText(matches.get(0));

            resultList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, results));




        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String listToString(List l) {
        String res = "";
        for (Object o : l) {
            res += o.toString();
        }
        return res;
    }
}

