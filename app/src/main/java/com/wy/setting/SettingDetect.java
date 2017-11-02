package com.wy.setting;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.math.BigInteger;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

import static com.wy.setting.GetLocalIp.getHostIp;
import static com.wy.setting.NetWorkTools.PingHost;

/**
 * Created by my-think on 2017/11/2.
 */

public class SettingDetect implements ISpeedTestListener
{
    public static final int LOCAL_NET_RESULT = 100;
    public static final int EXTRA_NET_RESULT = 101;
    public static final int CURRENT_SPEED = 102;
    public static final int RESULT_SPEED = 103;

    private static final String TAG = "wy";
    private static final String DEFAULT_EXTRA_HOST = "114.114.114.114";
    private static final String SpeedTestUrl = "http://d.app.huan.tv/appstore/resources/2013/11/15/tcltestspeed/file_1384506246118.jpg";

    private Event event = null;
    private Context context = null;
    public SettingDetect(Context context, Event event)
    {
        this.event = event;
        this.context = context;
    }

    public void startDetect()
    {
        String ip = getHostIp(context);
        if(ip == null)
        {
            Log.i(TAG, "local net is net reachable!");
            event.onEventCallBack(LOCAL_NET_RESULT, "0");

            return;
        }
        else
        {
            boolean local_net = PingHost(ip);
            if(local_net)
            {
                Log.i(TAG, "local net is ok!");
                event.onEventCallBack(LOCAL_NET_RESULT, "1");
            }
            else
            {
                Log.i(TAG, "local net is net reachable!");
                event.onEventCallBack(LOCAL_NET_RESULT, "0");
            }

            boolean extra_net = PingHost(DEFAULT_EXTRA_HOST);
            if(extra_net)
            {
                Log.i(TAG, "extra net is ok!");
                event.onEventCallBack(EXTRA_NET_RESULT, "1");
            }
            else
            {
                Log.i(TAG, "extra net is net reachable!");
                event.onEventCallBack(EXTRA_NET_RESULT, "0");
            }

            SpeedTestSocket speedTestSocket = new SpeedTestSocket();
            speedTestSocket.addSpeedTestListener(this);
            speedTestSocket.startFixedDownload(SpeedTestUrl, 5000);
        }
    }

    @Override
    public void onCompletion(SpeedTestReport report)
    {
        Log.i(TAG, "[COMPLETED] result speed: " + report.getTransferRateOctet().toBigInteger().divide(BigInteger.valueOf(1024)) + " kb/s");
        event.onEventCallBack(RESULT_SPEED, report.getTransferRateOctet().toBigInteger().divide(BigInteger.valueOf(1024)).toString());
    }

    @Override
    public void onProgress(float percent, SpeedTestReport report)
    {
        Log.i(TAG, "[PROGRESS] progress : " + percent + "%");
        Log.i(TAG, "[PROGRESS] current speed: " + report.getTransferRateOctet().toBigInteger().divide(BigInteger.valueOf(1024)) + " kb/s");
        event.onEventCallBack(CURRENT_SPEED, report.getTransferRateOctet().toBigInteger().divide(BigInteger.valueOf(1024)).toString());
    }

    @Override
    public void onError(SpeedTestError speedTestError, String errorMessage)
    {
        Log.i(TAG, "SpeedTestError: " + errorMessage);
    }
}
