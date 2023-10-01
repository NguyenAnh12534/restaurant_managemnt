package com.ha.app.entities.builders;

import com.ha.app.entities.Item;

public class ItemBuilder {
    private String name;
    private String description;
    private Double price;
    private String additionalDetail;
    private String imageURL;


    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemBuilder setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public ItemBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ItemBuilder setAdditionalDetail(String additionalDetail) {
        this.additionalDetail = additionalDetail;
        return this;
    }

    public Item build() {
        return new Item(this.name, this.price, this.description, this.imageURL, this.additionalDetail);
    }

}
