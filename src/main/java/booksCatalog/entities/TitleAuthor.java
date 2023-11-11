package booksCatalog.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity // To make this class as Entity
@Table(name = "titleauthors") // To map the titleauthors table in database with this entity
public class TitleAuthor {

	@EmbeddedId
	private TitleAuthorCPK pk;

	public TitleAuthorCPK getPk() {
		return pk;
	}

	public void setPk(TitleAuthorCPK pk) {
		this.pk = pk;
	}

	@Column(name = "royalty_pct")
	@NotBlank(message = "Royalty percentage is required")
	private String royalty;

	public String getRoyalty() {
		return royalty;
	}

	public void setRoyalty(String royalty) {
		this.royalty = royalty;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pk, royalty);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitleAuthor other = (TitleAuthor) obj;
		return Objects.equals(pk, other.pk) && Objects.equals(royalty, other.royalty);
	}

	@Override
	public String toString() {
		return "TitleAuthor [pk=" + pk + ", royalty=" + royalty + "]";
	}

}