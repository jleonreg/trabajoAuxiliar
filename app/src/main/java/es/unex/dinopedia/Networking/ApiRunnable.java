package es.unex.dinopedia.Networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRunnable implements Runnable{

    private final ApiListener mApiListener;
    private final Api service;

    public ApiRunnable(ApiListener listener) {
        mApiListener = listener;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gist.githubusercontent.com/jleonreg/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(Api.class);
    }

    @Override
    public void run() {
        try {
            List<Dinosaurio> dinos = service.getListDinosaurios().execute().body();
            List<Logro> logros = service.listLogros().execute().body();
            AppExecutors.getInstance().mainThread().execute(() -> mApiListener.onLoaded(dinos, logros));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}