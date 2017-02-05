package enghack.connect.groupstudy;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben on 2017-02-05.
 */

@IgnoreExtraProperties
public class StudyGroup {
	public String group_name;
	public String course_name;
	public String meeting_location;
	public ArrayList<Long> member_ids;

	public StudyGroup() {
		// Default constructor required for calls to DataSnapshot.getValue(Post.class)
	}

	public StudyGroup(String name, String course, String location) {
		this.group_name = name;
		this.course_name = course;
		this.meeting_location = location;
		this.member_ids = new ArrayList<Long>();
	}

	// [START post_to_map]
	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("group_name", group_name);
		result.put("course_name", course_name);
		result.put("meeting_location", meeting_location);
		result.put("member_ids", member_ids);

		return result;
	}
}