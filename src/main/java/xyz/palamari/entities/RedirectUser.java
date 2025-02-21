package xyz.palamari.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class RedirectUser extends PanacheEntityBase {

    @Id
    @Column(name = "userid", nullable = false, updatable = false)
    public UUID id;

    public String username;

    @OneToMany(mappedBy = "redirectUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<RedirectUrl> redirectUrls;

    public RedirectUser() {
        this.id = UUID.randomUUID();
        this.redirectUrls = List.of();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RedirectUser that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(username, that.username)
                && Objects.equals(redirectUrls, that.redirectUrls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, redirectUrls);
    }
}
