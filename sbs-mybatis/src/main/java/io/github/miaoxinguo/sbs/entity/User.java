package io.github.miaoxinguo.sbs.entity;

import java.util.Date;

public class User {
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
    private Integer age;

    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private Date birthday;

    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * 该属性由 Mybatis-Generator 自动创建，不要直接修改.
     *
     * @mbg.generated
     */
    private Date updateTime;

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
    public Integer getAge() {
        return age;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 该方法由 Mybatis-Generator 自动生成，不要修改.
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}