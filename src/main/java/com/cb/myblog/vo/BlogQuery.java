package com.cb.myblog.vo;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/21 23:48
 */
public class BlogQuery {

    private String search;

    public BlogQuery() {
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "search='" + search + '\'' +
                '}';
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
