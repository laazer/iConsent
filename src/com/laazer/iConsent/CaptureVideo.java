package com.laazer.iConsent;

import android.app.Activity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;

/**
 * Created by jacob on 10/2/14.
 */
public class CaptureVideo extends Activity {

    private VideoCapture videoCapture;
    private Button stop;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        state = 0;
        videoCapture = (VideoCapture) findViewById(R.id.videoView);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(state == 0) {
                    videoCapture.startCapturingVideo();
                    stop.setBackgroundColor(0xff0000);
                    state = 1;
                }
                else {
                    videoCapture.stopCapturingVideo();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });

    }
}