package com.fandexian.tongxue.Bean;

import java.sql.Timestamp;

/**
 * Created by fandexian on 16/4/13.
 */
public class GoodsDetailMessage {
    private  int goodsId;
    private  String userPhone;
    private  String goodsCampus;
    private  String goodsName;
    private  Timestamp releaseTime;
    private  Timestamp soldDate;
    private  float goodsPrice;
    private  String goodsDescription;
    private  int goodsStatus;
    private  int minorCategoryId;
    private  String tradeAddress;
    private  String contactTel;
    private  String contactQq;
    private  String contactWeChat;
    private  String goodsImg1;
    private  String goodsImg2;
    private  String goodsImg3;
    private  String goodsImg4;

    public GoodsDetailMessage() {
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getGoodsCampus() {
        return goodsCampus;
    }

    public void setGoodsCampus(String goodsCampus) {
        this.goodsCampus = goodsCampus;
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

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = Timestamp.valueOf(releaseTime);
    }

    public Timestamp getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Timestamp soldDate) {
        this.soldDate = soldDate;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = Float.parseFloat(goodsPrice);
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public int getMinorCategoryId() {
        return minorCategoryId;
    }

    public void setMinorCategoryId(int minorCategoryId) {
        this.minorCategoryId = minorCategoryId;
    }

    public String getTradeAddress() {
        return tradeAddress;
    }

    public void setTradeAddress(String tradeAddress) {
        this.tradeAddress = tradeAddress;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    public String getContactWeChat() {
        return contactWeChat;
    }

    public void setContactWeChat(String contactWeChat) {
        this.contactWeChat = contactWeChat;
    }

    public String getGoodsImg1() {
        return goodsImg1;
    }

    public void setGoodsImg1(String goodsImg1) {
        this.goodsImg1 = goodsImg1;
    }

    public String getGoodsImg2() {
        return goodsImg2;
    }

    public void setGoodsImg2(String goodsImg2) {
        this.goodsImg2 = goodsImg2;
    }

    public String getGoodsImg3() {
        return goodsImg3;
    }

    public void setGoodsImg3(String goodsImg3) {
        this.goodsImg3 = goodsImg3;
    }

    public String getGoodsImg4() {
        return goodsImg4;
    }

    public void setGoodsImg4(String goodsImg4) {
        this.goodsImg4 = goodsImg4;
    }
}
