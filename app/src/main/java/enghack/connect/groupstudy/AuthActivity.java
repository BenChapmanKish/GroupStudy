package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class AuthActivity extends AppCompatActivity {

	LoginButton loginButton;
	public static CallbackManager callbackmanager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);

		loginButton = (LoginButton) findViewById(R.id.login_button);

		// Initialize SDK before setContentView(Layout ID)
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);

		setContentView(R.layout.activity_main);

		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				onFblogin();

			}
		});
	}


	private void onFblogin() {
		callbackmanager = CallbackManager.Factory.create();

		// Set permissions
		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

		LoginManager.getInstance().registerCallback(callbackmanager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {

						System.out.println("Success");
						GraphRequest.newMeRequest(
								loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
									@Override
									public void onCompleted(JSONObject json, GraphResponse response) {
										if (response.getError() != null) {
											// handle error
											System.out.println("ERROR");
										} else {
											System.out.println("Success");
											try {

												String jsonresult = String.valueOf(json);
												System.out.println("JSON Result" + jsonresult);

												String str_email = json.getString("email");
												String str_id = json.getString("id");
												String str_firstname = json.getString("first_name");
												String str_lastname = json.getString("last_name");

											} catch (JSONException e) {
												e.printStackTrace();
											}
										}
									}

								}).executeAsync();

					}

					@Override
					public void onCancel() {
						Log.d("App: ", "On cancel");
					}

					@Override
					public void onError(FacebookException error) {
						Log.d("App: ", error.toString());
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		callbackmanager.onActivityResult(requestCode, resultCode, data);
	}
}