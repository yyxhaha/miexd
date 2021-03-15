package com.demo.design.abstractBuilder;

import com.demo.design.abstractBuilder.pojo.NyPizza;
import com.demo.design.abstractBuilder.pojo.Pizza;


public class Test{
    public static void main(String[] args) {
        NyPizza nyPizza=new NyPizza.Builder(NyPizza.Size.small).
                addTopping(Pizza.Topping.ham).
                addTopping(Pizza.Topping.mushroom).build();
        System.out.println(1);
    }
}