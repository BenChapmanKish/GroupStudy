package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity: ";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);

		final Intent profile_intent = new Intent(this, MyProfileActivity.class);

		Button logout_button = (Button) findViewById(R.id.profile_button);
		logout_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MainActivity.this.startActivity(profile_intent);
			}
		});

	}


	public void profile(View view){
		Intent myIntent = new Intent(MainActivity.this, MyProfileActivity.class);
		MainActivity.this.startActivity(myIntent);
	}

	public void findGroups(View view){
		Intent myIntent = new Intent(MainActivity.this, findGroups.class);
		MainActivity.this.startActivity(myIntent);
	}

	public void Notifications(View view){
		Intent myIntent = new Intent(MainActivity.this, Notifications.class);
		MainActivity.this.startActivity(myIntent);
	}
}
