package com.example.guftugu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashBoard extends AppCompatActivity {

    EditText secretCode;
    AppCompatButton join;
    AppCompatButton share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        secretCode = findViewById(R.id.securityCode);
        join = findViewById(R.id.buttonJoin);

        try {
            JitsiMeetConferenceOptions defaultOption =
                    new JitsiMeetConferenceOptions.Builder()
                            .setWelcomePageEnabled(false)
                            .setServerURL(new URL("https://meet.jit.si/"))
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 JitsiMeetConferenceOptions option = new JitsiMeetConferenceOptions.Builder()
                         .setRoom(secretCode.getText().toString())
                         .setWelcomePageEnabled(false).build();

                JitsiMeetActivity.launch(DashBoard.this,option);
            }
        });

    }
}