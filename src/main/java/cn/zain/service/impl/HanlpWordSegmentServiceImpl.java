package cn.zain.service.impl;

import cn.zain.model.entity.RobotSceneWord;
import cn.zain.service.WordSegementService;
import cn.zain.toolkit.hanlp.SegmentExtend;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hanlp分词
 *
 * http://hanlp.linrunsoft.com/services.html 相关下载
 * http://hanlp.linrunsoft.com/doc.html 开发文档
 * 默认配置hanlp.properties
 * 由于资源文件较大，自行下载 http://www.hankcs.com/nlp/hanlp.html
 * @author Zain
 */
@Deprecated
public class HanlpWordSegmentServiceImpl implements WordSegementService {
    @Override
    public List<String> wordCutting(String sentence, RobotSceneWord robotSceneWord) throws URISyntaxException {
        //无须词典文件
        List<String> result = new ArrayList<>();
        CustomDictionary.add("打开电视");
        List<com.hankcs.hanlp.seg.common.Term> termList = HanLP.segment(sentence);
        Segment segment = new SegmentExtend(null);
        segment.seg("");
        for (com.hankcs.hanlp.seg.common.Term term : termList) {
            result.add(term.word);
        }
        ViterbiSegment v;
        result.add("----------hanlp分词");
//        for (int i=0, len = user_words.length; i<len && CustomDictionary.trie.size()>0;i++) {
//            CustomDictionary.remove(user_words[i]);
//        }
        return result;
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
