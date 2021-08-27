package cn.tedu.storage2.service;

public interface StorageService {
    void decrease(Long productId,Integer count)throws Exception;
}
