package lu.elio.sample.spring.data;

import lu.elio.sample.spring.data.entities.Child1;
import lu.elio.sample.spring.data.entities.Child2;
import lu.elio.sample.spring.data.entities.Parent;
import lu.elio.sample.spring.data.repositories.IChild1Repository;
import lu.elio.sample.spring.data.repositories.IChild2Repository;
import lu.elio.sample.spring.data.repositories.IParentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("h2mem")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SampleSprintDataH2Test {

    @Autowired
    private IParentRepository parentRepository;

    @Autowired
    private IChild1Repository child1Repository;

    @Autowired
    private IChild2Repository child2Repository;

    @Test
    public void testCreateParent() {
        parentRepository.save(buildParent("Parent 1"));
        parentRepository.save(buildParent("Parent 2"));
        parentRepository.save(buildParent("Parent 3"));

        List<Parent> parents = parentRepository.findAll();

        Assert.assertEquals("Wrong size of parent elements", 3, parents.size());
    }

    @Test
    public void testSaveWithChildrenCascadeAll() {
        Parent parent1 = buildParent("Parent 1");
        parent1.setChildren1(buildChildren1(parent1, "Child 1.1"));
        parentRepository.save(parent1);

        Parent parent2 = buildParent("Parent 2");
        parent2.setChildren1(buildChildren1(parent2, "Child 2.1", "Child 2.2"));
        parentRepository.save(parent2);

        Parent parent3 = buildParent("Parent 3");
        parent3.setChildren1(buildChildren1(parent3, "Child 3.1", "Child 3.2", "Child 3.3"));
        parentRepository.save(parent3);

        List<Parent> parents = parentRepository.findAll();
        List<Child1> allChildren1 = child1Repository.findAll();
        List<Child1> parent1Children1 = child1Repository.findAllByParentId(parent1.getId());
        List<Child1> parent2Children1 = child1Repository.findAllByParentId(parent2.getId());
        List<Child1> parent3Children1 = child1Repository.findAllByParentId(parent3.getId());

        Assert.assertEquals("Wrong size of parent elements", 3, parents.size());
        Assert.assertEquals("Wrong size of child elements", 6, allChildren1.size());
        Assert.assertEquals("Wrong size of parent1 child elements", 1, parent1Children1.size());
        Assert.assertEquals("Wrong size of parent2 child elements", 2, parent2Children1.size());
        Assert.assertEquals("Wrong size of parent3 child elements", 3, parent3Children1.size());
    }

    @Test
    public void testSaveWithChildrenNoCascadeFails() {
        Parent parent1 = buildParent("Parent 1");
        parent1.setChildren2(buildChildren2(parent1, "Child 1.1"));
        parentRepository.save(parent1);

        Parent parent2 = buildParent("Parent 2");
        parent2.setChildren2(buildChildren2(parent2, "Child 2.1", "Child 2.2"));
        parentRepository.save(parent2);

        Parent parent3 = buildParent("Parent 3");
        parent3.setChildren2(buildChildren2(parent3, "Child 3.1", "Child 3.2", "Child 3.3"));
        parentRepository.save(parent3);

        List<Parent> parents = parentRepository.findAll();
        List<Child2> allChildren2 = child2Repository.findAll();

        Assert.assertEquals("Wrong size of parent elements", 3, parents.size());
        Assert.assertEquals("Children should not be saved because of no Cascade property", 0, allChildren2.size());
    }

    @Test
    public void testSaveWithChildrenNoCascadeSuccess() {
        Parent parent1 = buildParent("Parent 1");
        parent1.setChildren2(buildChildren2(parent1, "Child 1.1"));
        parentRepository.save(parent1);
        child2Repository.saveAll(parent1.getChildren2());

        Parent parent2 = buildParent("Parent 2");
        parent2.setChildren2(buildChildren2(parent2, "Child 2.1", "Child 2.2"));
        parentRepository.save(parent2);
        child2Repository.saveAll(parent2.getChildren2());

        Parent parent3 = buildParent("Parent 3");
        parent3.setChildren2(buildChildren2(parent3, "Child 3.1", "Child 3.2", "Child 3.3"));
        parentRepository.save(parent3);
        child2Repository.saveAll(parent3.getChildren2());

        List<Parent> parents = parentRepository.findAll();
        List<Child2> allChildren2 = child2Repository.findAll();
        List<Child2> parent1Children2 = child2Repository.findAllByParentId(parent1.getId());
        List<Child2> parent2Children2 = child2Repository.findAllByParentId(parent2.getId());
        List<Child2> parent3Children2 = child2Repository.findAllByParentId(parent3.getId());

        Assert.assertEquals("Wrong size of elements", 3, parents.size());
        Assert.assertEquals("Wrong size of child elements", 6, allChildren2.size());
        Assert.assertEquals("Wrong size of parent1 child elements", 1, parent1Children2.size());
        Assert.assertEquals("Wrong size of parent2 child elements", 2, parent2Children2.size());
        Assert.assertEquals("Wrong size of parent3 child elements", 3, parent3Children2.size());
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
