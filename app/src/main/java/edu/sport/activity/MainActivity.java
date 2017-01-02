package edu.sport.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import edu.sport.R;
import edu.sport.application.MyApplication;
import edu.sport.entity.Group;
import edu.sport.entity.UserInfo;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    MapView mMapView = null;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check();
    }

    /**
     * 判断用户是否登陆（通过本地SharedPreferences文件用户名是否为空来判断用户是否登陆）
     */
    private void check() {
        String pass = MyApplication.getUserPwd(MyApplication.getNowUserName());
        Log.w(TAG, "check: "+pass +MyApplication.getNowUserName());
        //说明当前用户没有登陆或注册（启动登陆界面让用户进行登陆或注册）
        if (pass.isEmpty()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }else {//说明用户登陆了，初始化界面
            userInfo=new UserInfo(this,MyApplication.getNowUserName());
            Toast.makeText(this, "欢迎你："+userInfo.getUserInfo(UserInfo.NAME), Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
            init();
        }
    }

    /**
     * 初始化控件
     */
    private void init() {
        //获取硬件设备的管理器
        SensorManager sensorMnager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mMapView = (MapView) findViewById(R.id.bmapView);
        initLocation();
        //获取加速传感器
        Sensor accelerSensor = sensorMnager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerSensor!=null){
            //注册传感器
            sensorMnager.registerListener(new MySensorListener(),accelerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this, "您的手机上没有加速传感器", Toast.LENGTH_SHORT).show();
        }
       
    }

    private void initLocation() {
        //让地图显示当前用户的所在位置和位置图标
        LatLng center=new LatLng(Double.parseDouble(MyApplication.getLocationInfo(Group.LAT)),
                Double.parseDouble(MyApplication.getLocationInfo(Group.LNG)));
        MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(center, 18);
        BaiduMap map = mMapView.getMap();
        map.setMapStatus(mapStatus);
        map.setTrafficEnabled(true);
        map.setMyLocationEnabled(true);
        mMapView.showScaleControl(true);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.point);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(center)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        map.addOverlay(option);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.id.menu_group,R.id.distory,1,"历史纪录");
        menu.add(R.id.menu_group,R.id.change,2,"切换账号");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.distory:
                Toast.makeText(this, "历史纪录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mMapView!=null)
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (mMapView!=null)
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (mMapView!=null)
        mMapView.onPause();
    }

    class MySensorListener implements SensorEventListener{
        public static final float mGravity = 9.80665F;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

//            Log.d("linc", "value size: " + sensorEvent.values.length);
            float xValue = sensorEvent.values[0];// Acceleration minus Gx on the x-axis
            float yValue = sensorEvent.values[1];//Acceleration minus Gy on the y-axis
            float zValue = sensorEvent.values[2];//Acceleration minus Gz on the z-axis
//            Log.w(TAG, "onSensorChanged: "+"x轴： "+xValue+"  y轴： "+yValue+"  z轴： "+zValue );

            if(xValue > mGravity) {
                Log.w(TAG, "onSensorChanged: 重力指向设备左边" );
            } else if(xValue < -mGravity) {
                Log.w(TAG, "onSensorChanged: 重力指向设备右边" );
            } else if(yValue > mGravity) {
                Log.w(TAG, "onSensorChanged: 重力指向设备下边" );
            } else if(yValue < -mGravity) {
                Log.w(TAG, "onSensorChanged: 重力指向设备上边" );
            } else if(zValue > mGravity) {
                Log.w(TAG, "onSensorChanged: 屏幕朝上" );
            } else if(zValue < -mGravity) {
                Log.w(TAG, "onSensorChanged: 屏幕朝下" );
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

}
