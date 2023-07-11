package com.technyks.testFCMApp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.util.Log;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "MyFirebaseMsgService";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    // TODO: Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    Log.d(TAG, "From: " + remoteMessage.getFrom());

    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      Log.d(TAG, "Message data payload: " + remoteMessage.getData());

      if (/* Check if data needs to be processed by long running job */ true) {
        // For long-running tasks (10 seconds or more) use WorkManager.
        scheduleJob();
      } else {
        // Handle message within 10 seconds
        handleNow();
      }
    }

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
    }

    // Also, if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated. See sendNotification method below.
  }

  private void scheduleJob() {
    // [START dispatch_job]
    OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class).build();
    WorkManager.getInstance(this).enqueue(work);
    // [END dispatch_job]
  }

  private void handleNow() {
    Log.d(TAG, "Short lived task is done.");
  }

  public static class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
      super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
      // TODO: Add long-running task here.
      return Result.success();
    }
  }
}
