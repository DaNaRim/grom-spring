package com.lesson5;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {
    private Long id;
    private String description;

    public Item() {
    }

    public Item(String description) {
        this.description = description;
    }

    public Item(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    @Id
    @SequenceGenerator(name = "ITEM_SEQ", sequenceName = "ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ")
    @Column(name = "ITEM_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "DESCRIPTION")
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
