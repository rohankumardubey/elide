/*
 * Copyright 2015, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package example;

import com.yahoo.elide.annotation.Audit;
import com.yahoo.elide.annotation.Exclude;
import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.Paginate;
import com.yahoo.elide.annotation.ReadPermission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Model for authors.
 * <p>
 * <b>CAUTION: DO NOT DECORATE IT WITH {@link Builder}, which hides its no-args constructor. This will result in
 * runtime error at places such as {@code entityClass.newInstance();}</b>
 */
@Entity
@Table(name = "author")
@Include
@Paginate
@Audit(action = Audit.Action.CREATE,
        operation = 10,
        logStatement = "{0}",
        logExpressions = {"${author.name}"})
public class Author {
    @Setter
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Exclude
    private String naturalKey = UUID.randomUUID().toString();

    @Getter @Setter
    private String name;

    @Setter
    private Collection<Book> books = new ArrayList<>();

    @ManyToMany(mappedBy = "authors")
    public Collection<Book> getBooks() {
        return books;
    }

    @Setter
    private String homeAddress;

    @ReadPermission(expression = "Prefab.Role.None")
    public String getHomeAddress() {
        return homeAddress;
    }

    @Override
    public int hashCode() {
        return naturalKey.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Author && ((Author) obj).naturalKey.equals(naturalKey);
    }

    @Override
    public String toString() {
        return "Author: " + id;
    }
}
