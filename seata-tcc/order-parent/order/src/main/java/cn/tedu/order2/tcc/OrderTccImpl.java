package cn.tedu.order2.tcc;

import cn.tedu.order2.entity.Order;
import cn.tedu.order2.mapper.OrderMapper;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class OrderTccImpl implements OrderTccAction{
    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    @Override
    public boolean prepare(BusinessActionContext ctx, Long id, Long userId, Long productId, Integer count, BigDecimal money) {
        orderMapper.create(new Order(id,userId,productId,count,money,0));
        ResultHolder.setResult(OrderTccAction.class, ctx.getXid(), "p");
        return true;
    }
    @Transactional
    @Override
    public boolean commit(BusinessActionContext ctx) {
        if (ResultHolder.getResult(OrderTccAction.class,ctx.getXid())==null){
            return true;
        }
        //从上下文中取出id
        Long orderId = Long.valueOf(ctx.getActionContext("id").toString());
        orderMapper.updateStatus(orderId, 1);
        ResultHolder.removeResult(OrderTccAction.class,ctx.getXid());
        return true;
    }
    @Transactional
    @Override
    public boolean rollback(BusinessActionContext ctx) {
        if (ResultHolder.getResult(OrderTccAction.class,ctx.getXid())==null){
            return true;
        }
        Long orderId = Long.valueOf(ctx.getActionContext("id").toString());
        orderMapper.deleteById(orderId);
        ResultHolder.removeResult(OrderTccAction.class,ctx.getXid());
        return true;
    }
}
