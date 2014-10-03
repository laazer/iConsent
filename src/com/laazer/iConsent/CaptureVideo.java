package com.laazer.iConsent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jacob on 10/2/14.
 */
public class CaptureVideo extends Activity {

    private VideoCapture videoCapture;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        videoCapture = (VideoCapture) findViewById(R.id.videoView);
        stop= (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                videoCapture.stopCapturingVideo();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

    }
}