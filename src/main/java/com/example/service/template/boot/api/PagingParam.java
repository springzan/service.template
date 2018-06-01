package com.example.service.template.boot.api;

public class PagingParam {
    private int page = 1;
    private int pageSize = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PagingApiResponse.PagingApiResponseBuilder aPagingApiResponseBuilder() {
        return PagingApiResponse.PagingApiResponseBuilder.aPagingApiResponseBuilder()
                .withPageParam(this);
    }

    public static PagingParam aPagingParam(int page, int pageSize) {
        PagingParam pagingParam = new PagingParam();
        pagingParam.setPage(page);
        pagingParam.setPageSize(pageSize);
        return pagingParam;
    }
}
