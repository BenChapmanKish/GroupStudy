package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class findGroups extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_groups);


    }

    public void MainActivity(View view) {
        Intent myIntent = new Intent(findGroups.this, MainActivity.class);
        findGroups.this.startActivity(myIntent);
    }

    public void Notifications(View view) {
        Intent myIntent = new Intent(findGroups.this, Notifications.class);
        findGroups.this.startActivity(myIntent);
    }

    public void profile(View view) {
        Intent myIntent = new Intent(findGroups.this, MyProfileActivity.class);
        findGroups.this.startActivity(myIntent);
    }
}