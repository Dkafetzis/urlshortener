package xyz.palamari.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class RedirectUser extends PanacheEntityBase {

    @Id
    @Column(name = "userid", nullable = false, updatable = false)
    public UUID id;

    public String username;

    @OneToMany(mappedBy = "redirectUser", cascade = CascadeType.ALL)
    public List<RedirectUrl> redirectUrls;

    public RedirectUser() {
        this.id = UUID.randomUUID();
        this.redirectUrls = List.of();
    }
}
