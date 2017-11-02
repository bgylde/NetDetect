package com.wy.setting;

public interface Event 
{
	public static final int LOCAL_NET_RESULT = 100;
	public static final int EXTRA_NET_RESULT = 101;
	public static final int CURRENT_SPEED = 102;
	public static final int RESULT_SPEED = 103;

	public void onEventCallBack(int eventId, String message);
}
