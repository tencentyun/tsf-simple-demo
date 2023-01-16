package com.tsf.demo.provider.controller;

import com.tsf.demo.provider.config.ProviderNameConfig;
import com.tsf.demo.provider.uitls.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.tsf.core.util.TsfSpringContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class ProviderController {
    private static final Logger LOG = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private ProviderNameConfig providerNameConfig;

    @RequestMapping(value = "/echo/{param}", method = RequestMethod.GET)
    public String echo(@PathVariable String param) {
        LOG.info("provider-demo -- request param: [" + param + "]");
        String result = "from host-ip: " + getInet4Address() + ",request param: " + param + ", response from " + providerNameConfig.getName();
        LOG.info("provider-demo -- provider config name: [" + providerNameConfig.getName() + ']');
        LOG.info("provider-demo -- response info: [" + result + "]");
        return result;
    }

    @RequestMapping(value = "/apiDebug/{param}", method = RequestMethod.GET)
    public String api_debug(@PathVariable String param,
                           @RequestParam() String gw_host,
                           @RequestParam() String gw_port,
                           @RequestParam() String api_method,
                           @RequestParam() String gw_context,
                           @RequestParam() String headers,
                           @RequestParam(required = false) String post_data) throws Exception {
        String resp = "";
        LOG.info(headers);
        if (api_method.equals("GET")) {
            if(headers.equals("")){
                resp = HttpUtils.httpGet(gw_host + ":" + gw_port + gw_context + param);
            }else {
                resp = HttpUtils.httpGet(gw_host + ":" + gw_port + gw_context + param, headers);
            }
        } else if (api_method.equals("POST")) {
            resp = HttpUtils.httpPost(gw_host + ":" + gw_port + gw_context + param, post_data,headers);
        } else {
            return "Unsupported request method！";
        }
        String result = "from host-ip: " + gw_host + " api result : " + resp;
        LOG.info("api result: [" + result + "]");
        return result;
    }

    // 获取本机ip
    public static String getInet4Address() {
        Enumeration<NetworkInterface> nis;
        String ip = null;
        try {
            nis = NetworkInterface.getNetworkInterfaces();
            for (; nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                for (; ias.hasMoreElements(); ) {
                    InetAddress ia = ias.nextElement();
                    if (ia instanceof Inet4Address && !ia.getHostAddress().equals("127.0.0.1")) {
                        ip = ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ip;
    }

    @RequestMapping(value = "/echo/unit/{param}", method = RequestMethod.GET)
    public String echoUnit(@PathVariable String param) {
        LOG.info("provider-demo -- unit request param: [" + param + "]");
        String result = "from host-ip: " + getInet4Address() + ",request param: " + param + ", response from " + providerNameConfig.getName();
        LOG.info("provider-demo -- unit provider config name: [" + providerNameConfig.getName() + ']');
        LOG.info("provider-demo -- unit response info: [" + result + "]");
        return result;
    }

    @RequestMapping(value = "/config/{path}/value", method = RequestMethod.GET)
    public String config(@PathVariable String path) {
        return TsfSpringContextAware.getProperties(path);
    }


    @RequestMapping(value = "/echo/error/{param}", method = RequestMethod.GET)
    public String echoError(@PathVariable String param) {
        LOG.info("provider-demo -- Error request param: [" + param + "], throw exception");

        if (param.equals("error")) {
            // 抛异常
            throw new RuntimeException("mock-ex:" + param);
        } else {
            String result = "from host-ip: " + getInet4Address() + ",request param: " + param + ", response from " + providerNameConfig.getName();
            LOG.info("provider-demo -- provider config name: [" + providerNameConfig.getName() + ']');
            LOG.info("provider-demo -- response info: [" + result + "]");
            LOG.info("mock-normal:" + param);
            return result;
        }
    }

    /**
     * 延迟返回
     *
     * @param param 参数
     * @param delay 延时时间，单位毫秒
     * @throws InterruptedException
     */
    @RequestMapping(value = "/echo/slow/{param}", method = RequestMethod.GET)
    public String echoSlow(@PathVariable String param, @RequestParam(required = false) Integer delay) throws InterruptedException {
        int sleepTime = delay == null ? 1000 : delay;
        LOG.info("provider-demo -- slow request param: [" + param + "], Start sleep: [" + sleepTime + "]ms");
        Thread.sleep(sleepTime);
        LOG.info("provider-demo -- slow request param: [" + param + "], End sleep: [" + sleepTime + "]ms");
        String result = "from host-ip: " + getInet4Address()
                + "request param: " + param
                + ", slow response from " + providerNameConfig.getName()
                + ", sleep: [" + sleepTime + "]ms";
        return result;
    }

    /**
     * 简单的鉴权接口。
     * token = provider-demo 鉴权成功，其它失败。
     *
     * @param token 凭证
     * @return 鉴权结果
     */
    @RequestMapping(value = "/checkToken", method = RequestMethod.GET)
    public Map<String, Object> checkToken(@RequestParam String token) {
        LOG.info("provider-demo -- request param: [" + token + "]");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "provider-demo".equalsIgnoreCase(token));
        resultMap.put("payload", "this is payload");

        LOG.info("provider-demo -- response info: [" + resultMap + "]");
        return resultMap;
    }

    /**
     * 打印请求
     *
     * @param request 原始请求
     * @return 请求内容
     */
    @RequestMapping(value = "/printRequest", method = RequestMethod.GET)
    public Map<String, Object> printRequest(HttpServletRequest request) {
        Map<String, Object> requestMap = new LinkedHashMap<>();
        Map<String, String> headerMap = new LinkedHashMap<>();
        Map<String, String> parameterMap = new LinkedHashMap<>();
        Map<String, String> cookieMap = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            parameterMap.put(paramName, request.getParameter(paramName));
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Stream.of(cookies).forEach(e -> {
                cookieMap.put(e.getName(), e.getValue());
            });
        }
        requestMap.put("Protocol", request.getProtocol());
        requestMap.put("Method", request.getMethod());
        requestMap.put("URL", request.getRequestURL());
        requestMap.put("Parameters", parameterMap);
        requestMap.put("Headers", headerMap);
        requestMap.put("Cookies", cookieMap);
        requestMap.put("OriginalCookies", cookies);
        return requestMap;
    }


}