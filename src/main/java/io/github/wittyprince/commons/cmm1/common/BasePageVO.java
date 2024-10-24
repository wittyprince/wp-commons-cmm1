package io.github.wittyprince.commons.cmm1.common;

import com.github.pagehelper.PageInfo;
import io.github.wittyprince.commons.cmm1.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * base page VO
 *
 * @author WangChen
 * Created on 2023/10/8
 * @since 1.0.1
 */
public class BasePageVO<E> {

    private Long totalElements; // 总数量

    private Integer totalPages; // 总页数

    private List<E> list = new ArrayList<>();

    public BasePageVO() {
    }

    public BasePageVO(Long totalElements, Integer totalPages, List<E> list) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.list = list;
    }


    /**
     * java.util.List 转 commons-core#BasePageVO
     */
    public static <R> BasePageVO<R> toBasePageVO(List<R> list, Long totalElements, Integer pageSize) {
        BasePageVO<R> basePageVO = new BasePageVO<>();
        basePageVO.setList(list);
        basePageVO.setTotalElements(totalElements);
        int totalPage = totalElements.intValue() / pageSize;
        basePageVO.setTotalPages(Objects.equals(totalElements.intValue() % pageSize, 0)
                ? totalPage
                : totalPage + 1);
        return basePageVO;
    }

    public static <T, R> BasePageVO<R> toBasePageVO(PageInfo<T> pageInfo, Function<T, R> mapping) {
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        List<T> list = pageInfo.getList();
        return new BasePageVO<>(total, pages, ListUtil.toList(list, mapping));
    }

    public <R> BasePageVO<R> convert(Function<E, R> fn) {
        BasePageVO<R> basePageVO = new BasePageVO<>();
        basePageVO.setList(ListUtil.toList(list, fn));
        basePageVO.setTotalElements(totalElements);
        basePageVO.setTotalPages(totalPages);
        return basePageVO;
    }


    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
