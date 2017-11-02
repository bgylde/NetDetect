package com.wy.setting;

import android.util.Log;

import com.stealthcopter.networktools.Ping;
import com.stealthcopter.networktools.ping.PingResult;

import java.net.UnknownHostException;

/**
 * Created by my-think on 2017/11/2.
 */

public class NetWorkTools
{
    private final static String TAG = "wy";

    public static boolean PingHost(String host)
    {
        try
        {
            boolean result = false;
            PingResult pingResult = Ping.onAddress(host).setTimeOutMillis(3000).doPing();

            result = pingResult.isReachable();

            Log.i(TAG, "ping result: " + result);
            return result;
            // Asynchronously
//            Ping.onAddress("192.168.0.1").setTimeOutMillis(1000).setTimes(5).doPing(new Ping.PingListener()
//            {
//                @Override
//                public void onResult(PingResult pingResult)
//                {
//
//                }
//
//                @Override
//                public void onFinished(PingStats pingStats)
//                {
//
//                }
//            });
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
