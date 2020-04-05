package com.example.thanhhien;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MyService.this);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this, "ThongBao")
                            .setSmallIcon(R.drawable.imgkhachhang)
                            .setContentTitle("Bạn đã tạo một đơn hàng thành công")
                            .setContentText("Nhấp vào để xem chi tiết")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    notificationManagerCompat.notify(1, builder.build());
                    return Service.START_STICKY;
    }

}

