package cn.zain.service.impl;

import cn.zain.model.entity.RobotSceneWord;
import cn.zain.service.WordSegementService;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Ansj分词
 * @author Zain
 */
public class IkWordSegmentServiceImpl implements WordSegementService {
    @Override
    public List<String> wordCutting(String sentence, RobotSceneWord robotSceneWord) throws URISyntaxException, IOException {
        List<String> result = new ArrayList<>();
        StringReader re = new StringReader(sentence);
        Configuration configuration = DefaultConfig.getInstance();
        configuration.setUseSmart(true);
        Collection<String> words = new ArrayList<>();
        words.add(robotSceneWord.getWords());
        Dictionary.initial(configuration);
        Dictionary.getSingleton().addWords(words);

        IKSegmenter ik = new IKSegmenter(re, configuration);

        Lexeme lex;
        while((lex=ik.next()) != null){
            result.add(lex.getLexemeText());
        }
        result.add("--------------ik分词");
        return result;
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
