package cn.zain.service.impl;

import cn.zain.dao.RobotSceneWordDao;
import cn.zain.model.entity.RobotSceneWord;
import cn.zain.service.WordSegementService;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Ansj分词
 *
 * @author Zain
 */
@Service
public class AnsjWordSegmentServiceImpl implements WordSegementService {
    @Resource
    private RobotSceneWordDao robotSceneWordDao;

    @Override
    public List<String> wordCutting(String sentence, RobotSceneWord robotSceneWord) throws URISyntaxException {
        //无须词典文件，直接加载
        //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");
            add("v");
            add("vd");
            add("vn");
            add("vf");
            add("vx");
            add("vi");
            add("vl");
            add("vg");
            add("nt");
            add("nz");
            add("nw");
            add("nl");
            add("ng");
            add("userDefine");
            add("wh");
        }};
        Forest defaultForest = DicLibrary.get();
        Forest tempForest = new Forest();


        if(null != robotSceneWord){
            List<RobotSceneWord> list = robotSceneWordDao.getRobotSceneWordList(robotSceneWord.getRobotId());
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    Library.insertWord(tempForest,list.get(i).getWords() + "\t"+list.get(i).getSceneId()+"\t" + list.get(i).getId().intValue());
                }
            }
        }

        Result result = ToAnalysis.parse(sentence, tempForest,DicLibrary.get());
        //拿到terms
        List<Term> terms = result.getTerms();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < terms.size(); i++) {
            res.add(terms.get(i).getName());
        }
        return res;
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
