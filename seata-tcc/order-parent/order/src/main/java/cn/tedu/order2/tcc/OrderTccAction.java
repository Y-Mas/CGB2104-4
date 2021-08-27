package cn.tedu.order2.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;

@LocalTCC
public interface OrderTccAction {
    /*
    为了避开seata的bug,这里不使用封装的order对象,
    而是一个一个的单独传递每一个值
     BusinessActionContext上下文对象,用来向第二阶段传递数据
     @BusinessActionContextParameter 用来把参数放入上下文对象
     */
    @TwoPhaseBusinessAction(name = "OrderTccAction")
    boolean prepare(BusinessActionContext ctx,
                    @BusinessActionContextParameter(paramName = "id") Long id,
                    Long userId,
                    Long productId,
                    Integer count,
                    BigDecimal money);

    boolean commit(BusinessActionContext ctx);
    boolean rollback(BusinessActionContext ctx);
}
