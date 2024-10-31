package io.github.wittyprince.commons.cmm1.dal.mapper;

import io.github.wittyprince.commons.cmm1.common.Bit32Generator;
import io.github.wittyprince.commons.cmm1.dal.model.BaseDO;
import io.github.wittyprince.commons.cmm1.holder.UserContextHolder;
import io.mybatis.common.util.Assert;
import io.mybatis.mapper.Mapper;
import io.mybatis.mapper.example.Example;
import io.mybatis.mapper.fn.Fn;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.mybatis.common.core.Code.SAVE_FAILURE;
import static io.mybatis.common.core.Code.UPDATE_FAILURE;

/**
 * base mapper
 *
 * @author wp
 * Created on 2023/10/8
 * @since 1.0.1
 */
public interface BaseMapper<T extends BaseDO> extends Mapper<T, Long> {

    default T save(T entity) {
        if (entity == null) {
            return null;
        }
        preSave(entity);
        Assert.isTrue(insert(entity) == 1, SAVE_FAILURE);
        return entity;
    }

    default T getByBid(String bid) {
        if (bid == null) {
            return null;
        }
        List<T> tList = getByBids(Collections.singletonList(bid));
        return tList == null || tList.isEmpty() ? null : tList.get(0);
    }

    default List<T> getByBids(List<String> bids) {
        return bids == null || bids.isEmpty()
                ? Collections.emptyList()
                : selectByFieldList(T::getBid, bids)
                .stream().filter(t -> !t.getDeleted()).collect(Collectors.toList());
    }

    default T update(T entity) {
        preUpdate(entity);
//        Assert.isTrue(baseMapper.updateByPrimaryKey(entity) == 1, UPDATE_FAILURE);
        return updateByBid(entity);
    }

    default T updateByBid(T entity) {
        if (entity == null) {
            return null;
        }
        return this.updateByBid(entity, entity.getBid());
    }

    default T updateByBid(T entity, String bid) {
        if (entity == null || bid == null) {
            return null;
        }
        entity.setUpdateBy(getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        Example<T> example = new Example<>();
        example.createCriteria().andEqualTo(((Fn<T,Object>) T::getBid).in(entityClass()), bid);
        Assert.isTrue(updateByExample(entity, example) == 1, UPDATE_FAILURE);
        return getByBid(bid);
    }

    default int deleteByBid(String bid) {
        T entity = getByBid(bid);
        entity.setDeleted(Boolean.TRUE);
        update(entity);
        return 1;
    }

    default int deleteByBids(List<String> bids) {
        List<T> byBids = getByBids(bids);
        byBids.forEach(t -> {
            t.setDeleted(Boolean.TRUE);
            update(t);
        });
        return 1;
    }

    default List<T> saveBatch(List<T> entityList) {
        if (entityList == null || entityList.size() == 0) {
            return null;
        }
        entityList.forEach(entity -> {
            preSave(entity);
            Assert.isTrue(insert(entity) == 1, SAVE_FAILURE);
        });
        return entityList;
    }

    private void preSave(BaseDO entity) {
        if (entity.getBid() == null) {
            entity.setBid(Bit32Generator.nextBid());
        }
        entity.setCreateBy(getUserId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setDeleted(false);
    }

    private void preUpdate(T entity) {
        entity.setUpdateBy(getUserId());
        entity.setUpdateTime(LocalDateTime.now());
    }

    private String getUserId() {
        UserContextHolder.UserInfo user = UserContextHolder.getUser();
        return user == null ? null : user.getUserBid();
    }
}
