package booksCatalog.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity // To make this class as Entity
@Table(name = "stores") // To map the stores table in database with this entity
public class Store {
	@Id // primary key
	@Column(name = "store_id")
	@NotNull(message = "Store ID is required")
	private String storeId;

	@Column(name = "location")
	@NotBlank(message = "Location is required")
	@Size(max = 100, message = "Location cannot exceed 100 characters")
	private String location;

	@Column(name = "city")
	@NotBlank(message = "City is required")
	@Size(max = 50, message = "City cannot exceed 50 characters")
	private String city;

	@Column(name = "country")
	@NotBlank(message = "Country is required")
	@Size(max = 50, message = "Country cannot exceed 50 characters")
	private String country;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	@JsonIgnore
	List<Sale> sales = new ArrayList<>();

	// Store to title
	@ManyToMany(mappedBy = "stores")
	@JsonIgnore
	Set<Title> titles = new HashSet<Title>();

	public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
		return Objects.hash(city, country, location, storeId, titles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(location, other.location) && Objects.equals(storeId, other.storeId)
				&& Objects.equals(titles, other.titles);
	}

	@Override
	public String toString() {
		return "Store [storeId=" + storeId + ", location=" + location + ", city=" + city + ", country=" + country
				+ ", titles=" + titles + "]";
	}

}
