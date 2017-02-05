package enghack.connect.groupstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FindGroupsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Lists> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_groups);

        mRecyclerView = (RecyclerView) findViewById(R.id.groupView);

		if (mRecyclerView != null) {
			mRecyclerView.setHasFixedSize(true);

			// use a linear layout manager
			mLayoutManager = new LinearLayoutManager(this);
			mRecyclerView.setLayoutManager(mLayoutManager);

			// specify an adapter (see also next example)
			mAdapter = new ListAdapter(myDataset);
			mRecyclerView.setAdapter(mAdapter);


			//Lists list = new Lists("Group Yay");
			//myDataset.add(list);
		}
    }

}
