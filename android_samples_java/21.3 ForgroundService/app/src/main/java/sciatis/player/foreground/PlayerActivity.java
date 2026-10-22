package sciatis.player.foreground;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayerActivity extends Activity implements ServicesConsts
{
	
	protected void startForegroundPlayerService()
	{
		Intent intent = new Intent(this, ForegroundServicePlayer.class);
	    startService(intent);
	}
	
	protected void stopForegroundPlayerService()
	{
		Intent intent = new Intent(PLAYER_INTENT);
	    intent.setAction(PLAYER_INTENT);
	    
	    sendBroadcast(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
				
		Button startForegroundServiceButton = (Button) findViewById(R.id.serviceStartButton);
		startForegroundServiceButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{

				startForegroundPlayerService();
			}
		});
		
		Button stotForegroundServiceButton = (Button) findViewById(R.id.serviceStopButton);
		stotForegroundServiceButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{

				stopForegroundPlayerService();
			}
		});
		
	}

	

}
