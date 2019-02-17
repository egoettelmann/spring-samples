package lu.elio.sample.spring.data.repositories;

import lu.elio.sample.spring.data.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IParentRepository extends JpaRepository<Parent, Long> {
}
