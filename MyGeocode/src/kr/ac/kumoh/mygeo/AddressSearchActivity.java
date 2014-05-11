package kr.ac.kumoh.mygeo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddressSearchActivity extends Activity {
    
	EditText editAddress;  // �ּ� �Է�ĭ
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresssearch);
 
        editAddress = (EditText) findViewById(R.id.editAddress);
        
        // �˻� ��ư ��ü ���� �� ������ ����
        Button btnSearchtoAddr = (Button) findViewById(R.id.btnSearchtoAddress);
        btnSearchtoAddr.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String strAddress = editAddress.getText().toString();
        		
        		if(strAddress.equals("") == true){
        			Toast.makeText(getBaseContext(), "�ּҸ� �Է��ϼ���.", Toast.LENGTH_LONG).show();
        		}
        		else{
        			// ���ξ�Ƽ��Ƽ�� ��� ����
        			Intent resultIntent = new Intent();
					resultIntent.putExtra("address", strAddress);
					setResult(1,resultIntent);  
					finish();
        		}
        	}
        });
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}


