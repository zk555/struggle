package com.gy.struggle.IOTGateConsole.mapper;

import com.gy.struggle.IOTGateConsole.domain.IotGateDB;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IotGateMapper {
    @Insert("insert into rpc_strategy(id,pId,pName,isBigEndian,beginHexVal,lengthFieldOffset,lengthFieldLength,isDataLenthIncludeLenthFieldLenth,exceptDataLenth,port,highControll,content) values(#{id},#{pId},#{pName},#{isBigEndian},#{beginHexVal},#{lengthFieldOffset},#{lengthFieldLength},#{isDataLenthIncludeLenthFieldLenth},#{exceptDataLenth},#{port},#{highControll},#{content})")
    @Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")//设置主键自增
    int insert(IotGateDB strategy);

    @Select("select * from rpc_strategy")
    List<IotGateDB> getAllStrategy();
    @Select("select * from rpc_strategy where pId = #{pId}")
    List<IotGateDB> getStrategyByPid(IotGateDB strategy);

    @Delete("delete from rpc_strategy where pId = #{pId}")
    int delOneStrategyByPID(IotGateDB strategy);
}
