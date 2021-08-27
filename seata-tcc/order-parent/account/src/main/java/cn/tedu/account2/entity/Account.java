package cn.tedu.account2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private Long userId;
    private BigDecimal total;//总和金额
    private BigDecimal used;//已使用
    private BigDecimal residue;//可用
    private BigDecimal frozen;//已冻结
}
