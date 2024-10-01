package cn.edu.dbsi.util;

import cn.edu.dbsi.dataetl.util.JobConfig;
import cn.edu.dbsi.dto.CubeSchema;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author 郭世明
 * @Description
 * @update 2017年7月7日 上午10:46:33
 */
public class HttpConnectDeal {
    /**
     * 处理get请求.
     *
     * @param url 请求路径
     * @return json
     */


    public static String get(String url) {
        // 实例化httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 实例化get方法
        HttpGet httpget = new HttpGet(url);
        // 请求结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 执行get方法
            response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");

                // content =
                // inputStreamToString(response.getEntity().getContent());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpget.releaseConnection();
        }
        return content;
    }

    /**
     * 处理post请求.
     *
     * @param url    请求路径
     * @param params 参数
     * @return json
     */
    public static String post(String url, Map<String, String> params) {
        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 实例化post方法
        HttpPost httpPost = new HttpPost(url);

        // 处理参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        // 结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 提交的参数
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            // 将参数给post方法
            httpPost.setEntity(uefEntity);

            // 执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return content;
    }

    /**
     * 发送带有json参数的请求
     *
     * @param uri
     * @param json
     * @return
     */
    public static String postJson(String uri, JSONObject json) {
        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //利用Preemptive authentication认证机制，不同的saiku其hostname是不同的
//        HttpHost targetHost = new HttpHost("10.65.1.92", 8080, "http");
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials(
//                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
//                new UsernamePasswordCredentials("admin", "admin"));
        // 创建 AuthCache 实例
//        AuthCache authCache = new BasicAuthCache();
        // 创建 BASIC scheme 对象 and 将它加入到本地的 auth cache
//        BasicScheme basicAuth = new BasicScheme();
//        authCache.put(targetHost, basicAuth);
        // 将 AuthCache 加入到执行的上下文
//        HttpClientContext context = HttpClientContext.create();
//        context.setCredentialsProvider(credsProvider);
//        context.setAuthCache(authCache);

        // 实例化post方法
        HttpPost httpPost = new HttpPost(uri);

        // 结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 提交的参数
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
//            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");

            // 将参数给post方法
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type", "application/json");
            // 执行post请求
//            response = httpclient.execute(targetHost, httpPost, context);
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return content;
    }

    /**
     * 处理文件请求，先验证，然后发起请求
     *
     * @param path
     * @param uri
     */
    public static String postMutilpart(String path, String uri, CubeSchema schema) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //利用Preemptive authentication认证机制
//        HttpHost targetHost = new HttpHost("10.65.1.92", 8080, "http");
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials(
//                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
//                new UsernamePasswordCredentials("admin", "admin"));
        // 创建 AuthCache 实例
//        AuthCache authCache = new BasicAuthCache();
        // 创建 BASIC scheme 对象 and 将它加入到本地的 auth cache
//        BasicScheme basicAuth = new BasicScheme();
//        authCache.put(targetHost, basicAuth);
        // 将 AuthCache 加入到执行的上下文
//        HttpClientContext context = HttpClientContext.create();
//        context.setCredentialsProvider(credsProvider);
//        context.setAuthCache(authCache);
        //创建POST请求实例
        HttpPost uploadFile = new HttpPost(uri);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        final ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
        builder.addTextBody("name", schema.getName(), contentType);
        //builder.addTextBody("file", schema.getName(), contentType);
        // 将文件加入到POST请求:
        File f = new File(path);
        try {
            builder.addBinaryBody(
                    "file",
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            //将认证、POST请求，上下文一并发送
//            response = httpclient.execute(targetHost, uploadFile, context);
            response = httpclient.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity responseEntity = response.getEntity();
            System.out.println(responseEntity.toString());
            return "ok";
        } else {
            return "fail";
        }

    }

    /**
     * 对kylin 发起 post json 请求
     *
     * @param uri
     * @param json
     * @return
     */
    public static String postJson2Kylin(JobConfig jobConfig, String uri, JSONObject json) {
        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();

        /*String kylin_url = jobConfig.getKylinUrl();
        int index1 = kylin_url.indexOf("//");
        int index2 = kylin_url.lastIndexOf(":");
        String hostname = kylin_url.substring(index1 + 2, index2);
        int index3 = kylin_url.lastIndexOf("/");
        int port = Integer.parseInt(kylin_url.substring(index2 + 1, index3));*/

        //利用Preemptive authentication认证机制，不同的saiku其hostname是不同的
        //HttpHost targetHost = new HttpHost("hostname", port, "http");
        HttpHost targetHost = new HttpHost("10.1.18.211", 7070, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials("admin", "KYLIN"));
        // 创建 AuthCache 实例
        AuthCache authCache = new BasicAuthCache();
        // 创建 BASIC scheme 对象 and 将它加入到本地的 auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);
        // 将 AuthCache 加入到执行的上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        // 实例化post方法
        HttpPost httpPost = new HttpPost(uri);

        // 结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 提交的参数
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
//            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");

            // 将参数给post方法
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type", "application/json");
            // 执行post请求
            response = httpclient.execute(targetHost, httpPost, context);
//            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 500) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpPost.releaseConnection();
        }
        return content;
    }


    /**
     * 对kylin 发起 put json 请求
     *
     * @param uri
     * @param json
     * @return
     */
    public static String putJson2Kylin(JobConfig jobConfig, String uri, JSONObject json) {
        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();


        //利用Preemptive authentication认证机制，不同的saiku其hostname是不同的
        //HttpHost targetHost = new HttpHost("hostname", port, "http");
        HttpHost targetHost = new HttpHost("10.1.18.211", 7070, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials("admin", "KYLIN"));
        // 创建 AuthCache 实例
        AuthCache authCache = new BasicAuthCache();
        // 创建 BASIC scheme 对象 and 将它加入到本地的 auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);
        // 将 AuthCache 加入到执行的上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        // 实例化put方法

        HttpPut httpPut = new HttpPut(uri);
        // 结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 提交的参数
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");

            stringEntity.setContentType("application/json");


            // 将参数给put方法
            httpPut.setEntity(stringEntity);

            httpPut.setHeader("Content-type", "application/json");

            // 执行put请求
            response = httpclient.execute(targetHost, httpPut, context);

            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpPut.releaseConnection();
        }
        return content;
    }

    /**
     * 对kylin 发起 get 请求
     *
     *  对kylin 发起 get 请求
     * @param jobConfig
     * @param uri
     * @return
     */
    public static String getFromKylin(JobConfig jobConfig, String uri) {
        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //利用Preemptive authentication认证机制，不同的saiku其hostname是不同的
        //HttpHost targetHost = new HttpHost("hostname", port, "http");
        HttpHost targetHost = new HttpHost("10.1.18.211", 7070, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials("admin", "KYLIN"));
        // 创建 AuthCache 实例
        AuthCache authCache = new BasicAuthCache();
        // 创建 BASIC scheme 对象 and 将它加入到本地的 auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);
        // 将 AuthCache 加入到执行的上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        // 实例化get方法

        HttpGet httpGet = new HttpGet(uri);
        // 结果
        CloseableHttpResponse response = null;
        String content = "";
        try {

            // 执行put请求
            response = httpclient.execute(targetHost, httpGet, context);

            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpGet.releaseConnection();
        }
        return content;
    }



    public static String getMdxFromSaiku( String fileName){
        String content = "";

        return content;
    }

    public static String postStream(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.getOutputStream().write("theCityName=北京".getBytes("UTF-8"));
            httpConn.getOutputStream().flush();
            InputStreamReader is = new InputStreamReader(httpConn.getInputStream(), "utf-8");
            BufferedReader in = new BufferedReader(is);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("测试" + inputLine);
            }
            in.close();
            httpConn.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

}
