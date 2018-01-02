package cn.zain.dao;

import cn.zain.model.dto.ScopeNerDicDto;
import cn.zain.model.entity.ScopeNerDic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScopeNerDicDao {

    ScopeNerDic selectByPrimaryKey(Long id);

    List<ScopeNerDicDto> selectScopeNerDicDto();

    List<ScopeNerDicDto> selectScopeNerDicDtoList(@Param("sentence") String sentence);
}