package booksCatalog.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity // To make this class as Entity
@Table(name = "authors") // To map the authors table in database with this entity
public class Author {

	@Id // primary key
	@Column(name = "au_id")
	@NotBlank(message = "Author ID is required")
	private String auId;

	@Column(name = "au_name")
	@NotBlank(message = "Author Name is required")
	private String auName;

	@Column(name = "email")
	@Email(message = "Invalid email format")
	@Size(max = 40, message = "Email cannot exceed 40 characters")
	private String email;

	@Column(name = "mobile")
	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Invalid number format")
	private String mobile;

	@Column(name = "city")
	@NotBlank(message = "City is required")
	@Size(max = 50, message = "City cannot exceed 50 characters")
	private String city;

	@Column(name = "country")
	@NotBlank(message = "Country is required")
	@Size(max = 50, message = "Country cannot exceed 50 characters")
	private String country;

	@ManyToMany(mappedBy = "authors")
	@JsonIgnore
	Set<Title> titles = new HashSet<Title>();

	public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}

	public String getAuId() {
		return auId;
	}

	public void setAuId(String auId) {
		this.auId = auId;
	}

	public String getAuName() {
		return auName;
	}

	public void setAuName(String auName) {
		this.auName = auName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(auId, auName, city, country, email, mobile, titles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return Objects.equals(auId, other.auId) && Objects.equals(auName, other.auName)
				&& Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(email, other.email) && Objects.equals(mobile, other.mobile)
				&& Objects.equals(titles, other.titles);
	}

	@Override
	public String toString() {
		return "Author [auId=" + auId + ", auName=" + auName + ", email=" + email + ", mobile=" + mobile + ", city="
				+ city + ", country=" + country + ", titles=" + titles + "]";
	}

}
