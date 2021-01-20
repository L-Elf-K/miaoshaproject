package com.miaoshaproject.controller.Emvippower;

/**
 * Created by kk on 2021/1/19
 */
public enum Emvippower {
    LEVEL_1(1, new String[]{"vip标识","每周一张5元购物券"}),
    LEVEL_2(2,new String[]{"vip标识","每周一张10元购物券"}),
    LEVEL_3(3,new String[]{"vip标识","每周一张10元购物券","限定商品专属打折优惠"}),
    LEVEL_4(4,new String[]{"vip标识","每周一张10元购物券","限定商品专属打折优惠","免除广告"})
    ;
    private Integer level;
    private String[] power;
    private Emvippower(Integer level, String[] power) {
        this.level = level;
        this.power = power;
    }

    public Integer getLevel() {
        return level;
    }

    public void setPower(String[] power) {
        this.power = power;
    }

    public String[] getPower() {
        return power;
    }
}
