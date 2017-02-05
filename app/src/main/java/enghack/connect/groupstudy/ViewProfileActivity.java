package enghack.connect.groupstudy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProfileActivity extends AppCompatActivity {

    private static final String TAG = "ViewProfileActivity: ";
    static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        final TextView nameView = (TextView) findViewById(R.id.nameView);
        final TextView bio_field = (TextView) findViewById(R.id.bioView);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users");

        Bundle b = getIntent().getExtras();
        id = b.getString("id");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Group group = dataSnapshot.getValue(Group.class);
                if (dataSnapshot == null) return;

                nameView.setText(dataSnapshot.child(id).child("name").getValue().toString());
                String purl = dataSnapshot.child(id).child("photo_url").getValue().toString();

                new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                        .execute(purl);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);
    }

}
