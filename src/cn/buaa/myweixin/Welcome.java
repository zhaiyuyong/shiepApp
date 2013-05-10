package cn.buaa.myweixin;


import cn.edu.shiep.utils.ConstantUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Welcome extends Activity {
	private EditText editText;
	private Button set_ip_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        editText = (EditText) findViewById(R.id.set_ip_edt);
        set_ip_btn = (Button) findViewById(R.id.set_ip_btn);
        set_ip_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = editText.getText().toString().trim();
				ConstantUtil.URL = "http://"+ip+":8088/shiep/MainServlet";
			}
		});
    }
    public void welcome_login(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,Login.class);
		startActivity(intent);
		//this.finish();
      }  
    public void welcome_register(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,RegisterActivity.class);
		startActivity(intent);
		//this.finish();
      }  
   
}
