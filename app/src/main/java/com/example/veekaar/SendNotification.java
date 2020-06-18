package com.example.veekaar;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class SendNotification {

    public SendNotification(String message , String heading , String notificationKey) {

    //I have not applied this app to masses and due to unfavourable circumstances.
        // Henceforth , I have used bruteForce here for notificationKey .
        //Keep in mind that this app can be applied to a large audience as it is backed by powerful Firebase GOOGLE servers!!!

        notificationKey = "ac9e41c9-9295-4763-951b-535d0b582eb8";
        try{
        JSONObject notificationContent =new JSONObject(
                "{'contents':{'en':'" + message + "'},"+
                        "'include_player_ids':['" + notificationKey + "']," +
                        "'headings':{'en': '" + heading + "'}}");
        OneSignal.postNotification(notificationContent, null);
    } catch (
    JSONException e) {
        e.printStackTrace();
    }

    }
}
