package com.hswift.erp.service.materialProperty;

import com.alibaba.fastjson.JSONObject;
import com.hswift.erp.constants.BusinessConstants;
import com.hswift.erp.datasource.entities.MaterialProperty;
import com.hswift.erp.datasource.entities.MaterialPropertyExample;
import com.hswift.erp.datasource.entities.User;
import com.hswift.erp.datasource.mappers.MaterialPropertyMapper;
import com.hswift.erp.datasource.mappers.MaterialPropertyMapperEx;
import com.hswift.erp.exception.hswiftException;
import com.hswift.erp.service.log.LogService;
import com.hswift.erp.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class MaterialPropertyService {
    private Logger logger = LoggerFactory.getLogger(MaterialPropertyService.class);

    @Resource
    private MaterialPropertyMapper materialPropertyMapper;

    @Resource
    private MaterialPropertyMapperEx materialPropertyMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    public MaterialProperty getMaterialProperty(long id)throws Exception {
        MaterialProperty result=null;
        try{
            result=materialPropertyMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            hswiftException.readFail(logger, e);
        }
        return result;
    }

    public List<MaterialProperty> getMaterialProperty()throws Exception {
        MaterialPropertyExample example = new MaterialPropertyExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialProperty>  list=null;
        try{
            list=materialPropertyMapper.selectByExample(example);
        }catch(Exception e){
            hswiftException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialProperty> select(String name, int offset, int rows)throws Exception {
        List<MaterialProperty>  list=null;
        try{
            list=materialPropertyMapperEx.selectByConditionMaterialProperty(name, offset, rows);
        }catch(Exception e){
            hswiftException.readFail(logger, e);
        }
        return list;
    }

    public Long countMaterialProperty(String name)throws Exception {
        Long  result=null;
        try{
            result=materialPropertyMapperEx.countsByMaterialProperty(name);
        }catch(Exception e){
            hswiftException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterialProperty(JSONObject obj, HttpServletRequest request)throws Exception {
        MaterialProperty materialProperty = JSONObject.parseObject(obj.toJSONString(), MaterialProperty.class);
        int  result=0;
        try{
            result=materialPropertyMapper.insertSelective(materialProperty);
            logService.insertLog("商品属性",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(materialProperty.getNativeName()).toString(), request);
        }catch(Exception e){
            hswiftException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterialProperty(JSONObject obj, HttpServletRequest request)throws Exception {
        MaterialProperty materialProperty = JSONObject.parseObject(obj.toJSONString(), MaterialProperty.class);
        int  result=0;
        try{
            result=materialPropertyMapper.updateByPrimaryKeySelective(materialProperty);
            logService.insertLog("商品属性",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(materialProperty.getNativeName()).toString(), request);
        }catch(Exception e){
            hswiftException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterialProperty(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialPropertyByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialProperty(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialPropertyByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialPropertyByIds(String ids) throws Exception{
        logService.insertLog("商品属性",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int  result=0;
        try{
            result=materialPropertyMapperEx.batchDeleteMaterialPropertyByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            hswiftException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        return 0;
    }
}
