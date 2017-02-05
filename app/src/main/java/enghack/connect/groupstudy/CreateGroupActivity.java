package enghack.connect.groupstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
	private EditText course_name;
    private Spinner location_spinner;
    private String location;
	private String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        create_group = (Button) findViewById(R.id.create_group_button);

        database = FirebaseDatabase.getInstance().getReference();

        group_name = (EditText) findViewById(R.id.group_name_field);
	    course_name = (EditText) findViewById(R.id.course_name_field);
        location_spinner = (Spinner) findViewById(R.id.location_spinner);

        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                location = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + location, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        String[] items = new String[]{"V1 Great Hall", "UWP Grand Commons", "Dana Porter", "SLC Third Floor", "CLV Community Centre", "Davis Centre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        location_spinner.setAdapter(adapter);



        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewGroup();
            }
        });
    }

    private void createNewGroup() {
        final String group = group_name.getText().toString();
	    final String course = course_name.getText().toString();

        // Name is required
        if (TextUtils.isEmpty(group)) {
            group_name.setError(REQUIRED);
            return;
        }

	    // Course is required
	    if (TextUtils.isEmpty(course)) {
		    course_name.setError(REQUIRED);
		    return;
	    }

        // Location is required
        if (TextUtils.isEmpty(location)) {
            return;
        }

        // Disable button so there are no multi-posts
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

	    PostGroup(group, course, location);

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
	private void PostGroup(String name, String course, String Location) {
		String key = database.child("groups").push().getKey();
		StudyGroup group = new StudyGroup(name, course, location);
		Map<String, Object> postValues = group.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/groups/" + key, postValues);

		database.updateChildren(childUpdates);
	}
	// [END write_fan_out]


}
