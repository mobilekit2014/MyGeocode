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
    public static final int REQUEST_ADDRESS_SEARCH = 1001;  // �ּҷ� �˻� ��Ƽ��Ƽ ��û�ڵ�
    public static final int REQUEST_POSITION_SEARCH = 1002; // ��ǥ�� �˻� ��Ƽ��Ƽ ��û�ڵ�
     
    TextView textResultContents;  // �˻� ����� ��µǴ� �ؽ�Ʈâ
    RadioButton radioAdd, radioPos;  // �˻� ���� ������ư
    Geocoder gc;  // �����ڴ� ��ü
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        textResultContents = (TextView) findViewById(R.id.textResultContents);
        radioAdd = (RadioButton) findViewById(R.id.radioAddress);
        radioPos = (RadioButton) findViewById(R.id.radioPosition);
        
        // ��ư �̺�Ʈ ó��
        Button btnInput = (Button) findViewById(R.id.btnInputmove);
        btnInput.setOnClickListener(new OnClickListener() {
        	Intent intent;
            public void onClick(View v) {
            	if(radioAdd.isChecked()){  // �ּ� �˻� ������ư üũ���� �˻� 
            		intent = new Intent(getBaseContext(), AddressSearchActivity.class);
            		startActivityForResult(intent, REQUEST_ADDRESS_SEARCH);
            	}
            	else if(radioPos.isChecked()){
            		intent = new Intent(getBaseContext(), PositionSearchActivity.class);
            		startActivityForResult(intent, REQUEST_POSITION_SEARCH);
            	}
            }
        });
 
        // �����ڴ� ��ü ����
        gc = new Geocoder(this, Locale.KOREAN);
    }
    
    
    
    // �ٸ� ��Ƽ��Ƽ�κ��� ������� �޾ƿ��� �Լ�
    public void onActivityResult(int requestCode, int resultCode, Intent Data){
		super.onActivityResult(requestCode, resultCode, Data);
		
		if(requestCode == REQUEST_ADDRESS_SEARCH){  // �ּ� �Է°� �޾ƿ�
			String strAddr = Data.getExtras().getString("address");
			searchLocation(strAddr);
		}
		else if(requestCode == REQUEST_POSITION_SEARCH){  // ��/�浵 �Է°� �޾ƿ�
			String strLatitude = Data.getExtras().getString("lat");
			String strLongitude = Data.getExtras().getString("long");
			
			double latitude = Double.parseDouble(strLatitude);
			double longitude = Double.parseDouble(strLongitude);
			searchLocation(latitude, longitude);
		}
	}
 
    
    
    // �ּ� ���ڿ��� �Է¹޾� �ּ� �� ��/�浵 �˻�
    private void searchLocation(String searchStr) {
        // �˻� ����� �� ����Ʈ ����
        List<Address> listAddress = null;
        
        textResultContents.setText("");  // �˻� ���â �ʱ�ȭ
        
        try {
        	listAddress = gc.getFromLocationName(searchStr, 3);
 
        	if (listAddress != null) {
        		textResultContents.append("\n\'" + searchStr + "\'�� �˻� ��� �� : " + listAddress.size());
                
        		// ����Ʈ �����ŭ �ݺ����� ���� �˻� ��� ���
        		for (int i = 0; i < listAddress.size(); i++) {
        			int index = i+1;
        			Address outAddr = listAddress.get(i);
        			int addrCount = outAddr.getMaxAddressLineIndex() + 1;  // �ּ� ������ ��
        			StringBuffer outAddrStr = new StringBuffer();
                    
        			for (int k = 0; k < addrCount; k++) {   // �ּ� ������ ����ŭ �ݺ����� ���� �ּ� ��� 
        				outAddrStr.append(outAddr.getAddressLine(k));
        			}
                    
        			outAddrStr.append("\n\t���� : " + outAddr.getLatitude());
        			outAddrStr.append("\n\t�浵 : " + outAddr.getLongitude());
                
        			textResultContents.append("\n\t�ּ� [" + index + "] " + outAddrStr.toString());
        		}
        	}   
        } catch(IOException ex) {
            Log.d("MainActivity", "���� : " + ex.toString());
        }
    }

    
    
    // ��/�浵 ���� �Է¹޾� �ּ� �˻�
    private void searchLocation(double latitude, double longitude) {
        // �˻� ����� �� ����Ʈ ����
    	List<Address> listAddress = null;
 
    	textResultContents.setText("");  // �˻� ���â �ʱ�ȭ
    	
        try {
            listAddress = gc.getFromLocation(latitude, longitude, 3);
 
            if (listAddress != null) {
            	textResultContents.append("\n\'���� " + latitude + ", �浵 " + longitude + "\'�� �˻� ��� �� : " + listAddress.size());
            	
                for (int i = 0; i < listAddress.size(); i++) {
                	int index = i+1;
                    Address outAddr = listAddress.get(i);
                    int addrCount = outAddr.getMaxAddressLineIndex() + 1;
                    StringBuffer outAddrStr = new StringBuffer();
                    
                    for (int k = 0; k < addrCount; k++) {
                        outAddrStr.append(outAddr.getAddressLine(k));
                    }
                    outAddrStr.append("\n\t���� : " + outAddr.getLatitude());
                    outAddrStr.append("\n\t�浵 : " + outAddr.getLongitude());
 
                    textResultContents.append("\n\t�ּ� [" + index + "] " + outAddrStr.toString());
                }
            }
 
        } catch(IOException ex) {
            Log.d("MainActivity", "���� : " + ex.toString());
        }
 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}


