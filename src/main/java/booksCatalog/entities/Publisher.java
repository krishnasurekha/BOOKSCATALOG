
package booksCatalog.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity // To make this class as Entity
@Table(name = "publishers") // To map the publishers table in database with this entity
public class Publisher {

	@Id // primary key
	@Column(name = "pub_id")
	@NotNull(message = "Publsiher ID is required")
	private String pubId;

	@Column(name = "pub_name")
	@NotBlank(message = "Publisher Name is required")
	private String pubName;

	@Column(name = "email")
	@Email(message = "Invalid email format")
	@Size(max = 40, message = "Invalid email format")
	private String email;

	@Column(name = "city")
	@NotBlank(message = "City is required")
	@Size(max = 50, message = "City cannot exceed 50 characters")
	private String city;

	@Column(name = "country")
	@NotBlank(message = "Country is required")
	@Size(max = 50, message = "Country cannot exceed 50 characters")
	private String country;

	// Publisher to title
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "publisher")
	@JsonIgnore
	private List<Title> titles = new ArrayList<Title>();

	public List<Title> getTitles() {
		return titles;
	}

	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getPubName() {
		return pubName;
	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return Objects.hash(city, country, email, pubId, pubName, titles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(email, other.email) && Objects.equals(pubId, other.pubId)
				&& Objects.equals(pubName, other.pubName) && Objects.equals(titles, other.titles);
	}

	@Override
	public String toString() {
		return "Publisher [pubId=" + pubId + ", pubName=" + pubName + ", email=" + email + ", city=" + city
				+ ", country=" + country + ", titles=" + titles + "]";
	}

}