package com.example.david.demoapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private static final int EXPLICIT_ACTIVITY_REQUEST_CODE = 1;
    private static final int PICK_PICTURE_REQUEST_CODE = 2;
    private static final String EXTRA_TEXT = "Content send from MainActivity";
    private static final String EXTRA_SUBJECT = "MPIP Send Title";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appsButtonOnClickListener();
        calculatorButtonOnClickListener();
        shareButtonOnClickListener();
        pickButtonOnClickListener();
        mTextView = findViewById(R.id.main_activity_text_view);
    }

    private void pickButtonOnClickListener() {
        Button mPickButton = findViewById(R.id.main_activity_button_pick);
        mPickButton.setOnClickListener(v -> {
            String title = getResources().getString(R.string.pick_chooser_title);
            Intent pickIntent = getImplicitIntent(Intent.ACTION_PICK,title,"image/*",null);
            startActivityForResult(pickIntent,PICK_PICTURE_REQUEST_CODE);
        });
    }

    private void shareButtonOnClickListener() {
        Button mShareButton = findViewById(R.id.main_activity_button_share);
        mShareButton.setOnClickListener(v -> {
            String title = getResources().getString(R.string.send_chooser_title);
            Intent sendIntent = getImplicitIntent(Intent.ACTION_SEND,title,"text/plain",null);
            sendIntent.putExtra(Intent.EXTRA_TEXT,EXTRA_TEXT);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT,EXTRA_SUBJECT);
            startActivity(sendIntent);
        });
    }

    private void appsButtonOnClickListener() {
        Button mActivitiesButton = findViewById(R.id.main_activity_button_activities);
        mActivitiesButton.setOnClickListener(
                v -> startActivity(ImplicitActivity.newIntent()));
    }

    private void calculatorButtonOnClickListener() {
        Button mTextButton = findViewById(R.id.main_activity_button_text);
        mTextButton.setOnClickListener(
                v -> startActivityForResult(new Intent(getApplicationContext(),ExplicitActivity.class),EXPLICIT_ACTIVITY_REQUEST_CODE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EXPLICIT_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String writtenText = ExplicitActivity.getWrittenText(data);
                mTextView.setText(writtenText);
            }
        }
        else if(requestCode == PICK_PICTURE_REQUEST_CODE){
            if(data!=null) {
                String title = getResources().getString(R.string.view_chooser_title);
                Intent viewIntent = getImplicitIntent(Intent.ACTION_VIEW, title, "image/*", data.getData());
                startActivity(viewIntent);
            }
        }
    }

    private Intent getImplicitIntent(String action,String title,String type,Uri data){
        Intent i = new Intent(action);
        i.setType(type);
        if(data!=null){
            i.setData(data);
        }
        return Intent.createChooser(i,title);
    }
}
