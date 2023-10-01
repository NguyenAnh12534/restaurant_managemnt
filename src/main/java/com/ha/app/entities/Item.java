package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.ForeignKey;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.annotations.data.OneToMany;
import com.ha.app.entities.builders.ItemBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Item {
    @Id
    private Integer id;
    private String name;
    private String description;
    private String imageURL;
    private String additionalDetail;
    private Double price;

    @ManyToOne
    private Menu menu;

    @OneToMany(mappedBy = "item", childEntity = OrderItem.class)
    private Set<OrderItem> orderItems = new HashSet<>();


    @ForeignKey(parentClass = Menu.class)
    public int menuId;

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Item( String name, Double price, String description, String imageURL, String additionalDetail) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        this.additionalDetail = additionalDetail;
    }

    public Item() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAdditionalDetail() {
        return additionalDetail;
    }

    public void setAdditionalDetail(String additionalDetail) {
        this.additionalDetail = additionalDetail;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        this.menuId = menu.getId();
    }

    public void load(Item item) {
        this.name = item.getName();
        this.description = item.getDescription() != null ? item.getDescription() : this.description;
        this.price = item.getPrice() != null ? item.getPrice() : this.price;
        this.imageURL = item.getImageURL() != null ? item.getImageURL() : this.imageURL;
        this.additionalDetail = item.getAdditionalDetail() != null ? item.getAdditionalDetail() : this.additionalDetail;
    }

    public static ItemBuilder build() {
        return new ItemBuilder();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: ");
        stringBuilder.append(this.id);
        stringBuilder.append(", Name: ");
        stringBuilder.append(this.name);
        stringBuilder.append(", Description: ");
        stringBuilder.append(this.description);
        stringBuilder.append(", Image URL: ");
        stringBuilder.append(this.imageURL);
        stringBuilder.append(", Price: ");
        stringBuilder.append(this.price);
        stringBuilder.append(", Additional detail: ");
        stringBuilder.append(this.additionalDetail);
        stringBuilder.append(", Menu ID: ");
        stringBuilder.append(this.menuId);
        if (this.menuId != 0) {
            stringBuilder.append(", ");
            stringBuilder.append(this.menu);
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
