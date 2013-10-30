package com.example.FoodPlanner.food;

/**
 * Created with IntelliJ IDEA.
 * User: Kleino
 * Date: 10/30/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class FoodItem {
    private String name;
    private int cals;

    public FoodItem(String name, int cals) {
        this.name = name;
        this.cals = cals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCals() {
        return cals;
    }

    public void setCals(int cals) {
        this.cals = cals;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
