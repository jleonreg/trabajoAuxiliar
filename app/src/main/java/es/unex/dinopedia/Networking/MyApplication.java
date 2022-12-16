package es.unex.dinopedia.Networking;

import android.app.Application;

import es.unex.dinopedia.Networking.AppContainer;

public class MyApplication extends Application {
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(this);
    }
}