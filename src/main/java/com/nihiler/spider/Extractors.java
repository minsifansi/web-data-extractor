package com.nihiler.spider;

import com.google.common.collect.Lists;
import com.nihiler.spider.impl.JSONPathExtractor;
import com.nihiler.spider.impl.RegexExtractor;
import com.nihiler.spider.impl.SelectorExtractor;
import com.nihiler.spider.impl.XPathExtractor;

import java.util.List;

/**
 * @author <a href="mailto:fivesmallq@gmail.com">fivesmallq</a>
 * @version Revision: 1.0
 * @date 15/12/28 下午4:43
 */
public class Extractors {
    public Extractors(String name) {
        this.name = name;
    }

    private String name;
    private List<Extractor> extractors = Lists.newLinkedList();

    public static Extractors nameOf(String name) {
        return new Extractors(name);
    }

    public Extractors filter(Extractor extractor) {
        this.extractors.add(extractor);
        return this;
    }

    public Extractors selector(String... params) {
        this.extractors.add(new SelectorExtractor(params));
        return this;
    }

    public Extractors jsonpath(String... params) {
        this.extractors.add(new JSONPathExtractor(params));
        return this;
    }

    public Extractors xpath(String... params) {
        this.extractors.add(new XPathExtractor(params));
        return this;
    }

    public Extractors regex(String... params) {
        this.extractors.add(new RegexExtractor(params));
        return this;
    }

    public String extract(String html) {
        String result = html;
        for (Extractor extractor : extractors) {
            result = extractor.extract(result);
        }
        return result;
    }

    public String getName() {
        return name;
    }
}
