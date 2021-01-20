package com.miaoshaproject.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by kk on 2021/1/2
 */
public class AdModel {
    private Integer id;
    private String sponsor;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Map<Integer, List<AdModel>> relatedAd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<Integer, List<AdModel>> getRelatedAd() {
        return relatedAd;
    }

    public void setRelatedAd(Map<Integer, List<AdModel>> relatedAd) {
        this.relatedAd = relatedAd;
    }
}
