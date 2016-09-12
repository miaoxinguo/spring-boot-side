package io.github.miaoxinguo.sbs.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class HttpUtils {

    // 默认连接超时时间
    private static final int DEFAULT_CONNECT_TIMEOUT = 5 * 1000;

    // 默认会话超时时间
    private static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;

    private HttpUtils() {
    }

    /**
     * GET请求
     */
    public static String doGet(String url) throws IOException {
        return doGet(url, DEFAULT_CONNECT_TIMEOUT);
    }

    /**
     * GET请求,有超时时间
     */
    public static String doGet(String url, int connectTimeout) throws IOException {
        return Request.Get(url)
                .connectTimeout(connectTimeout)
                .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .execute()
                .returnContent()
                .asString();
    }

    /**
     * GET请求,支持BA认证
     */
    public static String doGet(String url, String id, String secret) throws IOException, InvalidKeyException {
        return doGet(url, id, secret, DEFAULT_CONNECT_TIMEOUT);
    }

    /**
     * GET请求,有超时时间,支持BA认证
     */
    public static String doGet(String url, String id, String secret,
                               int connectTimeout) throws IOException, InvalidKeyException {
        String date = DateUtils.toGmtString(LocalDateTime.now());
        String sign = sign("GET", url, secret, date);
        String authorization = "HLJ " + id + ":" + sign;
        return Request.Get(url)
                .connectTimeout(connectTimeout)
                .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .addHeader("Date", date)
                .addHeader("Authorization", authorization)
                .execute()
                .returnContent()
                .asString();
    }

    /**
     * POST请求
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, params, DEFAULT_CONNECT_TIMEOUT);
    }

    /**
     * POST请求,有超时时间
     */
    public static String doPost(String url, Map<String, String> params, int timeout) throws IOException {
        List<NameValuePair> formparams = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            formparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }

        String result = Request.Post(url)
                .connectTimeout(timeout)
                .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .body(new UrlEncodedFormEntity(formparams, "UTF-8"))
                .execute()
                .returnContent()
                .asString();
        return result;
    }

    /**
     * POST请求,支持BA认证
     */
    public static String doPost(String url, String id, String secret,
                                Map<String, String> params) throws IOException, InvalidKeyException {
        return doPost(url, id, secret, params, DEFAULT_CONNECT_TIMEOUT);
    }


    /**
     * POST请求,有超时时间,支持BA认证
     */
    public static String doPost(String url, String id, String secret, Map<String, String> params,
                                int timeout) throws IOException, InvalidKeyException {
        String date = DateUtils.toGmtString(LocalDateTime.now());
        String sign = sign("POST", url, secret, date);
        String authorization = "HLJ " + id + ":" + sign;
        List<NameValuePair> formparams = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            formparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }

        return Request.Post(url)
                .connectTimeout(timeout)
                .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .addHeader("Date", date)
                .addHeader("Authorization", authorization)
                .body(new UrlEncodedFormEntity(formparams, "UTF-8"))
                .execute()
                .returnContent()
                .asString();
    }

    /**
     * 发送 Post,请求的数据为JSON格式
     */
    public static String doPostJson(String url, String content) throws IOException {
        return doPostJson(url, content, DEFAULT_CONNECT_TIMEOUT);
    }

    /**
     * 发送 Post,请求的数据为JSON格式,有超时时间
     */
    public static String doPostJson(String url, String content, int timeout) throws IOException {
        return Request.Post(url)
                .connectTimeout(timeout)
                .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .addHeader("Accept", "application/json")
                .bodyString(content, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
    }

    /**
     * 发送 Post,请求的数据为JSON格式,,支持BA认证
     */
    public static String doPostJson(String url, String id, String secret,
                                    String content) throws IOException, InvalidKeyException {
        return doPostJson(url, id, secret, content, DEFAULT_CONNECT_TIMEOUT);
    }


    /**
     * 发送 Post,请求的数据为JSON格式,有超时时间,支持BA认证
     */
    public static String doPostJson(String url, String id, String secret, String content,
                                    int timeout) throws IOException, InvalidKeyException {
        String date = DateUtils.toGmtString(LocalDateTime.now());
        String sign = sign("POST", url, secret, date);
        String authorization = "HLJ " + id + ":" + sign;
        return Request.Post(url)
                .connectTimeout(timeout)
                .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .addHeader("Date", date)
                .addHeader("Authorization", authorization)
                .addHeader("Accept", "application/json")
                .bodyString(content, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
    }

    /**
     * 将url,method,date进行字符串的转换拼接,使用secret进行加密
     *
     * @return 加密完之后的字符串
     */
    private static String sign(String method, String url, String secret, String date) throws InvalidKeyException {
        /*
         * url为请求路径，类似 http://sms.honglingjinclub.com/sms/send?mobile=11212341234
         * 取其中 /sms/send 作为path进行签名
         */
        int beginIndex = url.indexOf("/", url.indexOf("://")+3);
        int endIndex = url.contains("?") ? url.indexOf("?") : url.length();

        String path = url.substring(beginIndex, endIndex);
        String str = method + path + date;
        return MacUtils.hmacSha1Base64(str, secret);
    }

}
