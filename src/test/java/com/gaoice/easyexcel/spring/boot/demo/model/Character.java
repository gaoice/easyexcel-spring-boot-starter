package com.gaoice.easyexcel.spring.boot.demo.model;

/**
 * 人物角色
 */
public class Character {

    private String name;

    private Book book;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
