package com.huawei.works.videolive.textstyle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author mao
 * @date 18/1/18
 */
public class GifDrawalbe extends AnimationDrawable {
    private int resId;
    private Context context;
    private AtomicBoolean bParsing = new AtomicBoolean(false);
    GifHelper helper;
    private List<UpdateUIListen> updateUIListenList;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public boolean isParsing() {
        return bParsing.get();
    }

    public GifDrawalbe(Context context, int id) {
        resId = id;
        this.context = context;
        bParsing.set(false);
        updateUIListenList = new ArrayList();
        setOneShot(false);
        Drawable drawable = context.getResources().getDrawable(id);
        setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        invalidateSelf();
    }

    public void readFrames(boolean bStarted) {
        if (!bParsing.get()) {
            parseFrame();
        }

        if (bStarted) {
            start();
        }

    }

    private void parseFrame() {
        synchronized (this) {
            if (!bParsing.get()) {
                GifHelper helper = new GifHelper();
                helper.read(context.getResources().openRawResource(resId));
                int gifCount = helper.getFrameCount();
                if (gifCount <= 0) {
                    bParsing.set(false);
                    return;
                }

                BitmapDrawable bd = new BitmapDrawable(context.getResources(), helper.getImage());
                addFrame(bd, helper.getDelay(0));

                for (int i = 1; i < helper.getFrameCount(); ++i) {
                    addFrame(new BitmapDrawable((Resources) null, helper.nextBitmap()), helper.getDelay(i));
                }

                bParsing.set(true);
            }

        }
    }

    public void run() {
        super.run();
        invalidateSelf();
        synchronized (this) {
            Iterator var3 = updateUIListenList.iterator();

            while (var3.hasNext()) {
                GifDrawalbe.UpdateUIListen listen = (GifDrawalbe.UpdateUIListen) var3.next();
                listen.updateUI();
            }

        }
    }

    public void addListen(GifDrawalbe.UpdateUIListen listen) {
        synchronized (this) {
            if (!updateUIListenList.contains(listen)) {
                updateUIListenList.add(listen);
            }

        }
    }

    public void removeListen(GifDrawalbe.UpdateUIListen listen) {
        synchronized (this) {
            updateUIListenList.remove(listen);
        }
    }

    public void scheduleSelf(Runnable what, long when) {
        synchronized (this) {
            if (updateUIListenList.size() <= 0) {
                stop();
                return;
            }
        }

        handler.postDelayed(this, 150L);
    }


    public interface UpdateUIListen {
        void updateUI();
    }
}
