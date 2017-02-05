package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity: ";

	private DatabaseReference mDatabase;
	FirebaseDatabase database;

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private List<Lists> myDataset = new ArrayList<>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		mAdapter = new ListAdapter(myDataset);
		mRecyclerView.setAdapter(mAdapter);

		Lists list = new Lists("Group Yay");
		myDataset.add(list);

		// Initialize the facebook logging and analytics
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);

		// Create the intents for launching every activity
		final Intent login_intent =         new Intent(this, AuthActivity.class);
		final Intent profile_intent =       new Intent(this, MyProfileActivity.class);
		final Intent find_group_intent =    new Intent(this, FindGroupsActivity.class);
		final Intent notifications_intent = new Intent(this, NotificationsActivity.class);

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		// We require the user to be logged in
		if (user == null) {
			startActivity(login_intent);
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
