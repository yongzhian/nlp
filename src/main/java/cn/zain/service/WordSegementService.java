package cn.zain.service;

import cn.zain.model.entity.RobotSceneWord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * http://www.cnblogs.com/lishanyang/p/6017155.html
 *
 * @author Zain
 */
public interface WordSegementService {

    Logger logger = LogManager.getLogger();

    /**
     * 功能说明：基于RobotSceneWord对句子进行切词
     *
     * @param sentence
     * @param robotSceneWord
     * @return
     */
    List<String> wordCutting(String sentence, RobotSceneWord robotSceneWord) throws Exception;

    /**
     * 功能说明：分词
     *
     * @return
     */
    List segmentSentence(String sentence, String robotSn);

    /**
     * 功能说明: 添加自定义词汇到RobotSceneWord
     *
     * @param words
     * @param robotSceneWord
     * @return
     */
    boolean addWords2Robot(List<String> words, RobotSceneWord robotSceneWord);

    /**
     * 功能说明: 删除自定义词汇到RobotSceneWord
     *
     * @param words
     * @param robotSceneWord
     * @return
     */
    boolean deleteWords2Robot(List<String> words, RobotSceneWord robotSceneWord);


}
