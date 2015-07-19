package com.cong.notepad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Html.ImageGetter;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class edittravels extends Activity{
	private static final int SELECT_IMAGE =123;
	int rotate = 0; 
	private EditText titleedit;
	private EditText travelsedit;
	private ImageButton save;
	private ImageButton insertimage;
	private ImageButton takephoto;
	private String idString;
	private int id2;
	private String title="";
	private String travelsdata = "";
	private String timeText = "";
	public Cursor cursor=null;
	public String namestr="";
	private DBManage dm=null;
	private Bitmap bitmap = null;
	private String path = null;
	private int state =-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edittravels);
		titleedit=(EditText)findViewById(R.id.titleedit);
		travelsedit=(EditText)findViewById(R.id.travelsedit);
		save=(ImageButton)findViewById(R.id.save);
		save.setOnClickListener(new saveListener());
		insertimage=(ImageButton)findViewById(R.id.insertimage);
		insertimage.setOnClickListener(new insertimageListener());
		dm=new DBManage(this);
		Intent intent = getIntent();
		state = Integer.parseInt(intent.getStringExtra("state"));
		Log.i("log", "state---->"+state);
		if (state==2)
		{
			idString =intent.getStringExtra("id");
			Log.i("log", "id---->"+idString);
			id2 =  Integer.parseInt(idString);
			title = intent.getStringExtra("title");
			travelsdata = intent.getStringExtra("content");
			timeText = intent.getStringExtra("time");
			titleedit.setText(title);
			dm.open();
			int i=0;
			int start=0;
			int end=0;
			String str1=null;
			String str2="[";
			String str4="]";
			String iconname=null;
			SpannableString travelsSpan =new SpannableString(travelsdata);
			for(i=0;i<travelsdata.length();i++)
			{
				str1=travelsdata.substring(i, i+1);
				//travelsString+=str1;
				Log.i("log", str1);
				if(str1.equals(str2))
				{
					start=i+1;
				}
				if(str1.equals(str4))
				{
					end=i;
				namestr=travelsdata.substring(start,end);
				Log.i("log", namestr);
				cursor=dm.selcetPathByName(namestr);
				cursor.moveToFirst();
				path=cursor.getString(cursor.getColumnIndex("path"));
				cursor.close();
				namestr=null;
				Log.i("log", path);
				if(!(cursor==null))
				{
					int count=cursor.getCount();
					Log.i("log", "count----->"+count);
					BitmapFactory.Options options =new BitmapFactory.Options();
					options.inJustDecodeBounds =true;
					bitmap =BitmapFactory.decodeFile(path, options); //此时返回bm为空
					options.inJustDecodeBounds =false;
					int be = (int)(options.outHeight/ (float)100);
					if (be <= 0)
						be = 1;
					options.inSampleSize = be;
					bitmap=BitmapFactory.decodeFile(path,options);
					Drawable drawable = new BitmapDrawable(bitmap);
					drawable.setBounds(0, 0, 360, 280);
					ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
					travelsSpan.setSpan(span, start-1,end+1, 

Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				else
				{
					Log.i("log", "insert icon faile");
				}
				}
				
				}
			dm.close();	
			travelsedit.setText(travelsSpan);
		}
	}
	//保存按钮点击事件
	class saveListener implements OnClickListener{

		

		public void onClick(View v) {
			// TODO Auto-generated method stub
			title = titleedit.getText().toString();
			travelsdata= travelsedit.getText().toString();
			Log.i("log","title---->"+title);
			Log.i("log", "travels---->"+travelsdata);
				try{
					dm.open();
					if(state==1)					
					dm.insert(title, travelsdata);
					if (state==2)
					Log.i("log", "ready to alter");
					dm.update(id2, title, travelsdata);
					dm.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				Intent gobackIntent=new Intent(edittravels.this,MainActivity.class);
				edittravels.this.startActivity(gobackIntent);
				finish();
		}
		
	}
	//插入图片点击事件
	class insertimageListener implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Intent getImage = new Intent(Intent.ACTION_PICK,
					//MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			//startActivityForResult(getImage, SELECT_IMAGE);
			Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
			  intent.addCategory(Intent.CATEGORY_OPENABLE);
			  intent.setType("image/*");
			  intent.putExtra("return-data", true);
			  startActivityForResult(intent, SELECT_IMAGE);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK)
		{
			path=getpath(data.getData());
		BitmapFactory.Options options =new BitmapFactory.Options();
				        options.inJustDecodeBounds =true;
				         //获取这个图片的宽和高
				       bitmap =BitmapFactory.decodeFile(path, options); //此时返回bm为空
				        options.inJustDecodeBounds =false;			      
				         //计算缩放比
				        int be = (int)(options.outHeight/ (float)100);
				        if (be <= 0)
				            be = 1;
				        options.inSampleSize = be;
				        Log.i("log","be---->"+be);
				        //重新读入图片
				       bitmap=BitmapFactory.decodeFile(path,options);
				       int bitmapwidth=options.outWidth;
				       int bitmapheight=options.outHeight;
				       //检查图片是否要翻转
				       try {   
				           ExifInterface exifInterface = new ExifInterface(path);   
				           int result = exifInterface.getAttributeInt(   
				                   ExifInterface.TAG_ORIENTATION, 

ExifInterface.ORIENTATION_UNDEFINED);   
				             
				           switch(result) {   
				           case ExifInterface.ORIENTATION_ROTATE_90:   
				               rotate = 90; 
				               Log.i("log", "rotate----->"+rotate);
				               break;   
				           case ExifInterface.ORIENTATION_ROTATE_180:   
				               rotate = 180;   
				               Log.i("log", "rotate----->"+rotate);
				               break;   
				           case ExifInterface.ORIENTATION_ROTATE_270:   
				               rotate = 270;
				               Log.i("log", "rotate----->"+rotate);
				               break;   
				           default:   
				               break;   
				           } 
				     
				       }  catch (IOException e) {   
				           e.printStackTrace();   
				       }
				       if (rotate!=0&&bitmap!=null)
				        {
				        	Matrix matrix=new Matrix();
					        matrix.reset();
					        matrix.setRotate(rotate);
					        Bitmap bitmap1= Bitmap.createBitmap(bitmap, 0, 0, 

options.outWidth, options.outHeight, matrix, true);
					        bitmap = bitmap1;
					        rotate=0;
					        bitmapwidth=options.outHeight;
					        bitmapheight=options.outWidth;
				        }
				Drawable drawable = new BitmapDrawable(bitmap);
				drawable.setBounds(0, 0,bitmapwidth*2,bitmapheight*2);
				String iconname = path.substring(path.lastIndexOf("/")+1);
				String iconimfo="["+iconname+"]";
				SpannableString spannable = new SpannableString(iconimfo);
				ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
				spannable.setSpan(span, 0,iconimfo.length

(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				int select=travelsedit.getSelectionStart();
				Editable edit = travelsedit.getEditableText();
				edit.insert(select, spannable);
				try{
					dm.open();
					dm.inserticonpath(iconname, path);
					dm.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
		}
	}
	String getpath(Uri uri) {
		String[] projection = {MediaColumns.DATA};
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		startManagingCursor(cursor);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		// 记录图片的位置
		Log.i("log","column_index---->"+column_index);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	int poi = 0;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	public boolean onKeyDown(int KeyCode,KeyEvent event){
		if (KeyCode==KeyEvent.KEYCODE_BACK)
		{
			Intent goBackToMainIntent=new Intent(edittravels.this,MainActivity.class);
			this.startActivity(goBackToMainIntent);
			finish();
		}
		return false;
		
	}
	
}
