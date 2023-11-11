
package booksCatalog.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity // To make this class as Entity
@Table(name = "sales") // To map the sales table in database with this entity
public class Sale {

	@EmbeddedId
	private SaleCPK pk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "store_id", insertable = false, updatable = false)
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "title_id", insertable = false, updatable = false)
	private Title title;

	@Column(name = "qty_sold")
	@Min(value = 0, message = "Quantity sold must be a positive integer")
	private int sold;

	public SaleCPK getPk() {
		return pk;
	}

	public void setPk(SaleCPK pk) {
		this.pk = pk;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pk, sold);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(pk, other.pk) && sold == other.sold;
	}

	@Override
	public String toString() {
		return "Sale [pk=" + pk + ", sold=" + sold + "]";
	}

}
