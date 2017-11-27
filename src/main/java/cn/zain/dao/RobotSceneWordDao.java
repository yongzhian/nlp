package cn.zain.dao;

import cn.zain.model.entity.RobotSceneWord;

import java.util.List;

/**
 * @author Zain
 */
public interface RobotSceneWordDao {

    /**
     * 功能说明：根据ID查询RobotSceneWord
     *
     * @param id
     * @return
     */
    RobotSceneWord selectByPrimaryKey(Long id);

    /**
     * 功能说明 ： 根据robot_id查询自定义词信息
     *
     * @param robotId
     * @return
     */
    List<RobotSceneWord> getRobotSceneWordList(String robotId);
}