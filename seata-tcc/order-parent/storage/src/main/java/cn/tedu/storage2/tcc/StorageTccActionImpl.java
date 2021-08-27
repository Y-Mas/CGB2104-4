package cn.tedu.storage2.tcc;

import cn.tedu.storage2.entity.Storage;
import cn.tedu.storage2.mapper.StorageMapper;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StorageTccActionImpl implements StorageTccAction{
    @Autowired
    private StorageMapper storageMapper;
    @Transactional
    @Override
    public boolean prepare(BusinessActionContext ctx, Long productId, Integer count) {
        Storage storage = storageMapper.findByProductIdAfter(productId);
        if (storage.getResidue() < count) {
            throw new RuntimeException("库存不足");
        }
        storageMapper.updateResidueToFrozen(productId, count);
        //标记
        ResultHolder.setResult(Storage.class,ctx.getXid(),"p");
        return true;
    }
    @Transactional
    @Override
    public boolean commit(BusinessActionContext ctx) {
        if (ResultHolder.getResult(StorageTccAction.class,ctx.getXid())==null){
            return true;
        }
        Long productId = Long.valueOf(ctx.getActionContext("productId").toString());
        Integer count = Integer.valueOf(ctx.getActionContext("count").toString());
        storageMapper.updateFrozenToUsed(productId, count);
        ResultHolder.removeResult(StorageTccAction.class,ctx.getXid());
        return true;
    }
    @Transactional
    @Override
    public boolean rollback(BusinessActionContext ctx) {
        if (ResultHolder.getResult(StorageTccAction.class,ctx.getXid())==null){
            return true;
        }
        Long productId = Long.valueOf(ctx.getActionContext("productId").toString());
        Integer count = Integer.valueOf(ctx.getActionContext("count").toString());
        storageMapper.updateFrozenToResidue(productId, count);
        ResultHolder.removeResult(StorageTccAction.class,ctx.getXid());
        return true;
    }
}
