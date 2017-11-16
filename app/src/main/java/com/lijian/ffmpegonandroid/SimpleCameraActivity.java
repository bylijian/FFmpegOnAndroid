package com.lijian.ffmpegonandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import com.lijian.ffmpegonandroid.view.AutoFitTextureView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijian on 2017/11/6.
 */

public class SimpleCameraActivity extends AppCompatActivity {
    private static final String TAG = "SimpleCameraActivity";

    private AutoFitTextureView autoFitTextureView;


    private CameraDevice device;
    private CameraCaptureSession session;
    private String cameraId;
    private CameraManager cameraManager;
    private Size previewSize;
    private CaptureRequest.Builder mPreviewRequestBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_camera);
        autoFitTextureView = (AutoFitTextureView) findViewById(R.id.texture_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoFitTextureView.isAvailable()) {
            openCamera(autoFitTextureView.getHeight(), autoFitTextureView.getWidth());
        } else {
            autoFitTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    openCamera(height, width);
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                    configureTransform(width, height);
                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });
        }
    }

    private void configureTransform(int width, int height) {
    }

    @TargetApi(21)
    private void openCamera(int height, int width) {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if (cameraManager == null) {
            return;
        }
        cameraId = getCameraId();
        if (TextUtils.isEmpty(cameraId)) {
            return;
        }
        try {
            cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    device = camera;
                    listSize();
                    SurfaceTexture texture = autoFitTextureView.getSurfaceTexture();
                    assert texture != null;

                    // We configure the size of default buffer to be the size of camera preview we want.
                    texture.setDefaultBufferSize(720, 920);

                    // This is the output Surface we need to start preview.
                    Surface surface = new Surface(texture);

                    // We set up a CaptureRequest.Builder with the output Surface.
                    try {
                        mPreviewRequestBuilder
                                = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    mPreviewRequestBuilder.addTarget(surface);
                    List<Surface> targets = new ArrayList<>();
                    targets.add(surface);
                    try {
                        device.createCaptureSession(targets, new CameraCaptureSession.StateCallback() {
                            @Override
                            public void onConfigured(@NonNull CameraCaptureSession session) {
                                try {
                                    session.setRepeatingRequest(mPreviewRequestBuilder.build(), null, null);
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                            }
                        }, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {

                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void listSize() {
        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);

            Size[] sizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(SurfaceTexture.class);
            for (Size size : sizes
                    ) {
                Log.d(TAG, "size=" + size);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    public String getCameraId() {
        String[] ids;
        try {
            ids = cameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            return "";
        }
        for (String id : ids
                ) {
            Log.d(TAG, "id=" + id);
            CameraCharacteristics characteristics = null;
            try {
                characteristics = cameraManager.getCameraCharacteristics(id);
            } catch (CameraAccessException e) {
                e.printStackTrace();
                return "";
            }
            Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
            if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                //not use front camera
                continue;
            }
            return id;
        }
        return "";
    }
}
