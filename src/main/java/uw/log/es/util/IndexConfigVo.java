package uw.log.es.util;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 索引配置Vo
 *
 * @author liliang,kaizen
 * @since 2018-07-27
 */
public class IndexConfigVo {

    /**
     * 索引名称
     */
    private final String rawIndex;


    /**
     * 索引Pattern
     */
    private final FastDateFormat indexPattern;

    public IndexConfigVo(String rawIndex, final FastDateFormat indexPattern) {
        this.rawIndex = rawIndex;
        this.indexPattern = indexPattern;
    }

    public String getRawIndex() {
        return rawIndex;
    }

    public FastDateFormat getIndexPattern() {
        return indexPattern;
    }
}
