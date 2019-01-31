package com.example.notchme;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
	NotchService service;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
	
	    final Switch onSwitch = findViewById(R.id.onSwitch);
	    onSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
	    {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
			    if (isChecked)
			    {
			    	service.show();
			    }
			    else
			    {
			    	service.hide();
			    }
		    }
	    });
	
	    RadioGroup notchSelector = findViewById(R.id.PhoneGroup);
	    notchSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(RadioGroup radioGroup, int i)
		    {
			    switch (i)
			    {
				    case (R.id.iPhone):
				    	service.setType(R.drawable.iphone_x);
					    onSwitch.setChecked(true);
				    	break;
				    case (R.id.huawei):
				    	service.setType(R.drawable.huawei_p20);
					    onSwitch.setChecked(true);
				    	break;
				    case (R.id.pixel):
				    	service.setType(R.drawable.pixel_3);
					    onSwitch.setChecked(true);
				    	break;
				    default:
				    	break;
			    }
		    }
	    });
    }
    
    @Override
    protected void onStart()
    {
    	super.onStart();
    	Intent mIntent = new Intent(this, NotchService.class);
    	bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }
    
    ServiceConnection mConnection = new ServiceConnection() {
	    @Override
	    public void onServiceConnected(ComponentName componentName, IBinder iBinder)
	    {
		    NotchService.NotchBinder mNotchBinder = (NotchService.NotchBinder)iBinder;
		    service = mNotchBinder.getService();
	    }
	
	    @Override
	    public void onServiceDisconnected(ComponentName componentName)
	    {
			service = null;
	    }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
