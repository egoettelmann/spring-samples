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

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(final Parent parent) {
        this.parent = parent;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }
}
