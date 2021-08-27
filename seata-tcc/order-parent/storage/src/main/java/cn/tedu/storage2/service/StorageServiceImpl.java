package cn.tedu.storage2.service;

import cn.tedu.storage2.mapper.StorageMapper;
import cn.tedu.storage2.tcc.StorageTccAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService{
    @Autowired
    private StorageTccAction storageTccAction;

    @Override
    public void decrease(Long productId, Integer count) throws Exception {
        storageTccAction.prepare(null, productId, count);
    }
}
