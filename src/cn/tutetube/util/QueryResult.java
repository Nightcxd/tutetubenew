package cn.tutetube.util;

import java.util.List;

/**
 * Created by cxd on 2016/9/23.
 */
public class QueryResult<T> {
    private List<T> list;
    private Long count;

    public QueryResult(List<T> list, Long count) {
        this.list = list;
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
