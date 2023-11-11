package booksCatalog.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class SaleCPK implements Serializable {

	@Column(name = "store_id")
	@NotNull(message = "Store ID is required")
	private String storeid;

	@Column(name = "title_id")
	@NotBlank(message = "Title ID is required")
	private String titleid;

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getTitleid() {
		return titleid;
	}

	public void setTitleid(String titleid) {
		this.titleid = titleid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(storeid, titleid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaleCPK other = (SaleCPK) obj;
		return Objects.equals(storeid, other.storeid) && Objects.equals(titleid, other.titleid);
	}

	@Override
	public String toString() {
		return "SaleCPK [storeid=" + storeid + ", titleid=" + titleid + "]";
	}

}
