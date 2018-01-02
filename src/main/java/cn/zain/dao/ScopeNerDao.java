package cn.zain.dao;

import cn.zain.model.entity.ScopeNer;
import cn.zain.model.entity.ScopeNerExample;
import java.util.List;

public interface ScopeNerDao {
    List<ScopeNer> selectByExample(ScopeNerExample example);

    ScopeNer selectByPrimaryKey(Long id);
}