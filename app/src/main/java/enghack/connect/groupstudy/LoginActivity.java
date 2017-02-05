package enghack.connect.groupstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

	private static final String TAG = "LoginActivity: ";
	LoginButton loginButton;
	public static CallbackManager callbackmanager;
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;

	DatabaseReference database;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginButton = (LoginButton) findViewById(R.id.login_button);


		// Initialize SDK before setContentView(Layout ID)
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);

		final Intent list_groups_intent = new Intent(this, MainActivity.class);

		database = FirebaseDatabase.getInstance().getReference("users");


		callbackmanager = CallbackManager.Factory.create();

		loginButton.setReadPermissions("email", "public_profile", "user_education_history");
		loginButton.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				Log.d(TAG, "facebook:onSuccess:" + loginResult);
				handleFacebookAccessToken(loginResult.getAccessToken());
				finish();
				startActivity(list_groups_intent);
			}

			@Override
			public void onCancel() {
				Log.d(TAG, "facebook:onCancel");
				finish();
			}

			@Override
			public void onError(FacebookException error) {
				Log.e(TAG, "facebook:onError", error);
				finish();
			}
		});

		mAuth = FirebaseAuth.getInstance();

		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser fbuser = firebaseAuth.getCurrentUser();
				if (fbuser != null) {
					// User is signed in
					Log.d(TAG, "onAuthStateChanged:signed_in:" + fbuser.getUid());



					// Create new user for database

					database.orderByKey().equalTo(fbuser.getUid()).addValueEventListener(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							if(dataSnapshot == null || dataSnapshot.getChildren() == null) {
								// User not in database

								Log.d(TAG, "User logged in for first time");

								FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
								createNewUser(fbuser.getUid(), fbuser.getDisplayName(), fbuser.getPhotoUrl().toString());


							}
						}

						@Override
						public void onCancelled(DatabaseError databaseError) {

						}
					});

					finish();
					startActivity(list_groups_intent);

				} else {
					// User is signed out
					Log.d(TAG, "onAuthStateChanged:signed_out");
				}
			}
		};

	}

	private void createNewUser(String userId, String name, String purl) {

		String key = database.child("users").push().getKey();
		User dbuser = new User(name, purl);
		Map<String, Object> postValues = dbuser.toMap();

		Map<String, Object> childUpdates = new HashMap<>();
		childUpdates.put("/users/" + userId, postValues);

		database.updateChildren(childUpdates);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Pass the activity result back to the Facebook SDK
		callbackmanager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(mAuthListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAuthListener != null) {
			mAuth.removeAuthStateListener(mAuthListener);
		}
	}

	private void handleFacebookAccessToken(AccessToken token) {
		Log.d(TAG, "handleFacebookAccessToken:" + token);

		AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

						// If sign in fails, display a message to the user. If sign in succeeds
						// the auth state listener will be notified and logic to handle the
						// signed in user can be handled in the listener.
						if (!task.isSuccessful()) {
							Log.w(TAG, "signInWithCredential", task.getException());
							Toast.makeText(LoginActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
						}

					}
				});
	}

}