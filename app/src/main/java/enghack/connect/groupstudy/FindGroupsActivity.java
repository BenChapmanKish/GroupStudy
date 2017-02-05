package enghack.connect.groupstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindGroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        EditText frstName = (EditText)findViewById(R.id.firstName);

        Button btn =(Button)findViewById(R.id.Done);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText frstName = (EditText)findViewById(R.id.firstName);
                EditText lastName = (EditText)findViewById(R.id.lastName);
                EditText school = (EditText)findViewById(R.id.School);

            }
        });
    }



}
