
package com.shopjava.app.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "items")
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "price")
    private Double price;

    public ShopItem() {

    }

    public ShopItem(String title, String description, Double weight, Double price) {
        this.title = title;
        this.description = description;
        this.weight = weight;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof ShopItem)) return false;

        ShopItem another = (ShopItem) obj;

        if (this.getId() != null && another.getId() != null) {
            if (!this.getId().equals(another.getId())) {
                return false;
            }
        }

        if (this.getTitle() != null && another.getTitle() != null) {
            if (!this.getTitle().equals(another.getTitle())) {
                return false;
            }
        }

        if (this.getDescription() != null && another.getDescription() != null) {
            if (!this.getDescription().equals(another.getDescription())) {
                return false;
            }
        }

        return
            this.getWeight().doubleValue() == another.getWeight().doubleValue() &&
            this.getPrice().doubleValue() == another.getPrice().doubleValue();
    }

    @Override
    public String toString() {
        return "ShopItem [id=" + getId()
            + ", title=" + getTitle()
            + ", description=" + getDescription()
            + ", weight=" + getWeight()
            + ", price=" + getPrice();
    }
}
