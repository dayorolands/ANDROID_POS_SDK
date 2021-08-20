package com.interswitchng.interswitchpossdkdemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDex;

import com.interswitchng.interswitchpossdkdemo.utils.MockDevice;
import com.interswitchng.smartpos.IswPos;
import com.interswitchng.smartpos.emv.pax.services.POSDeviceImpl;
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice;
import com.interswitchng.smartpos.shared.models.core.Environment;
import com.interswitchng.smartpos.shared.models.core.IswLocal;
import com.interswitchng.smartpos.shared.models.core.POSConfig;
import com.interswitchng.smartpos.shared.models.core.PurchaseConfig;

public class POSApplication extends Application {

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        configureTerminal();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void configureTerminal() {
        POSDevice device;

        if (BuildConfig.MOCK) {
            device = new MockDevice();
        } else {
//            Drawable logo = ContextCompat.getDrawable(this, R.drawable.ic_app_logo);
            Drawable logo = ContextCompat.getDrawable(this, R.drawable.isw_logo);
            Bitmap bm = drawableToBitmap(logo);

            POSDeviceImpl service = POSDeviceImpl.create(getApplicationContext());
            service.setCompanyLogo(bm);
            device = service;
        }

        String clientId = "IKIA4733CE041F41ED78E52BD3B157F3AAE8E3FE153D";
        String clientSecret = "t1ll73stS3cr3t";
        String alias = "000001";
        String merchantCode = "MX1065";
        String merchantPhone = "080311402392";

        POSConfig config = new POSConfig(alias, clientId, clientSecret, merchantCode, merchantPhone, Environment.Production);
        config.withPurchaseConfig(new PurchaseConfig(1, "tech@isw.ng", IswLocal.NIGERIA));

        // setup terminal
        IswPos.setupTerminal(this, device, null, config, true);
    }
}
