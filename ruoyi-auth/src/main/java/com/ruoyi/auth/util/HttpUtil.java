package com.ruoyi.auth.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 地址调用工具类
 *
 * @author chenzhongxin
 * @date 2024/11/28
 * */

@Component
public class HttpUtil {

    private final RestTemplate restTemplate;

    // 构造器注入 RestTemplate
    public HttpUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 发送 GET 请求
     *
     * @param url 请求地址
     * @param headers 请求头 (可选)
     * @return 响应结果
     */
    public ResponseEntity<String> sendGet(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    /**
     * 发送 POST 请求
     *
     * @param url 请求地址
     * @param body 请求体
     * @param headers 请求头 (可选)
     * @return 响应结果
     */
    public ResponseEntity<String> sendPost(String url, Object body, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    /**
     * 发送 PUT 请求
     *
     * @param url 请求地址
     * @param body 请求体
     * @param headers 请求头 (可选)
     * @return 响应结果
     */
    public ResponseEntity<String> sendPut(String url, Object body, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    /**
     * 发送 DELETE 请求
     *
     * @param url 请求地址
     * @param headers 请求头 (可选)
     * @return 响应结果
     */
    public ResponseEntity<String> sendDelete(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
    }
}