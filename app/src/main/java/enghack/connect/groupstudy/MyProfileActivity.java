package enghack.connect.groupstudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfileActivity extends AppCompatActivity {

	private static final String TAG = "MyProfileActivity: ";

	private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        final Intent login_intent = new Intent(this, AuthActivity.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(login_intent);
        }

	   final AlertDialog.Builder confirmLogout = new AlertDialog.Builder(this)
			    .setTitle("Log Out")
			    .setMessage("Do you really want to log out?")
			    .setIcon(android.R.drawable.ic_dialog_alert)
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

				    public void onClick(DialogInterface dialog, int whichButton) {
					    FirebaseAuth.getInstance().signOut();
					    LoginManager.getInstance().logOut();
					    finish();
				    }})
			    .setNegativeButton(android.R.string.no, null);

        Button logout_button = (Button) findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirmLogout.show();
            }
        });

	    mAuthListener = new FirebaseAuth.AuthStateListener() {
		    @Override
		    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
			    FirebaseUser user = firebaseAuth.getCurrentUser();
			    if (user != null) {
				    // User is signed in
				    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

				    //user.getDisplayName();
				    //user.getPhotoUrl();

			    } else {
				    // User is signed out
				    Log.d(TAG, "onAuthStateChanged:signed_out");
				    finish();
			    }
		    }
	    };
    }
}
