package enghack.connect.groupstudy;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben on 2017-02-05.
 */

@IgnoreExtraProperties
public class StudyGroup {
	public String group_name;
	public Long course_id;
	public Long[] member_ids;
	public Long meetup_loc_id;

	public StudyGroup() {
		// Default constructor required for calls to DataSnapshot.getValue(Post.class)
	}

	public StudyGroup(String name, Long course, Long[] members, Long location) {
		this.group_name = name;
		this.course_id = course;
		this.member_ids = members;
		this.meetup_loc_id = location;
	}

	// [START post_to_map]
	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("group_name", group_name);
		result.put("course_id", course_id);
		result.put("member_ids", member_ids);
		result.put("meetup_loc_id", meetup_loc_id);

		return result;
	}
}