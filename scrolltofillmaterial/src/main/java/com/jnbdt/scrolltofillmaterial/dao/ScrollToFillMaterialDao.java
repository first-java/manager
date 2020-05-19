package com.jnbdt.scrolltofillmaterial.dao;

import com.jnbdt.scrolltofillmaterial.entity.ScrollToFillMaterial;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ：FirstJava
 * @date ：Created in 2020/5/16 9:53
 * @description：晶能半导体卷轴蓝膜补料
 * @version: 1.0
 * @Copyright © 2020 by FirstJava
 */
@Repository
public interface ScrollToFillMaterialDao {
    /**
     * 晶能半导体卷轴补料
     *
     * @param binUnitId 蓝膜号
     * @return 静电袋信息
     * @time 2020年4月29日 下午2:13:30
     */
    Map<String, Object> selectBinUnitId(@Param("binUnitId") String binUnitId);

    /**
     * 晶能半导体卷轴补料
     *
     * @param barcode 卷轴号
     * @return 卷轴号信息
     * @time 2020年4月29日 下午2:13:30
     */
    Map<String, Object> selectBarcode(@Param("barcode") String barcode);

    /**
     * 保存晶能半导体卷轴补料数据
     *
     * @param scrollToFillMaterial
     * @return 成功
     * @time 2020年4月29日 下午2:13:30
     */
    boolean saveBarcodeFedBatch(List<ScrollToFillMaterial> scrollToFillMaterial);

    /**
     * 补料后，更新货架上蓝膜的数量
     * @param qty
     * @param binUnitId
     * @return 成功
     * @time 2020年4月29日 下午2:13:30
     */
    boolean updateProductQty(@Param("qty") Integer qty,@Param("binUnitId") String binUnitId);


    /**
     * 补料后，更新货架上蓝膜的数量
     * @param qty
     * @param binUnitId
     * @return 成功
     * @time 2020年4月29日 下午2:13:30
     */
    boolean updateBarcodeQty(@Param("qty") Integer qty,@Param("binUnitId") String binUnitId);
}