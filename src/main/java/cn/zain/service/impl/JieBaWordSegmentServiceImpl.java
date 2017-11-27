package cn.zain.service.impl;

import cn.zain.model.entity.RobotSceneWord;
import cn.zain.service.WordSegementService;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 结巴分词
 * 词典格式：一个词占一行，分三部分---词语+词频（可省略）+词性（可省略）
 * 允许多次加载自定义词典
 * 但每个JiebaSegmenter使用同一个WordDictionary(WordDictionary.getInstance()),如果每个句子分词词典不一致会难实现
 * 故不适合机器人语义解析
 * @author Zain
 */
@Deprecated
public class JieBaWordSegmentServiceImpl implements WordSegementService {
    /**
     * 自定义词典路径，必须.dict结尾
     */
    private static String DIC_BASE_PATH1 = "/jieba/jieba_Dic1.dict";
    private static String DIC_BASE_PATH2 = "/jieba/jieba_Dic2.dict";
    @Override
    public List<String> wordCutting(String sentence, RobotSceneWord robotSceneWord) throws URISyntaxException {
        //基于robotSceneWord生成模型文件,生成词典
        //todo
        JiebaSegmenter segmenter = new JiebaSegmenter();

        URL url1 = JieBaWordSegmentServiceImpl.class.getResource(DIC_BASE_PATH1);
        Path dictPath1 = Paths.get(url1.toURI());


        URL url2 = JieBaWordSegmentServiceImpl.class.getResource(DIC_BASE_PATH2);
        Path dictPath2 = Paths.get(url2.toURI());
        WordDictionary.getInstance().loadUserDict(dictPath2);
        WordDictionary.getInstance().loadUserDict(dictPath1);
        List<String> jiebaResult = segmenter.sentenceProcess(sentence);
        return jiebaResult;
    }

    @Override
    public boolean addWords2Robot(List<String> words, RobotSceneWord robotSceneWord) {
        return false;
    }

    @Override
    public boolean deleteWords2Robot(List<String> words, RobotSceneWord robotSceneWord) {
        return false;
    }
}
