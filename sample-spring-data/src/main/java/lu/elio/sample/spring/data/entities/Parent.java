package lu.elio.sample.spring.data.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Version
    private Long version;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child1> children1;

    @OneToMany(mappedBy = "parent")
    private List<Child2> children2;

    public Long getId() {
        return id;
    }

    public Parent setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Parent setName(final String name) {
        this.name = name;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Parent setVersion(final Long version) {
        this.version = version;
        return this;
    }

    public List<Child1> getChildren1() {
        return children1;
    }

    public Parent setChildren1(final List<Child1> children1) {
        this.children1 = children1;
        return this;
    }

    public List<Child2> getChildren2() {
        return children2;
    }

    public Parent setChildren2(final List<Child2> children2) {
        this.children2 = children2;
        return this;
    }
}
