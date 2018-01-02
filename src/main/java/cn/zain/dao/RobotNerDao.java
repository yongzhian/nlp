package cn.zain.dao;

import cn.zain.model.entity.RobotNer;
import cn.zain.model.entity.RobotNerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RobotNerDao {
    List<RobotNer> selectByExample(RobotNerExample example);

    RobotNer selectByPrimaryKey(Long id);

    List<RobotNer> selectRobotNerList(@Param("sentence") String sentence,@Param("robotSn") String robotSn);
}