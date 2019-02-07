package com.issac.indoor_navigation.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.issac.indoor_navigation.R;
import com.issac.indoor_navigation.TestData;
import com.issac.library.MapView;
import com.issac.library.layer.LocationLayer;
import com.issac.library.utils.Compass;
import com.issac.library.utils.MapUtils;
import com.issac.library.utils.math.Matrix;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Issac on 2017/7/18.
 */

public class ARNavigationActivity extends AppCompatActivity{
//    implements View.OnClickListener{
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int STRIGHT = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int TURN = 4;
    ///为了使照片竖直显示
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private SurfaceView surfaceview;
    private SurfaceHolder surfaceholder;
    private ImageView arrow;
    private CameraManager cameramanager;
    private Handler childHandler, mainHandler,orientationHandler;
    private String mCameraID;//摄像头Id 0 为后  1 为前
    private ImageReader mImageReader;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice cameradevice;

//    private OrientationSensorListener orientationListener;
//    private SensorManager sm;
//    private Sensor sensor;


    private LocationLayer locationLayer;
    private MapView mapView;
    private Compass compass;
    private WifiManager wifiManager; // 管理wifi
    private ArrayList<ScanResult> list; // 存放周围wifi热点对象的列表

    private float ArrowRotate = 0f, CircleRotate = 361f;
    private int curr_x = 1000, curr_y = 50;
    private Matrix matrix;
    private PointF source, target,curr;
    private List<PointF> nodes = TestData.getNodesList(),
            nodesContact = TestData.getNodesContactList();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STRIGHT:
                    arrow.setImageBitmap(getBitmapFromId(R.drawable.straight));
                    break;
                case LEFT:
                    arrow.setImageBitmap(getBitmapFromId(R.drawable.right));//右转
                    break;
                case RIGHT:
                    arrow.setImageBitmap(getBitmapFromId(R.drawable.left));//左转
                    break;
                case TURN:
                    arrow.setImageBitmap(getBitmapFromId(R.drawable.turn));
                    break;
            }
        }
    };
        private Runnable runnable = new Runnable() {
            public void run() {
                this.update();
                handler.postDelayed(this, 5000);// 间隔1000ms
            }

            void update() {
                Log.i("degree", "degree:" + compass.getazimuth() + "\n");
                wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE); //获得系统wifi服务
                wifiManager.startScan();
                list = (ArrayList<ScanResult>) wifiManager.getScanResults();
                int a, b, c;
                a = b = c = 0;
                int[] level = {1, 1, 1};
                for (int i = 0; i < list.size(); i++) {
                    String strSsid = list.get(i).SSID;
                    String strBssid = list.get(i).BSSID;
                    String strCapabilities = list.get(i).capabilities;
                    int strLevel = list.get(i).level;
                    Log.i("WIFI", "SSID: " + strSsid + "\n" + "BSSID: " + strBssid + "\n" + "capabilities： "
                            + strCapabilities + "\n" + "level: " + strLevel + "\n");
                    if (strSsid.equals("navigation1")) {
                        level[0] = strLevel;
                    } else if (strSsid.equals("navigation2")) {
                        level[1] = strLevel;
                    } else if (strSsid.equals("navigation3")) {
                        level[2] = strLevel;
                    }
                }
                int x = 0, y = 0;
                if (level[0] != 1 && level[1] != 1 && level[2] != 1) {
                    matrix.setdis(level);
                    double[] point = matrix.cal();
                    Log.i("Point", point[0] + " " + point[1] + "\n");
                    x = (int) point[0] - curr_x;
                    y = (int) point[1] - curr_y;
                }
                curr_x = curr_x + x;
                curr_y = curr_y + y;
                curr = new PointF(curr_x, curr_y);
                locationLayer.setCurrentPosition(curr);
                mapView.translate(x, y); //Move

                if (x < 0 && y <= 0) {
                    ArrowRotate = -90 + (float) Math.toDegrees(Math.atan(y * 1.0 / x));
                } else if (x < 0 && y > 0) {
                    ArrowRotate = -90 - (float) Math.toDegrees(Math.atan(y * (-1.0) / x));
                } else if (x > 0 && y >= 0) {
                    ArrowRotate = 90 + (float) Math.toDegrees(Math.atan(y * 1.0 / x));
                } else if (x > 0 && y < 0) {
                    ArrowRotate = 90 - (float) Math.toDegrees(Math.atan(y * (-1.0) / x));
                } else {
                    if (y > 0) ArrowRotate = 180;
                    else if (y < 0) ArrowRotate = 0;
                }
                if (CircleRotate != 361f && CircleRotate != compass.getazimuth()) {
                    ArrowRotate += compass.getazimuth() - CircleRotate;
                }
                CircleRotate = compass.getazimuth();
                locationLayer.setCompassIndicatorCircleRotateDegree(CircleRotate);
                locationLayer.setCompassIndicatorArrowRotateDegree(ArrowRotate);


                mapView.refresh();
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_arnav);

            source = new PointF(3910, 4505);
            target = new PointF(4240, 4505);
            //获取起止点
