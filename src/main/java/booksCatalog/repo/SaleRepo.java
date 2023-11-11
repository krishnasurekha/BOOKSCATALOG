package booksCatalog.repo;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Sale;
import booksCatalog.entities.SaleCPK;

public interface SaleRepo extends JpaRepository<Sale, SaleCPK> {

	// 12
	@Query("select  s.store.storeId as storeId, sum(s.sold) as sumOfQtySold from Sale s Group By storeId Order By sumOfQtySold Desc")
	List<StoreSumOfQtyDTO> findByTitleSold(PageRequest pageRequest);

	// 15
	@Query(" select s.store.storeId as storeId, s.sold as qtySold from Sale s join s.title t where t.titleId like  %:titleId% ")
	List<SaleByTitleDTO> findSaleByTitle(@Param("titleId") String titleId);

}
