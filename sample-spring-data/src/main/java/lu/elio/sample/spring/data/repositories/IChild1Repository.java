package lu.elio.sample.spring.data.repositories;

import lu.elio.sample.spring.data.entities.Child1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChild1Repository extends JpaRepository<Child1, Long> {

    List<Child1> findAllByParentId(Long parentId);

}
