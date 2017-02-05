package enghack.connect.groupstudy;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewGroupActivity extends AppCompatActivity {

	// This entire file was really rushed due to time constraints
	// so we just used really bad lookup techniques by iterating over everything

	private final static String TAG = "ViewGroupActivity: ";

	DatabaseReference database;

	static boolean groupMember = false;
	static boolean removeMe = false;
	static boolean addMe = false;

	static ArrayList<String> members;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_group);

		final Button leaveButton = (Button) findViewById(R.id.leave_button);
		final TextView nameView = (TextView) findViewById(R.id.nameView);
		final TextView locationView = (TextView) findViewById(R.id.locationView);
		final LinearLayout userView = (LinearLayout) findViewById(R.id.userView);

		database = FirebaseDatabase.getInstance().getReference();

		final Intent viewUserIntent = new Intent(this, ViewProfileActivity.class);

		Bundle b = getIntent().getExtras();
		final String id = b.getString("did");
		Log.d(TAG, "Database ID: " + id);

		final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();


		leaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (members != null && id != null) {
					Log.d(TAG, "Changing members attribute for " + UID);
					if (members.contains(UID)) {
						database.getRoot().child("groups").child(id).child("member_ids").child(UID).removeValue();
						Log.d(TAG, "Removed member from database: " + database.getRoot().child("groups").child(id).child("member_ids").toString());

					} else {
						database.getRoot().child("groups").child(id).child("member_ids");
						Log.d(TAG, "Added member to database: " + database.getRoot().child("groups").child(id).child("member_ids").toString());
					}
					Log.d(TAG, "Member child: " + database.getRoot().child("groups").child(id).child("member_ids").toString());
				}
			}
		});

		ValueEventListener postListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				// Get Post object and use the values to update the UI
				//Group group = dataSnapshot.getValue(Group.class);
				if (dataSnapshot == null) return;

				Log.d(TAG, "ID: " + dataSnapshot.child("groups").child(id).toString());
				nameView.setText(dataSnapshot.child("groups").child(id).child("group_name").getValue().toString());
				locationView.setText(dataSnapshot.child("groups").child(id).child("meeting_location").getValue().toString());

				// Kludge to fix this so I can sleep tonight
				members = new ArrayList<>();
				ArrayList<String> tmembers = new ArrayList<>();
				for (final DataSnapshot member : dataSnapshot.child("groups").child(id).child("member_ids").getChildren()) {
					members.add(member.getValue().toString());
					tmembers.add(member.getValue().toString());
				}



				if (tmembers.contains(UID)) {
					Log.d(TAG, "Setting leave group button text");
					leaveButton.setText("Leave Group");
				} else {
					Log.d(TAG, "Setting join group button text");
					leaveButton.setText("Join Group");
				}


				Log.d(TAG, "users: " + dataSnapshot.child("users").getChildren().toString());
				for (final DataSnapshot datumSnapshot : dataSnapshot.child("users").getChildren()) {
					Log.d(TAG, "Checking member key " + datumSnapshot.getKey());
					if (tmembers.contains(datumSnapshot.getKey())) {
						LinearLayout ll = new LinearLayout(getApplicationContext());
						ll.setOrientation(LinearLayout.VERTICAL);
						ll.setBackgroundColor(Color.LTGRAY);

						TextView userV = new TextView(getApplicationContext());

						userV.setText(datumSnapshot.child("name").getValue().toString());
						userV.setTextSize(14);
						userV.setTextColor(Color.DKGRAY);
						userV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
						userV.setPadding(2, 4, 2, 2);
						ll.addView(userV);

						ll.setPadding(10, 10, 10, 10);

						ll.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								viewUserIntent.putExtra("id", datumSnapshot.getKey());
								startActivity(viewUserIntent);
							}
						});

						Log.d(TAG, "Added layout to groupView: " + ll.toString());
						userView.addView(ll);
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Getting Post failed, log a message
				Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
			}
		};
		database.addValueEventListener(postListener);


	}

	private void PostGroup(String userid, String name, String course, String location) {
		String key = database.child("groups").push().getKey();
		StudyGroup group = new StudyGroup(name, course, location, userid);
		Map<String, Object> postValues = group.toMap();

		Map<String, Object> childUpdates = new HashMap<>();
		childUpdates.put("/groups/" + key, postValues);

		database.updateChildren(childUpdates);
	}
}
