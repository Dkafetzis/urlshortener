package xyz.palamari.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@Entity
public class RedirectUrl extends PanacheEntityBase {

    @Id
    @Column(name = "urlid", nullable = false, updatable = false)
    public String id;

    public URI redirectUrl;

    public int hits;

    @ManyToOne
    @JoinColumn(name = "userid")
    public RedirectUser redirectUser;

    public RedirectUrl() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.hits = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RedirectUrl that)) return false;
        return hits == that.hits && Objects.equals(id, that.id) && Objects.equals(redirectUrl, that.redirectUrl) && Objects.equals(redirectUser, that.redirectUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, redirectUrl, hits, redirectUser);
    }
}
