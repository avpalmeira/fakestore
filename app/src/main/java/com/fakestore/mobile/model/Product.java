package com.fakestore.mobile.model;

public class Product {

    private int Item1;
    private double Item4;
    private String Item2, Item3;

    public String getItem3() {
        return Item3;
    }

    public void setItem3(String item3) {
        Item3 = item3;
    }

    public String getItem2() {
        return Item2;
    }

    public void setItem2(String item2) {
        Item2 = item2;
    }

    public double getItem4() {
        return Item4;
    }

    public void setItem4(double item4) {
        Item4 = item4;
    }

    public int getItem1() {
        return Item1;
    }

    public void setItem1(int item1) {
        Item1 = item1;
    }

    public String toString() {
        String formatter =
                "id: %s\ncategory: %s\nproduct: %s\nprice: %s\n\n";

        return String.format(formatter,
                getItem1(),
                getItem2(),
                getItem3(),
                getItem4());
    }
}
