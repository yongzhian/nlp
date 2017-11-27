package cn.zain.toolkit.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import com.hankcs.hanlp.collection.trie.bintrie.BinTrie;
import com.hankcs.hanlp.corpus.io.ByteArray;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.other.CharTable;
import com.hankcs.hanlp.utility.Predefine;

import java.io.*;
import java.util.*;

public class CustomerDictionaryExtend {
    public BinTrie<CoreDictionary.Attribute> trie;
    public DoubleArrayTrie<CoreDictionary.Attribute> dat = new DoubleArrayTrie();
    public final String[] path;

    CustomerDictionaryExtend(String[] words) {
        path = HanLP.Config.CustomDictionaryPath;
        long start = System.currentTimeMillis();
        if (!loadMainDictionary(path[0])) {
            Predefine.logger.warning("自定义词典" + Arrays.toString(path) + "加载失败");
        } else {
            Predefine.logger.info("自定义词典加载成功:" + dat.size() + "个词条，耗时" + (System.currentTimeMillis() - start) + "ms");
        }
        for (String word : words){
            add(word);
        }
    }

    boolean loadDat(String path) {
        try {
            ByteArray byteArray = ByteArray.createByteArray(path + ".bin");
            int size = byteArray.nextInt();
            CoreDictionary.Attribute[] attributes = new CoreDictionary.Attribute[size];
            Nature[] natureIndexArray = Nature.values();

            for(int i = 0; i < size; ++i) {
                int currentTotalFrequency = byteArray.nextInt();
                int length = byteArray.nextInt();
                attributes[i] = new CoreDictionary.Attribute(length);
                attributes[i].totalFrequency = currentTotalFrequency;

                for(int j = 0; j < length; ++j) {
                    attributes[i].nature[j] = natureIndexArray[byteArray.nextInt()];
                    attributes[i].frequency[j] = byteArray.nextInt();
                }
            }

            if (dat.load(byteArray, attributes) && !byteArray.hasMore()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception var9) {
            Predefine.logger.warning("读取失败，问题发生在" + var9);
            return false;
        }
    }

    public boolean load(String path, Nature defaultNature, TreeMap<String, CoreDictionary.Attribute> map) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

            while(true) {
                String[] param;
                do {
                    String line;
                    if ((line = br.readLine()) == null) {
                        br.close();
                        return true;
                    }

                    param = line.split("\\s");
                } while(param[0].length() == 0);

                if (HanLP.Config.Normalization) {
                    param[0] = CharTable.convert(param[0]);
                }

                int natureCount = (param.length - 1) / 2;
                CoreDictionary.Attribute attribute;
                if (natureCount == 0) {
                    attribute = new CoreDictionary.Attribute(defaultNature);
                } else {
                    attribute = new CoreDictionary.Attribute(natureCount);

                    for(int i = 0; i < natureCount; ++i) {
                        attribute.nature[i] = (Nature)Enum.valueOf(Nature.class, param[1 + 2 * i]);
                        attribute.frequency[i] = Integer.parseInt(param[2 + 2 * i]);
                        attribute.totalFrequency += attribute.frequency[i];
                    }
                }

                map.put(param[0], attribute);
            }
        } catch (Exception var9) {
            if (!path.startsWith(".")) {
                Predefine.logger.severe("自定义词典" + path + "读取错误！" + var9);
            }

            return false;
        }
    }

    private boolean loadMainDictionary(String mainPath) {
        Predefine.logger.info("自定义词典开始加载:" + mainPath);
        if (loadDat(mainPath)) {
            return true;
        } else {
            TreeMap map = new TreeMap();

            try {
                String[] arr$ = path;
                int len$ = arr$.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String p = arr$[i$];
                    Nature defaultNature = Nature.n;
                    int cut = p.indexOf(32);
                    if (cut > 0) {
                        String nature = p.substring(cut + 1);
                        p = p.substring(0, cut);

                        try {
                            defaultNature = Nature.valueOf(nature);
                        } catch (Exception var10) {
                            Predefine.logger.severe("配置文件【" + p + "】写错了！" + var10);
                            continue;
                        }
                    }

                    Predefine.logger.info("以默认词性[" + defaultNature + "]加载自定义词典" + p + "中……");
                    boolean success = load(p, defaultNature, map);
                    if (!success) {
                        Predefine.logger.warning("失败：" + p);
                    }
                }

                if (map.size() == 0) {
                    Predefine.logger.warning("没有加载到任何词条");
                    map.put("未##它", (Object)null);
                }

                Predefine.logger.info("正在构建DoubleArrayTrie……");
                dat.build(map);
                Predefine.logger.info("正在缓存词典为dat文件……");
                List<CoreDictionary.Attribute> attributeList = new LinkedList();
                Iterator i$1 = map.entrySet().iterator();

                while(i$1.hasNext()) {
                    Map.Entry<String, CoreDictionary.Attribute> entry = (Map.Entry)i$1.next();
                    attributeList.add(entry.getValue());
                }

                DataOutputStream out = new DataOutputStream(new FileOutputStream(mainPath + ".bin"));
                out.writeInt(attributeList.size());
                Iterator i$ = attributeList.iterator();

                while(i$.hasNext()) {
                    CoreDictionary.Attribute attribute = (CoreDictionary.Attribute)i$.next();
                    out.writeInt(attribute.totalFrequency);
                    out.writeInt(attribute.nature.length);

                    for(int i = 0; i < attribute.nature.length; ++i) {
                        out.writeInt(attribute.nature[i].ordinal());
                        out.writeInt(attribute.frequency[i]);
                    }
                }

                dat.save(out);
                out.close();
            } catch (FileNotFoundException var11) {
                Predefine.logger.severe("自定义词典" + mainPath + "不存在！" + var11);
                return false;
            } catch (IOException var12) {
                Predefine.logger.severe("自定义词典" + mainPath + "读取错误！" + var12);
                return false;
            } catch (Exception var13) {
                Predefine.logger.warning("自定义词典" + mainPath + "缓存失败！" + var13);
            }

            return true;
        }
    }

    public boolean add(String word, String natureWithFrequency) {
        return contains(word) ? false : insert(word, natureWithFrequency);
    }

    public boolean add(String word) {
        if (HanLP.Config.Normalization) {
            word = CharTable.convert(word);
        }
        return contains(word) ? false : insert(word, (String)null);
    }

    public boolean insert(String word, String natureWithFrequency) {
        if (word == null) {
            return false;
        } else {
            if (HanLP.Config.Normalization) {
                word = CharTable.convert(word);
            }

            CoreDictionary.Attribute att = natureWithFrequency == null ? new CoreDictionary.Attribute(Nature.nz, 1) : CoreDictionary.Attribute.create(natureWithFrequency);
            if (att == null) {
                return false;
            } else if (dat != null && dat.set(word, att)) {
                return true;
            } else {
                if (trie == null) {
                    trie = new BinTrie();
                }

                trie.put(word, att);
                return true;
            }
        }
    }

    public boolean insert(String word) {
        return insert(word, (String)null);
    }

    public boolean contains(String key) {
        if (dat != null && dat.exactMatchSearch(key) >= 0) {
            return true;
        } else {
            return trie != null && trie.containsKey(key);
        }
    }
}
