package com.demo.design.abstractBuilder.pojo;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class Pizza {
    public enum Topping{ham,mushroom,onion};
    final Set<Topping> toppings;
    abstract static class Builder<T extends Builder<T>>{
        EnumSet<Topping> toppings=EnumSet.noneOf(Topping.class);
        public T addTopping(Topping topping){
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }
        public abstract Pizza build();
        protected abstract T self();
    }
    Pizza(Builder<?> builder){
        this.toppings=builder.toppings.clone();
    }
}
