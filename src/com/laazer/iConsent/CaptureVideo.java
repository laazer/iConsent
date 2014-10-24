package com.laazer.iConsent;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;
import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jacob on 10/2/14.
 */
public class CaptureVideo extends Activity {

    private VideoCapture videoCapture;
    private Button stop;
    private Button switchCam;
    private int state;
    private final static String START = "Start";
    private final static String STOP = "Stop";
    final Context context = this;
    private Button phonenumbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        phonenumbutton = (Button) findViewById(R.id.buttonShowCustomDialog);
        // add button listener
        phonenumbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Please Enter Your Partner's Number");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Please Enter Your Partner's Number");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_launcher);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setText("Done");
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = (EditText)dialog.findViewById(R.id.number_entry);
                        text.setText(et.getText());
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        state = 0;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        videoCapture = (VideoCapture) findViewById(R.id.videoView);
        stop = (Button) findViewById(R.id.stop);
        switchCam = (Button) findViewById(R.id.switch_cam);
        stop.setText(START);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(state == 0) {
                    videoCapture.startCapturingVideo();
                    stop.setText(STOP);
                    stop.setBackgroundColor(0xff000000);
                    state = 1;
                }
                else {
                    videoCapture.stopCapturingVideo();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
        switchCam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                videoCapture.switchCamera(arg0);
            }
        });

    }
}