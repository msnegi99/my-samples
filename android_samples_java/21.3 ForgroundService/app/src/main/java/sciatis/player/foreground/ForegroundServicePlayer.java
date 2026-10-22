package sciatis.player.foreground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ForegroundServicePlayer extends Service 
	implements OnPreparedListener, ServicesConsts 
{
	final static int NOTIFICATION_ID = 4521;
	private MediaPlayer player = null;
	private boolean isReady = false;
	public static final String CHANNEL_ID = "ForegroundServiceChannel";

	private class StopReceiver extends BroadcastReceiver 
	{
		@Override
	    public void onReceive(Context context, Intent intent) 
	    {
	    	//Remove this service from foreground state, allowing it to be killed if more memory is needed.
			stopForeground(true);

			//Stop the service, if it was previously started. This is the same as calling stopService(Intent) for this particular service.
	    	stopSelf();
	    }
	}
	
	StopReceiver stopReciever = new StopReceiver();

	public ForegroundServicePlayer() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		try {

			player = new MediaPlayer();
			player.setWakeMode(getApplicationContext(),
					PowerManager.PARTIAL_WAKE_LOCK);

			// player = MediaPlayer.create(this, R.raw.rihana_disturbia);
			player.setLooping(false);
			player.setOnPreparedListener(this);
			AssetFileDescriptor fd = getResources().openRawResourceFd(
					R.raw.rihana_disturbia);
			player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
					fd.getLength());
			fd.close();

			isReady = true;
		} catch (Throwable e) {
			Log.e(getClass().getName(), "Fail:", e);
			e.printStackTrace();
		}
		
		IntentFilter intentFilter = 
			new IntentFilter(PLAYER_INTENT);

		registerReceiver(stopReciever, intentFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		try {
			if (isReady) {
				player.prepareAsync();
			} else {
				
				
				Toast.makeText(this, "Failed to init the Player",
						Toast.LENGTH_LONG).show();
			}
		} catch (Throwable e) {
			Log.e(getClass().getName(), "Fail:", e);
			e.printStackTrace();
		}

		return START_STICKY;//super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if (player != null) {
			if (player.isPlaying()) {
				player.stop();
			}
			player.release();
			player = null;

		}
		
		if(stopReciever != null)
		{
			unregisterReceiver(stopReciever);
		}
		super.onDestroy();
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void onPrepared(MediaPlayer mp) 
	{
		String name = "Rihana - Disturbia!";
		createNotificationChannel();
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), PlayerActivity.class), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

		Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
				.setContentTitle("Foreground Service")
				.setContentText("Now playing : " + name)
				.setSmallIcon(R.drawable.icon_play)
				.setContentIntent(pi)
				.build();

		this.startForeground(NOTIFICATION_ID, notification);

		player.start();

	}

	private void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel serviceChannel = new NotificationChannel(
					CHANNEL_ID,
					"Foreground Service Channel",
					NotificationManager.IMPORTANCE_DEFAULT
			);
			NotificationManager manager = getSystemService(NotificationManager.class);
			manager.createNotificationChannel(serviceChannel);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}






















