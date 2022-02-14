
public class User {
	int userid;
	String userName;
	String email;
	String password;
	float debt=0;
	float liability=0;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return userName;
	}
	public User(String userName, String email, String password, float debt,float liability) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.debt = debt;
		this.liability = liability;
	}
	public User(int userid, String userName, String email, float debt,float liability) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.email = email;
		this.debt = debt;
		this.liability = liability;
	}
	public User(String userName, String email, float debt) {
		super();
		this.userName = userName;
		this.email = email;
		this.debt = debt;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public float getDebt() {
		return debt;
	}
	
	public User(int userid, String userName, String email, String password, float debt, float liability) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.debt = debt;
		this.liability = liability;
	}
	public float getLiability() {
		return liability;
	}
	public void setLiability(float liability) {
		this.liability = liability;
	}
	public void setDebt(float debt) {
		this.debt = debt;
	}
	
	
}
