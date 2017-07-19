package com.course.innopolis.intentserviceexample;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import static java.lang.Thread.sleep;

/**
 * Created by Cristina on 19.07.2017.
 */

public class ValuteService extends IntentService{
    StringBuilder message;
    volatile boolean rubMonitoring, usMonitoring, euMonitoring;
    Context context;


    public ValuteService() {
        super("sdfsdf");
    }
//    public ValuteService(String name, Context context) {
//        super(name);
//        this.context = context;
//    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        final PendingIntent pendingIntent =
                intent.getParcelableExtra("pi");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                message = new StringBuilder("");
                for(;;){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    message = new StringBuilder("");
                    if(rubMonitoring){
                        message.append("\n rub course is: " + 100);

                    }
                    if(usMonitoring){
                        message.append("\n us course is: " + 0);
                    }
                    if(euMonitoring){
                        message.append("\n eu course is: " + 1);
                    }
                    if(!"".equals(message.toString())){
                        Intent intent1 = new Intent();
                        intent1.putExtra("message", message.toString());
                        try {
                            pendingIntent.send(ValuteService.this, 0, intent1);
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                    else break;
                }
            }
        });
        rubMonitoring = intent.getBooleanExtra("rur", false);
        usMonitoring = intent.getBooleanExtra("us", false);
        euMonitoring = intent.getBooleanExtra("eu", false);
        thread.start();

    }

}
