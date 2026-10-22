package com.msnegi.multilanguage;

import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.view.MenuItem;

import android.content.res.Configuration;
import java.util.Locale;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//getActionBar().hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.english)
		{
			String languageToLoad = "en";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

			this.setContentView(R.layout.activity_main);

			return true;
		}

		if (id == R.id.hindi)
		{
			String languageToLoad = "hi";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

			this.setContentView(R.layout.activity_main);

			return true;
		}

		if (id == R.id.deutsch) {
			String languageToLoad = "de";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

			this.setContentView(R.layout.activity_main);

			return true;
		}

		if (id == R.id.french) {
			String languageToLoad = "fr";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

			this.setContentView(R.layout.activity_main);

			return true;
		}

		if (id == R.id.japanese) {
			String languageToLoad = "ja";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

			this.setContentView(R.layout.activity_main);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