//        Intent intent = this.getIntent();
//        source = (PointF)intent.getSerializableExtra("source");
//        target = (PointF)intent.getSerializableExtra("target");
            compass = new Compass(this);
            compass.start();

            initVIew();
            //构建最优路径
            MapUtils.init(nodes.size(), nodesContact.size());
            List<Integer> routeList = MapUtils.getShortestDistanceBetweenTwoPoints
                    (source, target, nodes, nodesContact);
            AR_Navigation(routeList, nodes);//nodes
            handler.postDelayed(runnable, 5000);// 间隔1000ms
        }

        private void AR_Navigation(List<Integer> routeList, List<PointF> nodes) {
            List<Float> angles = MapUtils.getDegreeBetweenTwoPointsWithVertical(routeList, nodes);
            for (int i = 0; i < angles.size(); i++) {
                Float angle = angles.get(i), azimuth = compass.getazimuth();
                if (Math.abs(angle - azimuth) <= 30f)//该范围内认为可以直行
                    arrow.setImageBitmap(getBitmapFromId(R.drawable.straight));
                else if (Math.abs(angle - azimuth) <= 150f) {
                    if (angle > azimuth)
                        arrow.setImageBitmap(getBitmapFromId(R.drawable.right));//右转
                    else
                        arrow.setImageBitmap(getBitmapFromId(R.drawable.left));//左转
                } else//turn around
                    arrow.setImageBitmap(getBitmapFromId(R.drawable.turn));
            }

        }

        private Bitmap getBitmapFromId(int id) {
            Resources res = getResources();
            BitmapDrawable bitDraw = new BitmapDrawable(res.openRawResource(id));
            Bitmap bm = bitDraw.getBitmap();
            return bm;
            //mImageView.setImageBitmap(bm);
        }

        private void initVIew() {
            arrow = (ImageView) findViewById(R.id.arrow);
            surfaceview = (SurfaceView) findViewById(R.id.surfaceview);
            ;
            surfaceholder = surfaceview.getHolder();
            surfaceholder.setKeepScreenOn(true);
            // mSurfaceView添加回调
            surfaceholder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) { //SurfaceView创建
                    // 初始化Camera
                    initCamera2();
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) { //SurfaceView销毁
                    // 释放Camera资源
                    if (null != cameradevice) {
                        cameradevice.close();
                        ARNavigationActivity.this.cameradevice = null;
                    }
                }
            });
        }

        /**
         * 初始化Camera2
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void initCamera2() {
            HandlerThread handlerThread = new HandlerThread("Camera2");
            handlerThread.start();
            childHandler = new Handler(handlerThread.getLooper());
            mainHandler = new Handler(getMainLooper());
            mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;//后摄像头
            mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1);
            mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() { //可以在这里处理拍照得到的临时照片 例如，写入本地
                @Override
                public void onImageAvailable(ImageReader reader) {
                    cameradevice.close();
                    surfaceview.setVisibility(View.GONE);
                    arrow.setVisibility(View.VISIBLE);
                    // 拿到拍照照片数据
                    Image image = reader.acquireNextImage();
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);//由缓冲区存入字节数组
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (bitmap != null) {
                        arrow.setImageBitmap(bitmap);
                    }
                }
            }, mainHandler);
            //获取摄像头管理
            cameramanager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //打开摄像头
                cameramanager.openCamera(mCameraID, stateCallback, mainHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        /**
         * 摄像头创建监听
         */
        private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {//打开摄像头
                cameradevice = camera;
                //开启预览
                takePreview();
            }

            @Override
            public void onDisconnected(CameraDevice camera) {//关闭摄像头
                if (null != cameradevice) {
                    cameradevice.close();
                    ARNavigationActivity.this.cameradevice = null;
                }
            }

            @Override
            public void onError(CameraDevice camera, int error) {//发生错误
                Toast.makeText(ARNavigationActivity.this, "摄像头开启失败", Toast.LENGTH_SHORT).show();
            }
        };

        /**
         * 开始预览
         */
        private void takePreview() {
            try {
                // 创建预览需要的CaptureRequest.Builder
                final CaptureRequest.Builder previewRequestBuilder = cameradevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                // 将SurfaceView的surface作为CaptureRequest.Builder的目标
                previewRequestBuilder.addTarget(surfaceholder.getSurface());
                // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
                cameradevice.createCaptureSession(Arrays.asList(surfaceholder.getSurface(), mImageReader.getSurface()), new CameraCaptureSession.StateCallback() // ③
                {
                    @Override
                    public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                        if (null == cameradevice) return;
                        // 当摄像头已经准备好时，开始显示预览
                        mCameraCaptureSession = cameraCaptureSession;
                        try {
                            // 自动对焦
                            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                            // 打开闪光灯
                            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                            // 显示预览
                            CaptureRequest previewRequest = previewRequestBuilder.build();
                            mCameraCaptureSession.setRepeatingRequest(previewRequest, null, childHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                        Toast.makeText(ARNavigationActivity.this, "配置失败", Toast.LENGTH_SHORT).show();
                    }
                }, childHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onDestroy() {
            handler.removeCallbacks(runnable); //停止刷新
            super.onDestroy();
        }

}
