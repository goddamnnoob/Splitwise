
public class Expense {
	int paidBy;
	int paidFor;
	int groupId;
	public Expense(int paidBy, int paidFor, int groupId,float amount, String date) {
		
		this.paidBy = paidBy;
		this.paidFor = paidFor;
		this.groupId = groupId;
		this.amount = amount;
		this.date = date;
	}
	public int getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(int paidBy) {
		this.paidBy = paidBy;
	}
	public int getPaidFor() {
		return paidFor;
	}
	public void setPaidFor(int paidFor) {
		this.paidFor = paidFor;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	float amount;
	String date;
		
}
