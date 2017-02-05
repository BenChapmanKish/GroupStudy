package enghack.connect.groupstudy;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben on 2017-02-05.
 */

@IgnoreExtraProperties
public class User {

	public String name;
	public String photo_url;
	public Long[] search_courses;
	public Long[] groups;

	public User() {
		// Default constructor required for calls to DataSnapshot.getValue(User.class)
	}

	public User(String name, String purl) {
		this.name = name;
		this.photo_url = purl;
		this.search_courses = new Long[0];
		this.groups = new Long[0];
	}

	// [START post_to_map]
	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("name", name);
		result.put("photo_url", photo_url);
		result.put("search_courses", search_courses);
		result.put("groups", groups);

		return result;
	}

}