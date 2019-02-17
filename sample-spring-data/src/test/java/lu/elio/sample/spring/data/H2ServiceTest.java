package lu.elio.sample.spring.data;

import lu.elio.sample.spring.data.entities.Child1;
import lu.elio.sample.spring.data.entities.Child2;
import lu.elio.sample.spring.data.entities.Parent;
import lu.elio.sample.spring.data.repositories.IParentRepository;
import lu.elio.sample.spring.data.services.SampleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("h2mem")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class H2ServiceTest {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private IParentRepository parentRepository;

    @Test
    public void testSaveAllInBatch() {
        sampleService.saveAllInBatch(Arrays.asList(
                buildParent("Parent 1"),
                buildParent("Parent 2"),
                buildParent("Parent 3")
        ));

        List<Parent> parents = parentRepository.findAll();

        Assert.assertEquals("Wrong size of parent elements", 3, parents.size());
    }

    @Test
    public void testSaveAllInBatchSameIdWithoutVersion() {
        sampleService.saveAllInBatch(Arrays.asList(
                buildParent("Parent 1").setId(1L),
                buildParent("Parent 2").setId(1L),
                buildParent("Parent 3")
        ));

        List<Parent> parents = parentRepository.findAll();

        Assert.assertEquals("Not specifying a version should consider all elements as new", 3, parents.size());
    }

    @Test
    public void testSaveAllInBatchSameIdWithVersion() {
        sampleService.saveAllInBatch(Arrays.asList(
                buildParent("Parent 1").setId(1L).setVersion(0L),
                buildParent("Parent 2").setId(1L).setVersion(0L),
                buildParent("Parent 3")
        ));

        List<Parent> parents = parentRepository.findAll();

        Assert.assertEquals("Specifying a version should update elements with same id", 2, parents.size());
    }

    @Test
    public void testSaveAllInBatchSameIdWithWrongVersion() {
        try {
            sampleService.saveAllInBatch(Arrays.asList(
                    buildParent("Parent 1").setId(1L).setVersion(0L),
                    buildParent("Parent 2").setId(1L).setVersion(1L),
                    buildParent("Parent 3")
            ));
        } catch (ObjectOptimisticLockingFailureException e) {
            List<Parent> parents = parentRepository.findAll();
            Assert.assertEquals("Specifying a wrong version should prevent update of all elements", 0, parents.size());
            return;
        }
        Assert.fail("ObjectOptimisticLockingFailureException should be thrown");
    }

    @Test
    public void testSaveAllOneByOneSameIdWithWrongVersion() {
        try {
            sampleService.saveAllOneByOne(Arrays.asList(
                    buildParent("Parent 1").setId(1L).setVersion(0L),
                    buildParent("Parent 2").setId(1L).setVersion(1L),
                    buildParent("Parent 3")
            ));
        } catch (ObjectOptimisticLockingFailureException e) {
            List<Parent> parents = parentRepository.findAll();
            Assert.assertEquals("Specifying a wrong version should prevent update of last element", 1, parents.size());
            return;
        }
        Assert.fail("ObjectOptimisticLockingFailureException should be thrown");
    }

    @Test
    public void testSaveAllOneByOneInTransactionSameIdWithWrongVersion() {
        try {
            sampleService.saveAllOneByOneInTransaction(Arrays.asList(
                    buildParent("Parent 1").setId(1L).setVersion(0L),
                    buildParent("Parent 2").setId(1L).setVersion(1L),
                    buildParent("Parent 3")
            ));
        } catch (ObjectOptimisticLockingFailureException e) {
            List<Parent> parents = parentRepository.findAll();
            Assert.assertEquals("Specifying a wrong version should prevent update of all elements", 0, parents.size());
            return;
        }
        Assert.fail("ObjectOptimisticLockingFailureException should be thrown");
    }

    private Parent buildParent(String parentName) {
        Parent parent = new Parent();
        parent.setName(parentName);
        return parent;
    }

    private List<Child1> buildChildren1(Parent parent, String... child1Names) {
        List<Child1> children1 = new ArrayList<>();
        for (String child1Name: child1Names) {
            Child1 child1 = new Child1();
            child1.setName(child1Name);
            if (parent != null) {
                child1.setParent(parent);
            }
            children1.add(child1);
        }
        return children1;
    }

    private List<Child2> buildChildren2(Parent parent, String... child2Names) {
        List<Child2> children2 = new ArrayList<>();
        for (String child1Name: child2Names) {
            Child2 child2 = new Child2();
            child2.setName(child1Name);
            if (parent != null) {
                child2.setParent(parent);
            }
            children2.add(child2);
        }
        return children2;
    }

}
