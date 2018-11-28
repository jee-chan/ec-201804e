package jp.co.sample.domain;

/**
 * 管理者情報を表すドメインクラス.
 * 
 * @author rks
 *
 */
public class Administrator {
	/** ID*/
	private Integer ID;
	/** 名前*/
	private String name;
	/** メールアドレス*/
	private String mailAddress;
	/** パスワード*/
	private String password;

	public Administrator() {
	}

	public Administrator(Integer iD, String name, String mailAddress, String password) {
		super();
		ID = iD;
		this.name = name;
		this.mailAddress = mailAddress;
		this.password = password;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Administrator [ID=" + ID + ", name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

	
}
