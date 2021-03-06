package com.nuannuan.camer.preview.CameraSurface;

import com.nuannuan.camer.cameracontroller.CameraController;
import com.nuannuan.camer.cameracontroller.CameraControllerException;
import com.nuannuan.camer.preview.Preview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

/**
 * Provides support for the surface used for the preview, using a TextureView.
 *
 * @author Mark Harman 18 June 2016
 * @author kevin
 */
public class MyTextureView extends TextureView implements CameraSurface {
  private static final String TAG = MyTextureView.class.getSimpleName();

  private Preview preview = null;
  private int[] measure_spec = new int[2];


  public MyTextureView(Context context, Bundle savedInstanceState, Preview preview) {
    super(context);
    this.preview = preview;

    Log.d(TAG, "new MyTextureView");

    // Install a TextureView.SurfaceTextureListener so we get notified when the
    // underlying surface is created and destroyed.
    this.setSurfaceTextureListener(preview);
  }


  @Override public View getView() {
    return this;
  }


  @Override public void setPreviewDisplay(CameraController camera_controller) {

    Log.d(TAG, "setPreviewDisplay");
    try {
      camera_controller.setPreviewTexture(this.getSurfaceTexture());
    } catch (CameraControllerException e) {

      Log.e(TAG, "Failed to set preview display: " + e.getMessage());
      e.printStackTrace();
    }
  }


  @Override public void setVideoRecorder(MediaRecorder video_recorder) {
    // should be no need to do anything (see documentation for MediaRecorder.setPreviewDisplay())
  }


  @SuppressLint("ClickableViewAccessibility") @Override
  public boolean onTouchEvent(MotionEvent event) {
    return preview.touchEvent(event);
  }


  @Override protected void onMeasure(int widthSpec, int heightSpec) {
    preview.getMeasureSpec(measure_spec, widthSpec, heightSpec);
    super.onMeasure(measure_spec[0], measure_spec[1]);
  }


  @Override public void setTransform(Matrix matrix) {
    super.setTransform(matrix);
  }
}
