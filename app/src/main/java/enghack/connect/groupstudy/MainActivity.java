package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity: ";

	private DatabaseReference mDatabase;
	FirebaseDatabase database;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize the facebook logging and analytics
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);

		// Create the intents for launching every activity
		final Intent login_intent =         new Intent(this, LoginActivity.class);
		final Intent profile_intent =       new Intent(this, MyProfileActivity.class);
		final Intent find_group_intent =    new Intent(this, FindGroupsActivity.class);
		final Intent notifications_intent = new Intent(this, NotificationsActivity.class);

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		// We require the user to be logged in
		if (user == null) {
			finish();
		}

		Button profile_button = (Button) findViewById(R.id.profile_button);
		profile_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MainActivity.this.startActivity(profile_intent);
			}
		});

		Button find_groups_button = (Button) findViewById(R.id.find_groups_button);
		find_groups_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MainActivity.this.startActivity(find_group_intent);
			}
		});

		Button notifications_button = (Button) findViewById(R.id.notifications_button);
		notifications_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MainActivity.this.startActivity(notifications_intent);
			}
		});

		FirebaseDatabase database = FirebaseDatabase.getInstance();

	}

}
