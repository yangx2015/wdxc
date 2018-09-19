package cn.bidostar.ticserver;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.bidostar.ticserver.service.CarBindService;
import cn.bidostar.ticserver.service.SocketCarBindService;
import cn.bidostar.ticserver.utils.AppActivityManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        Intent serviceOne = new Intent();
        serviceOne.setClass(MainActivity.this, CarBindService.class);
        startService(serviceOne);
        Intent serviceTwo = new Intent();
        serviceTwo.setClass(MainActivity.this, SocketCarBindService.class);
        startService(serviceTwo);
        AppActivityManager.openActivity(this,TestActivity.class);
    }
}
