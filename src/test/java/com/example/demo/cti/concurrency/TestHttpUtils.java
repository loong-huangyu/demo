package com.example.demo.cti.concurrency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpRequest;

/**
 * @author huchan
 * @version 1.0
 * @date 2021/9/26 2:29 下午
 */
public class TestHttpUtils {

    public static final String TOKEN = "Bearer nskqzmx5sspd8omvkt42putade6mxkcssyye48q6082z4t64q5u2400tx6m3";

    public static final String URI = "http://cola-gateway:6677";

    @Before
    public void before() {
        Console.log("开始测试");
    }

    @Test
    public void testConcurrencyTest() {
        // 高并发性能测试
        ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, () -> {
//            getRolePage();
            getMenuPage();
//            getMenuPermission();
            Console.log("{} test finished", Thread.currentThread().getName());
        });
        // 获取总的执行时间，单位毫秒
        Console.log(tester.getInterval());
    }

    @Test
    public void testHttp() {
//        Console.log("login:{}", systemLogin());
//        Console.log("systemUser:{}", getSystemUserInfo());
        //Console.log("menuPage:{}", getMenuPage());
//        Console.log("rolePage:{}", getRolePage());
//        Console.log("menuPermission:{}", getMenuPermission());
    }

    private String systemLogin() {
        String json = "{\n" +
            "    \"loginType\":0,\n" +
            "    \"loginName\": \"admin\",\n" +
            "    \"loginCode\": \"j6kEqwxBLtqCKHpKb2i35T35J66ahtt3qkLhLm0pdGi3IMMPSiQIbJi7427crhT+MlcBVrKm98hcURG5qgjHyUsyPalPdyAuPAqdTouNmJ6A0jFXEGdOYKJu0fS+yEH88TxABXAMAW7aAL1P3eAXys28PCsbv8E3qXzeRcBiqcg=\"\n" +
            "}";
        return HttpRequest.post(URI + "/auth/system/login")
            .header("Client", "100")
            .header("TenantId", "1000")
            .header("TraceId", UUID.fastUUID().toString(true))
            .body(json)
            .execute().body();
    }

    private String getSystemUserInfo() {
        return HttpRequest.get(URI + "/system/user/info")
            .header("Client", "100")
            .header("TenantId", "1000")
            .header("RequestId", TOKEN)
            .header("TraceId", UUID.fastUUID().toString(true))
            .execute().body();
    }

    private String getMenuPage() {
        String json = "{\"page\":{},\"searchParams\":{}}";
        return HttpRequest.post(URI + "/system/menu/page")
            .header("Client", "100")
            .header("TenantId", "1000")
            .header("RequestId", TOKEN)
            .header("TraceId", UUID.fastUUID().toString(true))
            .body(json)
            .execute().body();
    }

    private String getMenuPermission() {
        return HttpRequest.get(URI + "/system/menu/permission")
            .header("Client", "100")
            .header("TenantId", "1000")
            .header("RequestId", TOKEN)
            .header("TraceId", UUID.fastUUID().toString(true))
            .execute().body();
    }

    private String getRolePage() {
        String json = "{\"page\":{\"current\":1,\"size\":10},\"searchParams\":{}}";
        return HttpRequest.post("http://cola-ui:3100/basic-api/system/role/page")
            .header("Client", "100")
            .header("TenantId", "1000")
            .header("RequestId", TOKEN)
            .header("TraceId", UUID.fastUUID().toString(true))
            .body(json)
            .execute().body();
    }

    @After
    public void after() {
        Console.log("结束测试");
    }

}
