package com.example.demo.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class ShoppingItem {
    private @Id @GeneratedValue Long id;
    private String name;
    private int quantity;

    ShoppingItem() {
    }

    ShoppingItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (! (o instanceof ShoppingItem))
            return false;
        ShoppingItem shoppingItem = (ShoppingItem) o;
        return Objects.equals(this.id, shoppingItem.id) && Objects.equals(this.name, shoppingItem.name) && Objects.equals(this.quantity, shoppingItem.quantity);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.quantity);
    }

    @Override
    public String toString(){
        return "ShippingItem{id="+this.id+", name="+this.name+", quantity="+quantity+"}";
    }
}