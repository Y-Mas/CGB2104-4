package cn.tedu.sp06.filter;

import cn.tedu.web.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class AccessFilter extends ZuulFilter {
    // 过滤器的类型： pre, routing, post, error
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
    // 顺序号
    @Override
    public int filterOrder() {
        return 6;
    }
    // 针对当前请求，是否要执行过滤代码
    // 请求商品要检查权限，请求用户和订单不检查权限
    @Override
    public boolean shouldFilter() {
        // 判断当前调用的后台服务，是不是 item-service
        // 获得调用的服务id
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId =
                (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
        // 如果id是item-service，返回true
        return "item-service".equals(serviceId);
    }
    // 过滤代码
    @Override
    public Object run() throws ZuulException {
        //http://localhost:3001/item-service/u5y34?token=y44t34t
        // 接收token参数
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        // 如果没有 token，阻止继续调用，直接返回响应
        // null, "", "    "
        if (StringUtils.isBlank(token)){
            ctx.setSendZuulResponse(false);
            String json = JsonResult.err()
                    .code(JsonResult.NOT_LOGIN)
                    .msg("Not login 未登录")
                    .toString();
            ctx.addZuulResponseHeader
                    ("Content-Type","application/json;charset=utf-8");
            ctx.setResponseBody(json);
        }
        // zuul当前版本中没有使用这个方回执，它不起任何作用
        return null;
    }
}
