package com.aylingunes.instagramcloneparseexample;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterActivity extends Application { // app' e extend et


    @Override
    public void onCreate() { //oncreate sonrası bazıbilgiler verilmeli
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG); // logcatte hangi parse soutlarını alacağını beliritoyruz
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("fyWj5VUBF7LJg1HFR2oUr6RiAKEQN4oGINlerstr")
                        .clientKey("ODCb25ns0f5WKqHIqQAZ0k3BLLSLk1GcW3i2hjrg")
                        .server("https://parseapi.back4app.com/")
                .build()
        );             // başlat config ver





    }
}
