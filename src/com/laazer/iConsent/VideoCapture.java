package com.laazer.iConsent;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class VideoCapture extends SurfaceView implements SurfaceHolder.Callback {

    private MediaRecorder recorder;
    private SurfaceHolder holder;
    private SurfaceView surfaceView;
    public Context context;
    private Camera camera;
    public static String videoPath = Environment.getExternalStorageDirectory()
            .getPath() +"/YOUR_VIDEO.mp4";
    int cameraId = 0;

    public VideoCapture(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public VideoCapture(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoCapture(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @SuppressLint("NewApi")
    public void init() {
        try {
            recorder = new MediaRecorder();
            holder = getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            surfaceView = new SurfaceView(getContext());
            camera = getCameraInstance();
            if(android.os.Build.VERSION.SDK_INT > 7)
                camera.setDisplayOrientation(90);
            camera.unlock();
            recorder.setCamera(camera);
            recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
            recorder.setOutputFile(videoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder mHolder) {
        try {
            recorder.setPreviewDisplay(mHolder.getSurface());
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startCapturingVideo() {
        try {
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopCapturingVideo() {
        try {
            recorder.stop();
            camera.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(5)
    public void surfaceDestroyed(SurfaceHolder arg0) {
        if (recorder != null) {
            stopCapturingVideo();
            recorder.release();
            camera.lock();
            camera.release();
            recorder = null;
        }
    }

    private Camera getCameraInstance() {
        Camera c = null;
        try {
            cameraId = CameraInfo.CAMERA_FACING_FRONT;
            c = Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c;
    }

    public int switchCamera(View view) {
        cameraId = -1;

        // Search for the front facing camera
//        int numberOfCameras = Camera.getNumberOfCameras();
//        if (numberOfCameras < 2) {
//            Toast.makeText(getContext(), "No front facing camera found.", Toast.LENGTH_LONG).show();
//        }
//        for (int i = 0; i < numberOfCameras; i++) {
//            CameraInfo info = new CameraInfo();
//            Camera.getCameraInfo(i, info);
//            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
//                //Log.d(DEBUG_TAG, "Camera found");
//                cameraId = i;
//                break;
//            }
//        }
        camera.stopPreview();
        camera.release();
        if(cameraId == CameraInfo.CAMERA_FACING_BACK) {
            cameraId = CameraInfo.CAMERA_FACING_FRONT;
        }
        else {
            cameraId = CameraInfo.CAMERA_FACING_BACK;
        }
        camera = camera.open(cameraId);
        try {
            camera.setPreviewDisplay(holder);
        }catch (IOException io) {
            io.printStackTrace();
        }
        camera.startPreview();
        return cameraId;
    }
}