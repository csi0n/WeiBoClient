package com.weibo.activity;
import com.weibo.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

 /**
 * @作者:陈华清
 *
 * @版本:1.0
 * @生成时期:2014年8月6日 下午8:26:58
 * @com.api.example.app
 */
public class About extends BaseActivity {
	 
	ImageView imageview1;
	Button button1, button2, button3, button4;

	public void findview() {
		imageview1 = (ImageView) findViewById(R.id.imageView2);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);

	}

	
	public void listen() {
		imageview1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				About.this.finish();
			}
		});
		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://www.jsit.edu.cn/");
				intent.setData(content_url);
				startActivity(intent);
				
			}
		});
		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://42.121.113.32/weibo/");
				intent.setData(content_url);
				startActivity(intent);
				
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://www.baidu.com/#wd=%E7%8E%8B%E5%8D%8E&rsv_spt=1&issp=1&rsv_bp=0&ie=utf-8&tn=baiduhome_pg&rsv_sug3=5&rsv_sug4=443&rsv_sug1=5&oq=wanghua&rsv_sug2=0&f=3&rsp=5&inputT=3739");
				intent.setData(content_url);
				startActivity(intent);
				}
		});
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://www.baidu.com/#wd=%E9%99%88%E5%8D%8E%E6%B8%85and%E5%AD%99%E4%BC%9F&ie=utf-8&tn=baiduhome_pg&oq=wanghua&f=8&rsv_bp=1&rsv_spt=1&rsv_sug3=21&rsv_sug4=1012&rsv_sug1=13&rsv_sug2=0&inputT=5275&bs=%E9%99%88%E5%8D%8E%E6%B8%85%EF%BC%8C%E5%AD%99%E4%BC%9F");
				intent.setData(content_url);
				startActivity(intent);
			
			}
		});
	}

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_about);
		findview();
		listen();
	}
}
