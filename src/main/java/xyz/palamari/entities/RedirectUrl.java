package xyz.palamari.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.net.URI;
import java.util.UUID;

@Entity
public class RedirectUrl extends PanacheEntityBase {

    @Id
    @Column(name = "urlid", nullable = false, updatable = false)
    public UUID id;

    public URI redirectUrl;

    @ManyToOne
    @JoinColumn(name = "userid")
    public RedirectUser redirectUser;

    public RedirectUrl() {
        this.id = UUID.randomUUID();
    }
}
