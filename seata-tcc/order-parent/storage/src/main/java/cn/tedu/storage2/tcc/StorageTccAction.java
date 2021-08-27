package cn.tedu.storage2.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface StorageTccAction {
    @TwoPhaseBusinessAction(name = "StorageTccAction")
    boolean prepare(BusinessActionContext ctx,
                    @BusinessActionContextParameter(paramName = "productId") Long productId,
                    @BusinessActionContextParameter(paramName = "count")Integer count);

    boolean commit(BusinessActionContext ctx);
    boolean rollback(BusinessActionContext ctx);

}
