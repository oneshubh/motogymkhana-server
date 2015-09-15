package eu.motogymkhana.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "passwords")
public class Password {

	public static final String CUSTOMER_CODE = "customer_code";
	public static final String PW_HASH = "pw_hash";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@Column(name = CUSTOMER_CODE)
	private String customerCode;

	@Column(name = PW_HASH)
	private String passwordHash;

	public Password() {
	}
	
	public Password(String customerCode, String password) {
		this.customerCode=customerCode;
		this.passwordHash=password;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
