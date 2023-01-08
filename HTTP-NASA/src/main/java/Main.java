import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main {
    public static final String REMOTE_SERVICE_URI = "https://api.nasa.gov/planetary/apod?api_key=J662LjD8d6RIQHkdP7SsDJbpdgSSg22S12CnYFY2";
    public static final ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature. ACCEPT_SINGLE_VALUE_AS_ARRAY);

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("My Test Service")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        CloseableHttpResponse response = httpClient.execute(httpGet(REMOTE_SERVICE_URI));
        Message obj = mapper.readValue(response.getEntity().getContent(), new TypeReference<>(){});
        response = httpClient.execute(httpGet(obj.getUrl()));
        FileOutputStream file = new FileOutputStream("d://ISS_TIANHE_FINAL_4_APOD1024.jpg");
        int a;
        while ((a = response.getEntity().getContent().read()) !=-1){
            file.write(a);
        }
    }

    public static HttpGet httpGet(String url) {
        HttpGet request = new HttpGet(url);
        return request;
    }
}

