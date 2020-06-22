
package com.okunderenko.app.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    //@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(
        name = "items_orders",
        joinColumns = @JoinColumn(
            name = "order_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "item_id",
            referencedColumnName = "id"
        )
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @Cascade({
//        org.hibernate.annotations.CascadeType.ALL,
//        org.hibernate.annotations.CascadeType.DELETE_ORPHAN
//    })
    private List<ShopItem> items;

    @JoinColumn(name = "user_id")
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    }, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinTable(
//        name = "users",
//        joinColumns = @JoinColumn(name = "id")//,
//        //inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
    private User user;

    @Column(name = "comment")
    private String comment;

    public Order() {
        this.items = new ArrayList<>();
    }

    public Order(User user, List<ShopItem> items, String comment) {
        this();
        this.user = user;
        this.items = items;
        this.comment = comment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public void setItems(List<ShopItem> items) {
        this.items = items;
    }

    public void addItem(ShopItem item) {
        items.add(item);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
