package Users.Components;

abstract class User {
	protected int id;
	protected String name;
	protected String username;
	protected String password;
	protected int age;
	protected String gender;
	protected long phn_no;

	public User(int id, String name, String username, String password, int age, String gender, long phn_no) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.phn_no = phn_no;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public int getAge() {
		return this.age;
	}

	public String getGender() {
		return this.gender;
	}

	public long getPhn_no() {
		return this.phn_no;
	}

	public abstract void displayDetails();
}