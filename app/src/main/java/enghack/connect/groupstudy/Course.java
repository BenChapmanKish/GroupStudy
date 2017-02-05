package enghack.connect.groupstudy;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben on 2017-02-05.
 */


@IgnoreExtraProperties
public class Course {
	public String course_name;

	public Course() {
		// Default constructor required for calls to DataSnapshot.getValue(Post.class)
	}

	public Course(String name) {
		this.course_name = name;
	}

	// [START post_to_map]
	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("course_name", course_name);

		return result;
	}
}