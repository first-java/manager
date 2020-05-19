package com.jnbdt.scrolltofillmaterial.service.impl;

import com.jnbdt.scrolltofillmaterial.dao.ScrollToFillMaterialDao;
import com.jnbdt.scrolltofillmaterial.entity.ScrollToFillMaterial;
import com.jnbdt.scrolltofillmaterial.service.ScrollToFillMaterialService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：FirstJava
 * @date ：Created in 2020/5/16 9:55
 * @description：晶能半导体卷轴蓝膜补料
 * @version: 1.0
 * @Copyright © 2020 by FirstJava
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ScrollToFillMaterialServiceImpl implements ScrollToFillMaterialService {
    @Resource
    ScrollToFillMaterialDao scrollToFillMaterialDao;
    Map<String, Object> data = new HashMap<>(10);

    /**
     * 晶能半导体卷轴补料
     *
     * @param binUnitId 蓝膜号
     * @return 静电袋信息
     * @time 2020年4月29日 下午2:13:30
     */
    @Override
    public Map<String, Object> selectBinUnitId(String binUnitId) {
        return scrollToFillMaterialDao.selectBinUnitId(binUnitId);
    }

    /**
     * 晶能半导体卷轴补料
     *
     * @param barcode 卷轴号
     * @return 静电袋信息
     * @time 2020年4月29日 下午2:13:30
     */
    @Override
    public Map<String, Object> selectBarcode(String barcode) {
        return scrollToFillMaterialDao.selectBarcode(barcode);
    }

    /**
     * 晶能半导体卷轴补料
     *
     * @param scrollToFillMaterial 保存对象
     * @return 保存结果
     * @time 2020年4月29日 下午2:13:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveBarcodeService(ScrollToFillMaterial scrollToFillMaterial) {
        List<ScrollToFillMaterial> binUnitList = new ArrayList<>(10);

        JSONTokener barcode1 = new JSONTokener(scrollToFillMaterial.getBarcode());
        JSONArray arraybarcode1 = (JSONArray) barcode1.nextValue();

        JSONTokener qty1 = new JSONTokener(scrollToFillMaterial.getQty());
        JSONArray arrayqty1 = (JSONArray) qty1.nextValue();
        int q = 0;
        String unit = scrollToFillMaterial.getBinUnitId();
        for (int j = 0; j < arraybarcode1.size(); j++) {
            ScrollToFillMaterial barcodeFedBatch1 = new ScrollToFillMaterial();

            JSONObject barcode21 = arraybarcode1.getJSONObject(j);
            JSONObject qty21 = arrayqty1.getJSONObject(j);

            String barcode = barcode21.getString("barcode");
            String qty = qty21.getString("qty");

            barcodeFedBatch1.setBarcode(scrollToFillMaterial.getBarcode());
            barcodeFedBatch1.setQty(qty);
            barcodeFedBatch1.setBinUnitId(unit);
            barcodeFedBatch1.setBarcode(barcode);
            barcodeFedBatch1.setMaterialName(scrollToFillMaterial.getMaterialName());
            barcodeFedBatch1.setLotHead(scrollToFillMaterial.getLotHead());
            q += Integer.parseInt(qty);
            barcodeFedBatch1.setWorkId(scrollToFillMaterial.getWorkId());
            binUnitList.add(barcodeFedBatch1);

        }

        boolean flag;
        boolean flagShelf;
        try {
            flag = saveBarcodeFedBatch(binUnitList);
            String start = "S";
            if (unit.startsWith(start)) {
                flagShelf = updateProductQty(q, unit);
            } else {
                flagShelf = updateBarcodeQty(q, unit);
            }
            if (flag && flagShelf) {
                data.put("msg", "保存成功！");
            } else {
                data.put("msg", "保存失败");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            data.put("msg", "数据库异常" + e.toString());
        }
        return data;
    }

    /**
     * 保存晶能半导体卷轴补料数据
     *
     * @param scrollToFillMaterial@return 成功
     * @time 2020年4月29日 下午2:13:30
     */
    @Override
    public boolean saveBarcodeFedBatch(List<ScrollToFillMaterial> scrollToFillMaterial) {
        return scrollToFillMaterialDao.saveBarcodeFedBatch(scrollToFillMaterial);
    }

    /**
     * 补料后，更新货架上蓝膜的数量
     *
     * @param qty
     * @param binUnitId
     * @return 成功
     * @time 2020年4月29日 下午2:13:30
     */
    @Override
    public boolean updateProductQty(Integer qty, String binUnitId) {
        return scrollToFillMaterialDao.updateProductQty(qty, binUnitId);
    }

    /**
     * 补料后，更新货架上蓝膜的数量
     *
     * @param qty
     * @param binUnitId
     * @return 成功
     * @time 2020年4月29日 下午2:13:30
     */
    @Override
    public boolean updateBarcodeQty(Integer qty, String binUnitId) {
        return scrollToFillMaterialDao.updateBarcodeQty(qty, binUnitId);
    }
}

