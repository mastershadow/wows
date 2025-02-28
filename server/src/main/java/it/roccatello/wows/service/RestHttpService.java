package it.roccatello.wows.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
@Slf4j
public class RestHttpService {
  @Getter private final OkHttpClient client;

  public RestHttpService() {
    this.client = new OkHttpClient().newBuilder()
        .followRedirects(false)
        .addInterceptor(new DefaultContentTypeInterceptor("application/json"))
        .callTimeout(90, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build();
  }

  public Response request(Request request) {
    try {
      return client.newCall(request).execute();
    } catch (IOException e) {
      log.error("Error serving request", e);
      return null;
    }
  }

  public Observable<Response> requestObservable(Request request) {
    return Observable.create(emitter -> {
      try {
        final Response response = client.newCall(request).execute();
        emitter.onNext(response);
        emitter.onComplete();
      } catch (IOException e) {
        log.error("Error serving request", e);
        emitter.onError(e);
      }
    });
  }

  public class DefaultContentTypeInterceptor implements Interceptor {
    private final String contentType;

    public DefaultContentTypeInterceptor(String contentType) {
      this.contentType = contentType;
    }

    public Response intercept(Interceptor.Chain chain)
        throws IOException {

      Request originalRequest = chain.request();
      Request requestWithUserAgent = originalRequest
          .newBuilder()
          .header("Content-Type", contentType)
          .build();

      return chain.proceed(requestWithUserAgent);
    }
  }

}
