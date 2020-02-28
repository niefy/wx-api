package com.github.niefy.common.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpClient {
    private String charset = "UTF-8";
    private String url;
    private Method method= Method.GET;
    private Map<String, String> param;
    private Map<String, String> headers;
    private String requestBody;
    public enum Method{
        /**
         * GET请求
         */
        GET("GET"),
        /**
         * POST请求
         */
        POST("POST"),
        /**
         * PUT请求
         */
        PUT("PUT"),
        /**
         * DELETE请求
         */
        DELETE("DELETE");
        private String type;
        Method(String type){
            this.type=type;
        }
    }
    public HttpClient(String url) {
        this.url=url;
    }

    /**
     * 打开连接并返回结果
     * @return
     */
    public String open(){
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, param), method.type, headers);
            conn.connect();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private  HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(19000);
        conn.setReadTimeout(19000);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }


    private  String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, charset));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Build queryString of the url
     */
    private  String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (!url.contains("?")) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }

        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null && !"".equals(value.trim())) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    public String getCharset() {
        return charset;
    }

    public HttpClient setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public HttpClient setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public HttpClient setParam(Map<String, String> param) {
        this.param = param;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpClient setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public HttpClient setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }
}