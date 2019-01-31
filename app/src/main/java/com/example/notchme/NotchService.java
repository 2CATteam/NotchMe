package com.example.notchme;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

public class NotchService extends Service implements NotchInterface {
	NotchView mView;
	IBinder binder = new NotchBinder();
	@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView = new NotchView(this);
	    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
			    Math.round(getResources().getDimension(R.dimen.notch_width)),
			    Math.round(getResources().getDimension(R.dimen.notch_height)),
			    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
			    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
			    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
			    PixelFormat.TRANSPARENT
	    );
	    params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
	    params.setTitle("Notch");
	    WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
	    wm.addView(mView, params);
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	if (mView != null)
	    {
	    	mView.hide();
		    ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mView);
		    mView = null;
	    }
    }

    @Override
    public void show() {
		mView.show();
    }

    @Override
    public void hide() {
		mView.hide();
    }

    @Override
    public void setType(int toSet) {
		this.mView.setDrawable(toSet);
    }
    class NotchView extends ViewGroup
    {
    	int drawable;
    	Context context;
        NotchView(Context context)
        {
            super(context);
            this.context = context;
            this.drawable = R.drawable.ic_launcher_foreground;
        }
        
        void hide()
        {
        	this.setBackground(null);
        }
        
        void show()
        {
	        this.setBackground(ContextCompat.getDrawable(context, drawable));
        }
        
        void setDrawable(int toSet) {this.drawable = toSet; this.show();}

        @Override
        protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        }
    }
    
    class NotchBinder extends Binder
    {
    	public NotchService getService()
	    {
	    	return NotchService.this;
	    }
    }
}
