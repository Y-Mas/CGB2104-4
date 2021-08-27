package cn.tedu.account2.service;

import cn.tedu.account2.mapper.AccountMapper;
import cn.tedu.account2.tcc.AccountTccAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountTccAction accountTccAction;
    @Override
    public void decrease(Long userId, BigDecimal money) {
        accountTccAction.prepareDecreaseAccount(null, userId, money);
    }
}
