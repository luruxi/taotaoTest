package com.taotao.mapper;

import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbItemParamMapper {
    int countByExample(TbItemParamExample example);

    int deleteByExample(TbItemParamExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItemParam record);

    int insertSelective(TbItemParam record);

    List<TbItemParam> selectByExampleWithBLOBs(TbItemParamExample example);

    List<TbItemParam> selectByExample(TbItemParamExample example);
    
    List<TbItemParam> getItemParamList(Map<String,Integer> map);
    
    List<TbItemParam> getItemParamListByCid(Map<String,Long> map);

    TbItemParam selectByPrimaryKey(Long id);
    
    long countAllList();

    int updateByExampleSelective(@Param("record") TbItemParam record, @Param("example") TbItemParamExample example);

    int updateByExampleWithBLOBs(@Param("record") TbItemParam record, @Param("example") TbItemParamExample example);

    int updateByExample(@Param("record") TbItemParam record, @Param("example") TbItemParamExample example);

    int updateByPrimaryKeySelective(TbItemParam record);

    int updateByPrimaryKeyWithBLOBs(TbItemParam record);

    int updateByPrimaryKey(TbItemParam record);
}