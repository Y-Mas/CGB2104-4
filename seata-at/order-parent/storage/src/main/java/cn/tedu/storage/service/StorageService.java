package cn.tedu.storage.service;

public interface StorageService {
    void decrease(Long productId,Long count)throws Exception;
}
