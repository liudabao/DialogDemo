package com.example.dialogdemo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button btn_1;
	Button btn_2;
	Button btn_3;
	Button btn_4;
	Button btn_5;
	ImageView picture;
	RelativeLayout layout;
	Uri imageUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView(){
		btn_1=(Button)findViewById(R.id.button1);
		btn_2=(Button)findViewById(R.id.button2);
		btn_3=(Button)findViewById(R.id.button3);
		btn_4=(Button)findViewById(R.id.button4);
		btn_5=(Button)findViewById(R.id.button5);
		layout=(RelativeLayout)findViewById(R.id.layout);
		picture=(ImageView)findViewById(R.id.imageView1);
		
		btn_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showAlertDialog();
			}
		});
		
		btn_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showProgressDialog();
			}
		});
		
		btn_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showNotification();
			}
		});
		
		btn_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showProgress();
			}
		});
		
		btn_5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showPicture();
			}
		});
	}
	
	private void showAlertDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("AlertDialog");
		builder.setMessage("test");
		builder.setCancelable(true);
		builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "alertdialg show", 1000).show();
			}
		});
		builder.create();
		builder.show();
	}
	
	private void showProgressDialog(){
		ProgressDialog progress=new ProgressDialog(MainActivity.this);
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progress.setIcon(R.drawable.ic_launcher);
		progress.setTitle("progressdialog");
		progress.setMessage("test");
		progress.setCancelable(true);
        progress.setMax(100);
        progress.setButton(DialogInterface.BUTTON_POSITIVE,"yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "progressdialg show", 1000).show();
			}
		});
        progress.show();
		
	}
	
	private void showNotification(){
		NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification=new Notification.Builder(MainActivity.this)
		.setTicker("new message")
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentText("this is a notification")
		.build();
		manager.notify(1,notification);
	}
	
	private void showProgress(){
		ProgressBar progressBar=new ProgressBar(MainActivity.this,null,
				android.R.attr.progressBarStyleLargeInverse);
		progressBar.setMax(1000);
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		progressBar.setLayoutParams(params);
		layout.addView(progressBar);
	
	}
	
	private void showPicture(){
		
		try {
			File imageFile=new File(Environment.getExternalStorageDirectory(),"temp.jpg");
			if(imageFile.exists()){
				imageFile.delete();
			}
			imageFile.createNewFile();
			imageUri=Uri.fromFile(imageFile);
			Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			//startActivity(intent);
			startActivityForResult(intent, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		switch (requestCode) {
	
		case 1:
			
			if (resultCode == RESULT_OK) {	
			
				Intent intent = new Intent("com.android.camera.action.CROP");				
				intent.setDataAndType(imageUri, "image/*");				
				intent.putExtra("scale", true);				
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);				
				startActivityForResult(intent, 2); // 启动裁剪程序
	
			}	
			break;
	
		case 2:
	
			if (resultCode == RESULT_OK) {
	
				try {
	
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
	
				} catch (FileNotFoundException e) {	
					e.printStackTrace();	
				}	
			}	
			break;
	
		default:
	
			break;
	}
	}
}
