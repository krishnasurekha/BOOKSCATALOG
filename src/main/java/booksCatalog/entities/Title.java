package booksCatalog.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity // To make this class as Entity
@Table(name = "titles") // To map the titles table in database with this entity
public class Title {
	@Id // primary key

	@Column(name = "title_id")
	@NotNull(message = "Title ID is required")
	private String titleId;

	@Column(name = "title")
	@NotBlank(message = "Title is required")
	private String title;

	@Column(name = "price")
	@Positive(message = "Price must be positive value")
	private Double price;

	@Column(name = "pub_id")
	@NotNull(message = "Publisher ID is required")
	private String pubId;

	@Column(name = "ytd_sales")
	@NotNull(message = "YTD Sales is required")
	@PositiveOrZero(message = "ytd sales must be a non-negative value")
	private Integer ytdsales;

	@Column(name = "release_year")
	private LocalDate year;

	// title to publisher
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pub_id", insertable = false, updatable = false)
	@JsonIgnore
	private Publisher publisher;

	// title to author
	@ManyToMany
	@JoinTable(name = "titleauthors", joinColumns = @JoinColumn(name = "title_id"), inverseJoinColumns = @JoinColumn(name = "au_id"))
	@JsonIgnore
	Set<Author> authors = new HashSet<Author>();

	// title to store
	@ManyToMany
	@JoinTable(name = "sales", joinColumns = @JoinColumn(name = "title_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
	@JsonIgnore
	Set<Store> stores = new HashSet<Store>();

	@OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
	private List<Sale> sales = new ArrayList<Sale>();

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public Integer getYtdsales() {
		return ytdsales;
	}

	public void setYtdsales(Integer ytdsales) {
		this.ytdsales = ytdsales;
	}

	public LocalDate getYear() {
		return year;
	}

	public void setYear(LocalDate year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authors, price, pubId, publisher, stores, title, titleId, year, ytdsales);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Title other = (Title) obj;
		return Objects.equals(authors, other.authors) && Objects.equals(price, other.price)
				&& Objects.equals(pubId, other.pubId) && Objects.equals(publisher, other.publisher)
				&& Objects.equals(stores, other.stores) && Objects.equals(title, other.title)
				&& Objects.equals(titleId, other.titleId) && Objects.equals(year, other.year)
				&& Objects.equals(ytdsales, other.ytdsales);
	}

	@Override
	public String toString() {
		return "Title [titleId=" + titleId + ", title=" + title + ", price=" + price + ", pubId=" + pubId
				+ ", ytdsales=" + ytdsales + ", year=" + year + ", publisher=" + publisher + ", authors=" + authors
				+ ", stores=" + stores + "]";
	}

}
