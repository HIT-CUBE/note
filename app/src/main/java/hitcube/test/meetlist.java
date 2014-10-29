package hitcube.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.view.View;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.content.res.Resources;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.content.Intent;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class meetlist extends ListActivity {

       private class createprojectOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),"创建会议" ,Toast.LENGTH_SHORT ).show();
            ShowDialogofmeet();
        }
    }

    private void ShowDialogofmeet()
    {
        LayoutInflater factory = LayoutInflater.from(meetlist.this);
        final View textEntryView = factory.inflate(R.layout.meetcreate, null);
        AlertDialog mDialog = new AlertDialog.Builder(this)
                //.setIcon(R.drawable.ic_launcher)
                .setTitle("创建会议")
                .setView(textEntryView)
                .setPositiveButton("提交",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        final EditText meetname = (EditText)textEntryView.findViewById(R.id.meet_name);
                        final EditText meetdetail = (EditText)textEntryView.findViewById(R.id.meet_detail);
                        SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
                        String   date   =   sDateFormat.format(new   java.util.Date());

                        String meetnamestr = meetname.getText().toString();
                        String meetdetailstr = meetdetail.getText().toString();

                        Toast.makeText(getApplicationContext(),date+" "+meetnamestr+" "+meetdetailstr ,Toast.LENGTH_LONG ).show();
                    }
                })
                .create();


        mDialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button createmeet;
        Intent intent = getIntent();
        String projectnamestr = intent.getStringExtra("project_name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetlist);
        createmeet =(Button)super.findViewById(R.id.createmeetbotton);
        createmeet.setOnClickListener(new createprojectOnClickListener());
        TextView projectname=(TextView)super.findViewById(R.id.textView);
        projectname.setText(projectnamestr);

        //生成适配器，数组-->>ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(
                this,
                getmeetData(),	//	数据来源
                R.layout.projectlist,	// ListItem的XML实现
                new String[] {"project_name","project_detail"},	// 动态数组与ListItem对应的子项
                new int[] {R.id.project_name,R.id.project_detail}	// ListItem的XML文件里面的两个TextView ID
        );
        setListAdapter(mSchedule);
    }

    //获取会议数据
    private List<HashMap<String, String>>  getmeetData() {
        ArrayList <HashMap<String,String>> list = new ArrayList <HashMap<String,String>>();

        int projectnum=5;

        for(int i=1; i<=projectnum; i++)
        {
            String meetname ="q ";
            String meetdetial ="f ";



            HashMap<String,String> map = new HashMap<String,String>();
            map.put("project_name", meetname+i);
            map.put("project_detail", meetdetial+i);
            list.add(map);
        }
        return list;
    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        System.out.println("id ++++ " + id);
        System.out.println("position +++++ " + position);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
