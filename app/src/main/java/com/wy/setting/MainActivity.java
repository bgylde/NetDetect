package com.wy.setting;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Event
{
    public final static String TAG = "wy";

    private TextView text = null;

    public void onEventCallBack(int eventId, String message) {
        Message msg = new Message();
        msg.what = eventId;
        msg.obj = message;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case Event.LOCAL_NET_RESULT:
                    text.setText(text.getText() + "\n" + "local net result: " + msg.obj);
                    break;
                case Event.EXTRA_NET_RESULT:
                    text.setText(text.getText() + "\n" + "extra net result: " + msg.obj);
                    break;
                case Event.CURRENT_SPEED:
                    text.setText(text.getText() + "\n" + "current speed: " + msg.obj + "kb/s");
                    break;
                case Event.RESULT_SPEED:
                    text.setText(text.getText() + "\n" + "result speed: " + msg.obj + "kb/s");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);

        Context context = this;
        final SettingDetect detect = new SettingDetect(context, this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                detect.startDetect();
            }
        }).start();
    }
}
