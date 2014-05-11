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

public class PositionSearchActivity extends Activity {
    
	EditText editLatitude, editLongitude;  // 위도, 경도 입력칸
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positionsearch);
 
        editLatitude = (EditText) findViewById(R.id.editLatitude);
        editLongitude = (EditText) findViewById(R.id.editLongitude);
        
        // 버튼 객체 생성 및 리스너 정의
        Button btnSearchtoPos = (Button) findViewById(R.id.btnSearchtoPosition);
        btnSearchtoPos.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		String strLatitude = editLatitude.getText().toString();
        		String strLongitude = editLongitude.getText().toString();
        		
        		if(strLatitude.equals("") == true || strLongitude.equals("") == true){
        			Toast.makeText(getBaseContext(), "위도 또는 경도를 입력하세요.", Toast.LENGTH_LONG).show();
        		}
        		else{
        			// 메인액티비티로 결과 전송
        			Intent resultIntent = new Intent();
					resultIntent.putExtra("lat", strLatitude);
					resultIntent.putExtra("long", strLongitude);
					setResult(2,resultIntent);
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


