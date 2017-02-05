package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }

    public void MainActivity(View view){
        Intent myIntent = new Intent(Notifications.this, MainActivity.class);
        Notifications.this.startActivity(myIntent);
    }

    public void profile(View view){
        Intent myIntent = new Intent(Notifications.this, MyProfileActivity.class);
        Notifications.this.startActivity(myIntent);
    }

    public void findGroups(View view){
        Intent myIntent = new Intent(Notifications.this, findGroups.class);
        Notifications.this.startActivity(myIntent);
    }


}
