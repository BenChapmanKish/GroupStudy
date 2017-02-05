package enghack.connect.groupstudy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity: ";

	/*private DatabaseReference mDatabase;
	FirebaseDatabase database;

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private List<Lists> myDataset = new ArrayList<>();*/


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*mRecyclerView = (RecyclerView) findViewById(R.id.groupView);

		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		mAdapter = new ListAdapter(myDataset);
		mRecyclerView.setAdapter(mAdapter);*/

		final LinearLayout groupView = (LinearLayout) findViewById(R.id.groupView);

		final DatabaseReference groups = FirebaseDatabase.getInstance().getReference().child("groups");

		final Intent viewGroupIntent = new Intent(this, ViewGroupActivity.class);

		ValueEventListener postListener = new ValueEventListener() {
			@Override
			public void onDataChange(final DataSnapshot dataSnapshot) {
				// Get Post object and use the values to update the UI
				//Group group = dataSnapshot.getValue(Group.class);
				if (dataSnapshot == null) return;

				groupView.removeAllViewsInLayout();

				for (final DataSnapshot datumSnapshot: dataSnapshot.getChildren()) {
					LinearLayout ll = new LinearLayout(getApplicationContext());
					ll.setOrientation(LinearLayout.VERTICAL);
					ll.setBackgroundColor(Color.LTGRAY);

					TextView nameView = new TextView(getApplicationContext());
					nameView.setText(datumSnapshot.child("group_name").getValue().toString());
					nameView.setTextSize(20);
					nameView.setTextColor(Color.BLACK);
					nameView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
					nameView.setPadding(2, 4, 2, 2);
					ll.addView(nameView);

					TextView courseView = new TextView(getApplicationContext());
					courseView.setText(
							datumSnapshot.child("course_name").getValue().toString() + " - "
							+ datumSnapshot.child("meeting_location").getValue().toString());
					courseView.setTextSize(14);
					courseView.setTextColor(Color.DKGRAY);
					courseView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
					courseView.setPadding(2, 4, 2, 2);
					ll.addView(courseView);

					ll.setPadding(10, 10, 10, 10);

					ll.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// Fix this
							Log.d(TAG, "Datum snapshot: " + datumSnapshot.getKey());
							viewGroupIntent.putExtra("did", datumSnapshot.getKey());
							startActivity(viewGroupIntent);
						}
					});

					Log.d(TAG, "Added layout to groupView: " + ll.toString());
					groupView.addView(ll);

					LinearLayout tmp = new LinearLayout(getApplicationContext());
					tmp.setPadding(0, 10, 0, 10);
					groupView.addView(tmp);

					Log.d(TAG, "Snapshot: " + datumSnapshot.toString());
					//Log.d(TAG, "Snapshot value: " + datumSnapshot.child("group_name").getValue().toString());
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Getting Post failed, log a message
				Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
			}
		};
		groups.addValueEventListener(postListener);

		//Lists list = new Lists("Group Yay");
		//myDataset.add(list);

		// Initialize the facebook logging and analytics
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);

		// Create the intents for launching every activity
		final Intent login_intent =         new Intent(this, LoginActivity.class);
		final Intent profile_intent =       new Intent(this, MyProfileActivity.class);
		final Intent find_group_intent =    new Intent(this, FindGroupsActivity.class);
		final Intent create_group_intent =  new Intent(this, CreateGroupActivity.class);

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

		final Button create_group_button = (Button) findViewById(R.id.create_group_button);
		create_group_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MainActivity.this.startActivity(create_group_intent);
			}
		});

	}

}
