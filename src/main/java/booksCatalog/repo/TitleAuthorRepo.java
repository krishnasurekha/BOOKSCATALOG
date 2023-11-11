package booksCatalog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import booksCatalog.entities.TitleAuthor;

public interface TitleAuthorRepo extends JpaRepository<TitleAuthor, String> {

}
