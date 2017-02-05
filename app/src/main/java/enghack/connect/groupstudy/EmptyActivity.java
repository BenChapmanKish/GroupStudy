package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmptyActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empty);

	}

	public void browseGroups(View view){
		Intent myIntent = new Intent(EmptyActivity.this, browseGroups.class);
		EmptyActivity.this.startActivity(myIntent);
	}

	public void profile(View view){
		Intent myIntent = new Intent(EmptyActivity.this, profile.class);
		EmptyActivity.this.startActivity(myIntent);
	}

	public void findGroups(View view){
		Intent myIntent = new Intent(EmptyActivity.this, findGroups.class);
		EmptyActivity.this.startActivity(myIntent);
	}
}
