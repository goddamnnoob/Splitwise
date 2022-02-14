
public class Group {
	int groupId;
	int createdBy;
	String groupName;
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Group(int groupId, int createdBy, String groupName) {
		
		this.groupId = groupId;
		this.createdBy = createdBy;
		this.groupName = groupName;
	}
public Group(int createdBy, String groupName) {
		
		this.createdBy = createdBy;
		this.groupName = groupName;
	}

	public Group(int groupId) {
		// TODO Auto-generated constructor stub
		this.groupId = groupId;
	}
}
