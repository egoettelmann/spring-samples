package lu.elio.sample.spring.data.services;

import lu.elio.sample.spring.data.repositories.IChild1Repository;
import lu.elio.sample.spring.data.repositories.IChild2Repository;
import lu.elio.sample.spring.data.repositories.IParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Parent repository service.
 */
@Service
public class SampleService {

    /**
     * The Parent Repository
     */
    private IParentRepository parentRepository;

    /**
     * The Child1 Repository
     */
    private IChild1Repository child1Repository;

    /**
     * The Child2 Repository
     */
    private IChild2Repository child2Repository;

    /**
     * Instantiates a new Parent repository service.
     *
     * @param parentRepository the parent repository
     * @param child1Repository the child 1 repository
     * @param child2Repository the child 2 repository
     */
    @Autowired
    public SampleService(final IParentRepository parentRepository,
                         final IChild1Repository child1Repository,
                         final IChild2Repository child2Repository
    ) {
        this.parentRepository = parentRepository;
        this.child1Repository = child1Repository;
        this.child2Repository = child2Repository;
    }

}