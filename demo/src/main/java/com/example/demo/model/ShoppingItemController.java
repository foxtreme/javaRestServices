package com.example.demo.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.EntityMode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
public class ShoppingItemController {
    private final ShoppingItemRepository repository;

    ShoppingItemController(ShoppingItemRepository repository){
        this.repository = repository;
    }

    // Agregate root
    // tag::get-aggregate-root[]
    @GetMapping("/shoppingItems")
    CollectionModel<EntityModel<ShoppingItem>> all() {
        List<EntityModel<ShoppingItem>> shoppingItems = repository.findAll().stream()
        .map(shoppingItem -> EntityModel.of(shoppingItem,
        linkTo(methodOn(ShoppingItemController.class).one(shoppingItem.getId())).withSelfRel(),
        linkTo(methodOn(ShoppingItemController.class).all()).withRel("shoppingItems")))
        .collect(Collectors.toList());

        return CollectionModel.of(shoppingItems, linkTo(methodOn(ShoppingItemController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/shoppingItems")
    ShoppingItem newShoppingItem(@RequestBody ShoppingItem newShoppingItem) {
        return repository.save(newShoppingItem);
    }

    // Single item

    @GetMapping("/shoppingItems/{id}")
    EntityModel<ShoppingItem> one(@PathVariable Long id){
        ShoppingItem shoppingItem = repository.findById(id) //
        .orElseThrow(() -> new ShoppingItemNotFoundException(id));

        return EntityModel.of(shoppingItem, //
        linkTo(methodOn(ShoppingItemController.class).one(id)).withSelfRel(),
        linkTo(methodOn(ShoppingItemController.class).all()).withRel("shoppingItems"));
    }

    @PutMapping("/shoppingItems/{id}")
    ShoppingItem replaceShoppingItem(@RequestBody ShoppingItem newShoppingItem, @PathVariable Long id){
        return repository.findById(id)
        .map(shoppingItem -> {
            shoppingItem.setName(newShoppingItem.getName());
            shoppingItem.setQuantity(newShoppingItem.getQuantity());
            return repository.save(shoppingItem);
        })
        .orElseGet(() -> {
            newShoppingItem.setId(id);
            return repository.save(newShoppingItem);
        });
    }

    @DeleteMapping("/shoppingItems/{id}")
    void deleteShoppingItem(@PathVariable Long id){
        repository.deleteById(id);
    }
}
