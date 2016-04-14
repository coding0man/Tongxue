package com.fandexian.tongxue.Bean;

import java.sql.Timestamp;

/**
 * Created by fandexian on 16/4/13.
 */
public class goodsBriefMessage {
    private int goodsId;
    private String goodsName;
    private Timestamp releaseTime;
    private float goodsPrice;
    private String goodsDescription;
    private String goodsImg1;

    public goodsBriefMessage() {
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getGoodsImg1() {
        return goodsImg1;
    }

    public void setGoodsImg1(String goodsImg1) {
        this.goodsImg1 = goodsImg1;
    }
}
