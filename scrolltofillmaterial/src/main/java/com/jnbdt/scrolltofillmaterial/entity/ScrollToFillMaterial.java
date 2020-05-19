package com.jnbdt.scrolltofillmaterial.entity;

/**
 * @author ：FirstJava
 * @date ：Created in 2020/5/18 13:47
 * @description：晶能半导体蓝膜卷轴补料
 * @version: 1.0
 * @Copyright © 2020 by FirstJava
 */

public class ScrollToFillMaterial {
    private String unitIdQty;
    private String binUnitId;
    private String qty;
    private String lotHead;
    private String barcode;
    private String materialName;
    private String workId;

    public String getUnitIdQty() {
        return unitIdQty;
    }

    public void setUnitIdQty(String unitIdQty) {
        this.unitIdQty = unitIdQty;
    }

    public String getBinUnitId() {
        return binUnitId;
    }

    public void setBinUnitId(String binUnitId) {
        this.binUnitId = binUnitId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getLotHead() {
        return lotHead;
    }

    public void setLotHead(String lotHead) {
        this.lotHead = lotHead;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
