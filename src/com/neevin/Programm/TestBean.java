package com.neevin.Programm;

public class TestBean {
    private String name;
    private int age;

    public TestBean() {
        this.name = "";
        this.age = 0;
    }

    public TestBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("[TestBean: name='%s', age=%d]", name, age);
    }
}
