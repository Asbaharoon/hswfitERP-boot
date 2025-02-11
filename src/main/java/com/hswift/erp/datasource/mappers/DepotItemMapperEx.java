package com.hswift.erp.datasource.mappers;

import com.hswift.erp.datasource.entities.*;
import com.hswift.erp.datasource.vo.DepotItemStockWarningCount;
import com.hswift.erp.datasource.vo.DepotItemVo4Stock;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/24 16:59
 */
public interface DepotItemMapperEx {
    List<DepotItem> selectByConditionDepotItem(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("remark") String remark,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByDepotItem(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("remark") String remark);

    List<DepotItemVo4DetailByTypeAndMId> findDetailByTypeAndMaterialIdList(
            @Param("mId") Long mId,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long findDetailByTypeAndMaterialIdCounts(
            @Param("mId") Long mId);

    List<DepotItemVo4WithInfoEx> getDetailList(
            @Param("headerId") Long headerId);

    List<DepotItemVo4WithInfoEx> findByAll(
            @Param("materialParam") String materialParam,
            @Param("endTime") String endTime,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int findByAllCount(
            @Param("materialParam") String materialParam,
            @Param("endTime") String endTime);

    BigDecimal buyOrSaleNumber(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("MId") Long MId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("sumType") String sumType);

    BigDecimal buyOrSalePrice(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("MId") Long MId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("sumType") String sumType);

    BigDecimal inOrOutPrice(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime);

    BigDecimal getStockCheckSum(
            @Param("depotId") Long depotId,
            @Param("mId") Long mId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime);

    DepotItemVo4Stock getStockByParam(
            @Param("depotId") Long depotId,
            @Param("mId") Long mId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("tenantId") Long tenantId);

    /**
     * 通过单据主表id查询所有单据子表数据
     * @param depotheadId
     * @param enableSerialNumber
     * @return
     */
     List<DepotItem> findDepotItemListBydepotheadId(@Param("depotheadId")Long depotheadId,
                                                    @Param("enableSerialNumber")String enableSerialNumber);
     /**
      * 根据单据主表id删除单据子表数据
      * 物理删除，已弃用
      * */
     @Deprecated
     int deleteDepotItemByDepotHeadIds(@Param("depotheadIds")Long []depotHeadIds);
     /**
      * 根据单据主表id删除单据子表数据
      * */
     int batchDeleteDepotItemByDepotHeadIds(@Param("depotheadIds")Long []depotHeadIds);

    int batchDeleteDepotItemByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    List<DepotItem> getDepotItemListListByDepotIds(@Param("depotIds") String[] depotIds);

    List<DepotItem> getDepotItemListListByMaterialIds(@Param("materialIds") String[] materialIds);

    List<DepotItemStockWarningCount> findStockWarningCount(
            @Param("offset") Integer offset,
            @Param("rows") Integer rows,
            @Param("materialParam") String materialParam,
            @Param("depotId") Long depotId);

    int findStockWarningCountTotal(
            @Param("materialParam") String materialParam,
            @Param("depotId") Long depotId);
}
