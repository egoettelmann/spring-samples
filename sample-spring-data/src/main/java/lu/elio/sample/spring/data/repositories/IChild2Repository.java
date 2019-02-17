package lu.elio.sample.spring.data.repositories;

import lu.elio.sample.spring.data.entities.Child2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChild2Repository extends JpaRepository<Child2, Long> {

    List<Child2> findAllByParentId(Long parentId);
}
