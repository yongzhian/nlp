package cn.zain.service.impl;

import cn.zain.model.entity.RobotSceneWord;
import cn.zain.service.WordSegementService;
import org.apache.commons.lang3.StringUtils;
import org.fnlp.ml.types.Dictionary;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.util.exception.LoadModelException;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * 复旦fnlp分词
 *
 * @author Zain
 */
public class FnlpWordSegmentServiceImpl implements WordSegementService {
    private static String DIC_PATH_SEG = "/fudan/seg.m";
    private static String DIC_PATH_DIC = "/fudan/fudan_Dic.txt";

    private static FnlpDictionary BASE_DICT;
    private static CWSTagger CWS_TAGGER;

    static {
        URL segUrl = FnlpWordSegmentServiceImpl.class.getResource(DIC_PATH_SEG);
        URL dicUrl = FnlpWordSegmentServiceImpl.class.getResource(DIC_PATH_DIC);
        try {
            CWS_TAGGER = new CWSTagger(segUrl.getPath());
            BASE_DICT = new FnlpDictionary(dicUrl.getPath()); //得到基础库
        } catch (LoadModelException e) {
            throw new RuntimeException("加载模型文件失败..");
        } catch (IOException e) {
            throw new RuntimeException("加载基础词库失败..");
        }
    }

    @Override
    public List<String> wordCutting(String sentence, RobotSceneWord robotSceneWord) throws Exception {

        Dictionary dictionary = BASE_DICT.deepClone();
        //添加私有词库
        if(null != robotSceneWord && StringUtils.isNoneBlank(robotSceneWord.getWords())){
            dictionary.add(robotSceneWord.getWords(), "nc");
            dictionary.add("打开卧室");
            dictionary.add("打开电视");
            dictionary.add("有限公司");
            dictionary.add("永和服装");
            dictionary.add("超级大的台风");
            CWS_TAGGER.setDictionary(dictionary);
        }


        //空格隔开的分词串
        return Arrays.asList(CWS_TAGGER.tag(sentence).split(" "));
    }

    @Override
    public List  segmentSentence(String sentence, String robotSn) {
        return null;
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

class FnlpDictionary extends Dictionary implements Cloneable,Serializable {
    public FnlpDictionary(String path) throws IOException {
        super(path, false);
    }

    public FnlpDictionary deepClone() throws CloneNotSupportedException {
        FnlpDictionary clone = (FnlpDictionary) super.clone();
        FnlpDictionary fnlpDictionary = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            // 将流序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            fnlpDictionary = (FnlpDictionary) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return fnlpDictionary;
    }
}