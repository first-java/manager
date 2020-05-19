package com.jnbdt.scrolltofillmaterial.controller;

import com.jnbdt.scrolltofillmaterial.entity.ScrollToFillMaterial;
import com.jnbdt.scrolltofillmaterial.service.ScrollToFillMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：FirstJava
 * @date ：Created in 2020/5/16 9:37
 * @description：晶能半导体卷轴蓝膜补料
 * @version: 1.0
 * @Copyright © 2020 by FirstJava
 */
@Controller
public class ScrollToFillMaterialController {
    Map<String, Object> data = new HashMap<>(10);

    private ScrollToFillMaterialService scrollToFillMaterialService;

    @Autowired
    public void setScrollToFillMaterialService(ScrollToFillMaterialService scrollToFillMaterialService) {
        this.scrollToFillMaterialService = scrollToFillMaterialService;
    }

    @GetMapping("scrollToFillMaterial")
    public String name() {
        return "scrollToFillMaterial";
    }

    @RequestMapping("selectBinUnit")
    @ResponseBody
    public Map<String, Object> selectBinUnit(String binUnitId) {
        Map<String, Object> binUnitList = scrollToFillMaterialService.selectBinUnitId(binUnitId);

        if (binUnitList == null) {
            data.put("code", 1);
            data.put("msg", "未找到[" + binUnitId + "]信息！");
            return data;
        }
        return binUnitList;
    }

    @RequestMapping("selectBarcode")
    @ResponseBody
    public Map<String, Object> selectBarcode(String barcode) {
        Map<String, Object> binUnitList = scrollToFillMaterialService.selectBarcode(barcode);

        if (binUnitList == null) {
            data.put("code", 1);
            data.put("msg", "未找到[" + barcode + "]信息！");
            return data;
        }
        return binUnitList;
    }

    @RequestMapping("saveBarcodeFedBatch")
    @ResponseBody
    public Map<String, Object> saveBarcodeFedBatch(ScrollToFillMaterial scrollToFillMaterial) {

        return scrollToFillMaterialService.saveBarcodeService(scrollToFillMaterial);
    }

}
