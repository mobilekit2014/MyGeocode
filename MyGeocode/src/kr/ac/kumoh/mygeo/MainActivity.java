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

public class MainActivity extends Activity {
    public static final int REQUEST_ADDRESS_SEARCH = 1001;  // 주소로 검색 액티비티 요청코드
    public static final int REQUEST_POSITION_SEARCH = 1002; // 좌표로 검색 액티비티 요청코드
     
    TextView textResultContents;  // 검색 결과가 출력되는 텍스트창
    RadioButton radioAdd, radioPos;  // 검색 유형 라디오버튼
    Geocoder gc;  // 지오코더 객체
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        textResultContents = (TextView) findViewById(R.id.textResultContents);
        radioAdd = (RadioButton) findViewById(R.id.radioAddress);
        radioPos = (RadioButton) findViewById(R.id.radioPosition);
        
        // 버튼 이벤트 처리
        Button btnInput = (Button) findViewById(R.id.btnInputmove);
        btnInput.setOnClickListener(new OnClickListener() {
        	Intent intent;
            public void onClick(View v) {
            	if(radioAdd.isChecked()){  // 주소 검색 라디오버튼 체크여부 검사 
            		intent = new Intent(getBaseContext(), AddressSearchActivity.class);
            		startActivityForResult(intent, REQUEST_ADDRESS_SEARCH);
            	}
            	else if(radioPos.isChecked()){
            		intent = new Intent(getBaseContext(), PositionSearchActivity.class);
            		startActivityForResult(intent, REQUEST_POSITION_SEARCH);
            	}
            }
        });
 
        // 지오코더 객체 생성
        gc = new Geocoder(this, Locale.KOREAN);
    }
    
    
    
    // 다른 액티비티로부터 결과값을 받아오는 함수
    public void onActivityResult(int requestCode, int resultCode, Intent Data){
		super.onActivityResult(requestCode, resultCode, Data);
		
		if(requestCode == REQUEST_ADDRESS_SEARCH){  // 주소 입력값 받아옴
			String strAddr = Data.getExtras().getString("address");
			searchLocation(strAddr);
		}
		else if(requestCode == REQUEST_POSITION_SEARCH){  // 위/경도 입력값 받아옴
			String strLatitude = Data.getExtras().getString("lat");
			String strLongitude = Data.getExtras().getString("long");
			
			double latitude = Double.parseDouble(strLatitude);
			double longitude = Double.parseDouble(strLongitude);
			searchLocation(latitude, longitude);
		}
	}
 
    
    
    // 주소 문자열을 입력받아 주소 및 위/경도 검색
    private void searchLocation(String searchStr) {
        // 검색 결과가 들어갈 리스트 선언
        List<Address> listAddress = null;
        
        textResultContents.setText("");  // 검색 결과창 초기화
        
        try {
        	listAddress = gc.getFromLocationName(searchStr, 3);
 
        	if (listAddress != null) {
        		textResultContents.append("\n\'" + searchStr + "\'의 검색 결과 수 : " + listAddress.size());
                
        		// 리스트 사이즈만큼 반복문을 돌려 검색 결과 출력
        		for (int i = 0; i < listAddress.size(); i++) {
        			int index = i+1;
        			Address outAddr = listAddress.get(i);
        			int addrCount = outAddr.getMaxAddressLineIndex() + 1;  // 주소 단위의 수
        			StringBuffer outAddrStr = new StringBuffer();
                    
        			for (int k = 0; k < addrCount; k++) {   // 주소 단위의 수만큼 반복문을 돌려 주소 출력 
        				outAddrStr.append(outAddr.getAddressLine(k));
        			}
                    
        			outAddrStr.append("\n\t위도 : " + outAddr.getLatitude());
        			outAddrStr.append("\n\t경도 : " + outAddr.getLongitude());
                
        			textResultContents.append("\n\t주소 [" + index + "] " + outAddrStr.toString());
        		}
        	}   
        } catch(IOException ex) {
            Log.d("MainActivity", "예외 : " + ex.toString());
        }
    }

    
    
    // 위/경도 값을 입력받아 주소 검색
    private void searchLocation(double latitude, double longitude) {
        // 검색 결과가 들어갈 리스트 선언
    	List<Address> listAddress = null;
 
    	textResultContents.setText("");  // 검색 결과창 초기화
    	
        try {
            listAddress = gc.getFromLocation(latitude, longitude, 3);
 
            if (listAddress != null) {
            	textResultContents.append("\n\'위도 " + latitude + ", 경도 " + longitude + "\'의 검색 결과 수 : " + listAddress.size());
            	
                for (int i = 0; i < listAddress.size(); i++) {
                	int index = i+1;
                    Address outAddr = listAddress.get(i);
                    int addrCount = outAddr.getMaxAddressLineIndex() + 1;
                    StringBuffer outAddrStr = new StringBuffer();
                    
                    for (int k = 0; k < addrCount; k++) {
                        outAddrStr.append(outAddr.getAddressLine(k));
                    }
                    outAddrStr.append("\n\t위도 : " + outAddr.getLatitude());
                    outAddrStr.append("\n\t경도 : " + outAddr.getLongitude());
 
                    textResultContents.append("\n\t주소 [" + index + "] " + outAddrStr.toString());
                }
            }
 
        } catch(IOException ex) {
            Log.d("MainActivity", "예외 : " + ex.toString());
        }
 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}


