package com.demo.design.abstractBuilder.pojo;

import java.util.Objects;

public class NyPizza extends Pizza{
    public enum Size{small,medium,large}
    private final Size size;
    public static class Builder extends  Pizza.Builder<Builder>{
        private final Size size;
        public Builder(Size size){
            this.size= Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
    private NyPizza(Builder builder){
        super(builder);
        size=builder.size;
    }
}
