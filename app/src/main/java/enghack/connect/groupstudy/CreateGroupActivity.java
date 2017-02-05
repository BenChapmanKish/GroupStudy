package enghack.connect.groupstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {

	private static final String TAG = "CreateGroupActivity: ";
	private static final String REQUIRED = "Required";

	private DatabaseReference database;

	Button create_group;
    private EditText group_name;
    private Spinner course_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        create_group = (Button) findViewById(R.id.create_group_button);

	    database = FirebaseDatabase.getInstance().getReference();

        group_name = (EditText) findViewById(R.id.group_name_field);
        course_spinner = (Spinner) findViewById(R.id.course_spinner);

        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewGroup();
            }
        });
    }

    private void createNewGroup() {
        final String group = group_name.getText().toString();
	    final Spinner course_spinner = (Spinner) findViewById(R.id.course_spinner);

        // Name is required
        if (TextUtils.isEmpty(group)) {
            group_name.setError(REQUIRED);
            return;
        }

        // ID is required
        if (TextUtils.isEmpty(course)) {
            return;
        }

        // Disable button so there are no multi-posts
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

	    writeNewPost(group, course);

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
                            Toast.makeText(CreateGroupActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(group, id);
                        }

                        // Finish this Activity, back to the stream
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        // [END single_value_read]*/
    }

	// [START write_fan_out]
	private void writeNewPost(String name, String course) {
		String key = database.child("groups").push().getKey();
		StudyGroup group = new StudyGroup(name, course);
		Map<String, Object> postValues = group.toMap();

		Map<String, Object> childUpdates = new HashMap<>();
		childUpdates.put("/groups/" + key, postValues);

		database.updateChildren(childUpdates);
	}
	// [END write_fan_out]

}
