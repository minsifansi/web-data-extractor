package im.nll.data.extractor;

import com.google.common.collect.Maps;
import im.nll.data.extractor.impl.JSONPathExtractor;
import im.nll.data.extractor.impl.RegexExtractor;
import im.nll.data.extractor.impl.SelectorExtractor;
import im.nll.data.extractor.impl.XPathExtractor;
import im.nll.data.extractor.utils.Reflect;

import java.util.Map;

/**
 * @author <a href="mailto:fivesmallq@gmail.com">fivesmallq</a>
 * @version Revision: 1.0
 * @date 15/12/25 下午9:26
 */
public class WebDataExtractor {
    private String html;

    public WebDataExtractor(String html) {
        this.html = html;
    }

    public static WebDataExtractor of(String html) {
        return new WebDataExtractor(html);
    }

    public WebDataExtractor filter(Extractor extractor) {
        this.html = extractor.extract(html);
        return this;
    }

    public WebDataExtractor json(String... params) {
        this.html = new JSONPathExtractor(params).extract(html);
        return this;
    }

    public WebDataExtractor xpath(String... params) {
        this.html = new XPathExtractor(params).extract(html);
        return this;
    }

    public WebDataExtractor selector(String... params) {
        this.html = new SelectorExtractor(params).extract(html);
        return this;
    }

    public WebDataExtractor regex(String... params) {
        this.html = new RegexExtractor(params).extract(html);
        return this;
    }

    public String asString() {
        return html;
    }

    public Map<String, String> asMap(Extractors... extractors) {
        Map<String, String> dataMap = Maps.newLinkedHashMap();
        for (Extractors extractorsOne : extractors) {
            String data = extractorsOne.extract(this.html);
            dataMap.put(extractorsOne.getName(), data);
        }
        return dataMap;
    }


    public <T> T asBean(Class<T> clazz, Extractors... extractors) {
        T entity = Reflect.on(clazz).create().get();
        for (Extractors extractorsOne : extractors) {
            String name = extractorsOne.getName();
            String data = extractorsOne.extract(this.html);
            Reflect.on(entity).set(name, data);
        }
        return entity;
    }
}