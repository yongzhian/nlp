package cn.zain.toolkit.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import com.hankcs.hanlp.collection.trie.bintrie.BaseNode;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.recognition.nr.JapanesePersonRecognition;
import com.hankcs.hanlp.recognition.nr.PersonRecognition;
import com.hankcs.hanlp.recognition.nr.TranslatedPersonRecognition;
import com.hankcs.hanlp.recognition.ns.PlaceRecognition;
import com.hankcs.hanlp.recognition.nt.OrganizationRecognition;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.seg.common.Vertex;
import com.hankcs.hanlp.seg.common.WordNet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class SegmentExtend extends ViterbiSegment {

    private static Logger logger = LogManager.getLogger();
    public static String[] word;

    public SegmentExtend(String[] w) {
        word = w;
    }

    @Override
    protected List<Term> segSentence(char[] sentence) {
        WordNet wordNetAll = new WordNet(sentence);
        this.GenerateWordNet(wordNetAll);
        if (HanLP.Config.DEBUG) {
            System.out.printf("粗分词网：\n%s\n", wordNetAll);
        }

        List<Vertex> vertexList = viterbi(wordNetAll);
        if (this.config.useCustomDictionary) {
            combineByCustomDictionary(vertexList);
        }

        if (HanLP.Config.DEBUG) {
            System.out.println("粗分结果" + convert(vertexList, false));
        }

        if (this.config.numberQuantifierRecognize) {
            this.mergeNumberQuantifier(vertexList, wordNetAll, this.config);
        }

        if (this.config.ner) {
            WordNet wordNetOptimum = new WordNet(sentence, vertexList);
            int preSize = wordNetOptimum.size();
            if (this.config.nameRecognize) {
                PersonRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }

            if (this.config.translatedNameRecognize) {
                TranslatedPersonRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }

            if (this.config.japaneseNameRecognize) {
                JapanesePersonRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }

            if (this.config.placeRecognize) {
                PlaceRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }

            if (this.config.organizationRecognize) {
                vertexList = viterbi(wordNetOptimum);
                wordNetOptimum.clear();
                wordNetOptimum.addAll(vertexList);
                preSize = wordNetOptimum.size();
                OrganizationRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }

            if (wordNetOptimum.size() != preSize) {
                vertexList = viterbi(wordNetOptimum);
                if (HanLP.Config.DEBUG) {
                    System.out.printf("细分词网：\n%s\n", wordNetOptimum);
                }
            }
        }

        if (this.config.indexMode) {
            return decorateResultForIndexMode(vertexList, wordNetAll);
        } else {
            if (this.config.speechTagging) {
                speechTagging(vertexList);
            }
            return convert(vertexList, this.config.offset);
        }
    }

    private static List<Vertex> viterbi(WordNet wordNet) {
        // 避免生成对象，优化速度
        LinkedList<Vertex> nodes[] = wordNet.getVertexes();
        LinkedList<Vertex> vertexList = new LinkedList<Vertex>();
        for (Vertex node : nodes[1]) {
            node.updateFrom(nodes[0].getFirst());
        }
        for (int i = 1; i < nodes.length - 1; ++i) {
            LinkedList<Vertex> nodeArray = nodes[i];
            if (nodeArray == null) {
                continue;
            }
            for (Vertex node : nodeArray) {
                if (node.from == null) {
                    continue;
                }
                for (Vertex to : nodes[i + node.realWord.length()]) {
                    to.updateFrom(node);
                }
            }
        }
        Vertex from = nodes[nodes.length - 1].getFirst();
        while (from != null) {
            vertexList.addFirst(from);
            from = from.from;
        }
        return vertexList;
    }

    /**
     * 核心代码覆盖
     * @param vertexList
     * @return
     */
    protected static List<Vertex> combineByCustomDictionary(List<Vertex> vertexList) {
        CustomerDictionaryExtend mydic = new CustomerDictionaryExtend(word);
        Vertex[] wordNet = new Vertex[vertexList.size()];
        vertexList.toArray(wordNet);
        // DAT合并
        DoubleArrayTrie<CoreDictionary.Attribute> dat = mydic.dat;
        for (int i = 0; i < wordNet.length; ++i) {
            int state = 1;
            state = dat.transition(wordNet[i].realWord, state);
            if (state > 0) {
                int to = i + 1;
                int end = to;
                CoreDictionary.Attribute value = dat.output(state);
                for (; to < wordNet.length; ++to) {
                    state = dat.transition(wordNet[to].realWord, state);
                    if (state < 0) {
                        break;
                    }
                    CoreDictionary.Attribute output = dat.output(state);
                    if (output != null) {
                        value = output;
                        end = to + 1;
                    }
                }
                if (value != null) {
                    combineWords(wordNet, i, end, value);
                    i = end - 1;
                }
            }
        }
        // BinTrie合并
        if (mydic.trie != null) {
            for (int i = 0; i < wordNet.length; ++i) {
                if (wordNet[i] == null) {
                    continue;
                }
                BaseNode<CoreDictionary.Attribute> state = mydic.trie.transition(wordNet[i].realWord.toCharArray(), 0);
                if (state != null) {
                    int to = i + 1;
                    int end = to;
                    CoreDictionary.Attribute value = state.getValue();
                    for (; to < wordNet.length; ++to) {
                        if (wordNet[to] == null) {
                            continue;
                        }
                        state = state.transition(wordNet[to].realWord.toCharArray(), 0);
                        if (state == null) {
                            break;
                        }
                        if (state.getValue() != null) {
                            value = state.getValue();
                            end = to + 1;
                        }
                    }
                    if (value != null) {
                        combineWords(wordNet, i, end, value);
                        i = end - 1;
                    }
                }
            }
        }
        vertexList.clear();
        for (Vertex vertex : wordNet) {
            if (vertex != null) {
                vertexList.add(vertex);
            }
        }
        return vertexList;
    }

    /**
     * 将连续的词语合并为一个
     *
     * @param wordNet 词图
     * @param start   起始下标（包含）
     * @param end     结束下标（不包含）
     * @param value   新的属性
     */
    private static void combineWords(Vertex[] wordNet, int start, int end, CoreDictionary.Attribute value) {
        if (start + 1 == end)   // 小优化，如果只有一个词，那就不需要合并，直接应用新属性
        {
            wordNet[start].attribute = value;
        } else {
            StringBuilder sbTerm = new StringBuilder();
            for (int j = start; j < end; ++j) {
                if (wordNet[j] == null) {
                    continue;
                }
                String realWord = wordNet[j].realWord;
                sbTerm.append(realWord);
                wordNet[j] = null;
            }
            wordNet[start] = new Vertex(sbTerm.toString(), value);
        }
    }

}
