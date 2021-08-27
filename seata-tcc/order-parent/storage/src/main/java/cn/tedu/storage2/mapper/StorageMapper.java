package cn.tedu.storage2.mapper;

import cn.tedu.storage2.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface StorageMapper extends BaseMapper<Storage> {
    void decrease(Long productId,Long count);
    //查询库存,用来判断是否有足够的库存
    Storage findByProductIdAfter(Long productId);
    //可用->冻结
    void updateResidueToFrozen(Long productId,Integer count);
    //冻结->已售出
    void updateFrozenToUsed(Long productId,Integer count);
    //冻结->可用
    void updateFrozenToResidue(Long productId,Integer count);
}
