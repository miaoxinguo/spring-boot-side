package io.github.miaoxinguo.sbs.entity;

import java.math.BigDecimal;

public class Account {
    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private int type;

    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private BigDecimal balance;

    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private User user;

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public int getType() {
        return type;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public User getUser() {
        return user;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setUser(User user) {
        this.user = user;
    }
}