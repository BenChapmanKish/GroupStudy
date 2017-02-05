package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

	boolean first = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);

		if (first) {
			first = false;
			Intent intent = new Intent(this, AuthActivity.class);
			startActivity(intent);
		}

	}

	public void browseGroups(View view){
		Intent myIntent = new Intent(MainActivity.this, browseGroups.class);
		MainActivity.this.startActivity(myIntent);
	}

	public void profile(View view){
		Intent myIntent = new Intent(MainActivity.this, profile.class);
		MainActivity.this.startActivity(myIntent);
	}

	public void findGroups(View view){
		Intent myIntent = new Intent(MainActivity.this, findGroups.class);
		MainActivity.this.startActivity(myIntent);
	}
}
