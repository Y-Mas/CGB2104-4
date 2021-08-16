package cn.tedu.sp06.fb;

import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class OrderFB implements FallbackProvider {
    /**
     * 设置针对哪个后台服务继续降级
     * "*": 对所有服务都使用当前降级类
     * 此处仅针对商品服务降级
     * @return
     */
    @Override
    public String getRoute() {
        return "order-service";
    }

    /**
     * 向客户端发回的降级响应
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            }
            @Override
            public void close() {
                //用来关闭输入流
                //BAIS  是虚拟机中读取byte[]数组的流对象,不占用底层系统资源
            }
            @Override
            public InputStream getBody() throws IOException {
                String json = JsonResult.err().code(500).msg("X**XX***XXX").toString();
                return new ByteArrayInputStream(json.getBytes("UTF-8"));
            }
            @Override
            public HttpHeaders getHeaders() {
                //content-type application/json;charset=utf-8
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json;charset=utf-8");
                return headers;
            }
        };
    }
}
