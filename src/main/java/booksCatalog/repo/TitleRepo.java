package booksCatalog.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Publisher;
import booksCatalog.entities.Title;

public interface TitleRepo extends JpaRepository<Title, String> {

	// 7
	List<Title> findByPublisher(Optional<Publisher> publisher);

	// 8
	@Query("SELECT t.title, a.auName FROM Title t JOIN t.authors a")
	List<Object[]> findTitleAndAuthor();

	// 9
	List<Title> findByTitleContaining(String title);

	// 10
	List<Title> findByPriceBetween(Double minPrice, Double maxPrice);

	// 11
	List<Title> findTop5ByOrderByYtdsalesDesc();

	// 13
	@Query("select t.title as title ,t.price as price ,t.ytdsales as ytdSales,t.publisher.pubName as pubName ,a.auName  as auName from Title t join t.authors a where titleId like %:titleId%  ")
	List<TitleDetailsDTO> findAllDetailsByTitle(@Param("titleId") String titleId);

}
