package enghack.connect.groupstudy;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FindGroupsActivity extends AppCompatActivity {

	private static final String TAG = "FindGroupsActivity: ";
	private static final String REQUIRED = "Required";

	private DatabaseReference database;

	Button create_group;
    private EditText group_name;
    private EditText course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_groups);

        create_group = (Button) findViewById(R.id.create_group_button);

	    database = FirebaseDatabase.getInstance().getReference();

        group_name = (EditText) findViewById(R.id.group_name_field);
        course_id = (EditText) findViewById(R.id.course_id_field);

        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewGroup();
            }
        });
    }

    private void createNewGroup() {
        final String group = group_name.getText().toString();
        final Long id = Long.valueOf(course_id.getText().toString());

        // Name is required
        if (TextUtils.isEmpty(group)) {
            group_name.setError(REQUIRED);
            return;
        }

        // ID is required
        if (id < 0) {
            course_id.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
       // setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

	    writeNewPost(group, id);

        /*// [START single_value_read]
        database.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(FindGroupsActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(group, id);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]*/
    }

	private void setEditingEnabled(boolean enabled) {
		group_name.setEnabled(enabled);
		course_id.setEnabled(enabled);
		if (enabled) {
			create_group.setVisibility(View.VISIBLE);
		} else {
			create_group.setVisibility(View.GONE);
		}
	}

	// [START write_fan_out]
	private void writeNewPost(String name, Long id) {
		String key = database.child("groups").push().getKey();
		StudyGroup group = new StudyGroup(name, id);
		Map<String, Object> postValues = group.toMap();

		Map<String, Object> childUpdates = new HashMap<>();
		childUpdates.put("/groups/" + key, postValues);

		database.updateChildren(childUpdates);
	}
	// [END write_fan_out]

}
