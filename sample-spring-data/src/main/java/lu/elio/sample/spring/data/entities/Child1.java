package lu.elio.sample.spring.data.entities;

import javax.persistence.*;

@Entity
public class Child1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private Parent parent;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public Child1 setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Child1 setName(final String name) {
        this.name = name;
        return this;
    }

    public Parent getParent() {
        return parent;
    }

    public Child1 setParent(final Parent parent) {
        this.parent = parent;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Child1 setVersion(final Long version) {
        this.version = version;
        return this;
    }
}
