package cn.zain.service;


import cn.zain.dao.RobotNerDao;
import cn.zain.model.entity.RobotNer;
import cn.zain.model.entity.RobotNerExample;
import cn.zain.model.entity.RobotSceneWord;
import cn.zain.service.impl.FnlpWordSegmentServiceImpl;
import cn.zain.service.impl.IkWordSegmentServiceImpl;
import cn.zain.service.impl.JieBaWordSegmentServiceImpl;
import cn.zain.service.impl.StanfordWordSegmentServiceImpl;
import cn.zain.toolkit.hanlp.SegmentExtend;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.utility.Predefine;
import org.ansj.dic.impl.Jdbc2Stream;
import org.ansj.domain.Result;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class WordSegementServiceTest {
    @Resource
    private WordSegementService wordSegementService;

    @Resource
    private RobotNerDao robotNerDao;

    static {
        Predefine.logger.setLevel(Level.ALL);
    }

    private static Logger logger = LogManager.getLogger();
    private static String testStr1 = "大连美容美发学校中君意是你值得信赖的选择";
    private static String testStr2 = "江州市长江大桥参加了长江大桥的通车仪式";
    private static String testStr3 = "永和服装饰品有限公司想打开电视,我要看超级大的台风";
    private static String testStr4 = "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作";
    private static String testStr5 = "维拉机器人可以帮我打开一下卧室的大灯么";
    private static String testStr6 = "帮我打开一下客厅所有灯的小彩灯";

    @Test
    public void daoTest() throws Exception {
        RobotNerExample robotNerExample = new RobotNerExample();
        robotNerExample.createCriteria().andRobotSnEqualTo("A025170100012");
        final List<RobotNer> robotNers = robotNerDao.selectByExample(robotNerExample);
        logger.info(robotNers);
    }

    @Test
    public void segmentSentenceByAnsj() throws Exception {
//        List list = wordSegementService.segmentSentence(testStr5, "23");
        List list1 = wordSegementService.segmentSentence(testStr6, "A025170100012");
        logger.info("........");
    }

    @Test
    public void ansjBaWordSegmentServiceImplTest() throws Exception {
        RobotSceneWord robotSceneWord = new RobotSceneWord();
        robotSceneWord.setRobotId("A01");
        List<String> result = wordSegementService.wordCutting(testStr5, null);
        logger.info(result);

        result = wordSegementService.wordCutting(testStr5, robotSceneWord);
        logger.info(result);

        Set set = DicLibrary.keys();

        DicLibrary.insert("dic", "维拉机器人");
        //直接使用默认词典
        Result result0 = ToAnalysis.parse(testStr5);

        //必须3列，word param 和词频
        InputStream stream4R1 = Jdbc2Stream.stream("jdbc://jdbc:mysql://192.168.21.104:3306/nlp?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull|root|passwd4root~Q|select words,scene_id,id from robot_scene_word where robot_id='A01'");
        Forest dic4R1 = Library.makeForest(stream4R1);
        Library.insertWord(dic4R1, "维拉\tn\t20");
        GetWord getWord = dic4R1.getWord(testStr5);

        String[] s = getWord.getParams();
        Result result1 = ToAnalysis.parse(testStr5, dic4R1, DicLibrary.get()); //公共词典应该无效

        InputStream stream4R2 = Jdbc2Stream.stream("jdbc://jdbc:mysql://192.168.21.104:3306/nlp?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull|root|passwd4root~Q|select words,scene_id,id from robot_scene_word where robot_id='A02'");
        Forest dic4R2 = Library.makeForest(stream4R2);
        Library.insertWord(dic4R2, "帮我\tncc\t20");
        Result result2 = ToAnalysis.parse(testStr5, dic4R2);

        logger.info(dic4R1);
    }

    @Test
    public void fnlpWordSegmentServiceImplTest() throws Exception {
        FnlpWordSegmentServiceImpl fnlpWordSegmentService = new FnlpWordSegmentServiceImpl();

        logger.info("通用：" + fnlpWordSegmentService.wordCutting(testStr3, null));
        RobotSceneWord robotSceneWord = new RobotSceneWord();
        robotSceneWord.setWords("帮我");

        logger.info("1：" + fnlpWordSegmentService.wordCutting(testStr3, robotSceneWord));

        RobotSceneWord robotSceneWord2 = new RobotSceneWord();
        robotSceneWord2.setWords("帮我");
        logger.info("2：" + fnlpWordSegmentService.wordCutting(testStr3, robotSceneWord2));

        //CNFactory自定义词典接口不支持 可重写
//        org.fnlp.nlp.cn.CNFactory factory = CNFactory.getInstance("models");
//        String[] words = factory.seg("关注自然语言处理、语音识别、深度学习等方向的前沿技术和业界动态。"); //分词
//        String result1 = factory.tag2String("关注自然语言处理、语音识别、深度学习等方向的前沿技术和业界动态。");//分词+词性
//        HashMap<String, String> result2 = factory.ner("詹姆斯·默多克和丽贝卡·布鲁克斯 鲁珀特·默多克旗下的美国小报《纽约邮报》的职员被公司律师告知，保存任何也许与电话窃听及贿赂有关的文件。"); //实体名识别

    }

    @Test
    public void jieBaWordSegmentServiceImplTest() throws Exception {
        JieBaWordSegmentServiceImpl jieBaWordSegmentService = new JieBaWordSegmentServiceImpl();
        List<String> result1 = jieBaWordSegmentService.wordCutting(testStr3, null);
        List<String> result2 = jieBaWordSegmentService.wordCutting(testStr4, null);
        logger.info(result1);
        logger.info(result2);
    }


    @Test
    public void ikWordSegmentServiceTest() throws Exception {
        IkWordSegmentServiceImpl ikWordSegmentService = new IkWordSegmentServiceImpl();
        RobotSceneWord robotSceneWord = new RobotSceneWord();
        robotSceneWord.setWords("永和服装");
        List<String> result1 = ikWordSegmentService.wordCutting(testStr3, robotSceneWord);
        logger.info(result1);
        robotSceneWord.setWords("超级大的台风");
        List<String> result2 = ikWordSegmentService.wordCutting(testStr3, robotSceneWord);
        logger.info(result2);

//        IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
//        StringReader reader = new StringReader(testStr3);
//        TokenStream ts = ikAnalyzer.tokenStream("", reader);
//        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
//        ts.reset();
//        while (ts.incrementToken()) {
//            System.out.print("【" + term.toString() + "】");
//        }
//        reader.close();
//        logger.info("end");
    }

    @Test
    public void stanfordWordSegmentServiceTest() throws Exception {
        StanfordWordSegmentServiceImpl stanfordWordSegmentService = new StanfordWordSegmentServiceImpl();
        List<String> result = stanfordWordSegmentService.wordCutting(testStr4, null);
        logger.info(result);

        result = stanfordWordSegmentService.wordCutting(testStr3, null);
        logger.info(result);
    }

    @Test
    public void hanlpWordSegmentServiceTest() throws Exception {
//        HanlpWordSegmentServiceImpl hanlpWordSegmentService = new HanlpWordSegmentServiceImpl();
//        List<String> result = hanlpWordSegmentService.wordCutting("打开电视,齐悦花园", null);
//        logger.info(result);

        System.out.println(HanLP.segment("你好，欢迎使用HanLP！"));


        logger.info("自定义hanlp分词...");
        List<String> result = new ArrayList<String>();
        String[] words = {"打开电视"};
        String sentence = "打开电视我要看电影";
        Segment segmenter = new SegmentExtend(words);
        List<com.hankcs.hanlp.seg.common.Term> termList = segmenter.seg(sentence);
        for (com.hankcs.hanlp.seg.common.Term term : termList) {
            result.add(term.word);
        }
        result.add("----------hanlp分词");

        logger.info(result);
    }
}
