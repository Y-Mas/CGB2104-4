package cn.tedu.account2.mapper;

import cn.tedu.account2.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;

import java.math.BigDecimal;

public interface AccountMapper extends BaseMapper<Account> {
    //扣减账户金额
    void decrease(Long userId, BigDecimal money);
    void updateFrozen(@Param("userId") Long userId, @Param("residue") BigDecimal residue, @Param("frozen") BigDecimal frozen);

    void updateFrozenToUsed(@Param("userId") Long userId, @Param("money") BigDecimal money);

    void updateFrozenToResidue(@Param("userId") Long userId, @Param("money") BigDecimal money);

}
