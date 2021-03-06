/*
 * Copyright 2018, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */

package example.models;

import com.yahoo.elide.annotation.CreatePermission;
import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.UpdatePermission;
import com.yahoo.elide.graphql.subscriptions.annotations.Subscription;
import com.yahoo.elide.graphql.subscriptions.annotations.SubscriptionField;
import example.checks.AdminCheck;
import lombok.Data;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Include
@Data
@Subscription
public class Post {
    @Id
    private long id;

    @Column(nullable = false)
    @SubscriptionField
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @SubscriptionField
    private Date date;

    @CreatePermission(expression = AdminCheck.USER_IS_ADMIN)
    @UpdatePermission(expression = AdminCheck.USER_IS_ADMIN)
    private boolean abusiveContent;
}
