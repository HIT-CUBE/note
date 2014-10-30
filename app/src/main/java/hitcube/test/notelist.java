package hitcube.test;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class notelist extends ListActivity {

       private class createnoteOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),"创建便签" ,Toast.LENGTH_SHORT ).show();
            ShowDialogofnote();
        }
    }

    private void ShowDialogofnote()
    {
        LayoutInflater factory = LayoutInflater.from(notelist.this);
        final View textEntryView = factory.inflate(R.layout.notecreate, null);
        AlertDialog mDialog = new Builder(this)
                //.setIcon(R.drawable.ic_launcher)
                .setTitle("创建便签")
                .setView(textEntryView)
                .setPositiveButton("提交",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        final EditText notename = (EditText)textEntryView.findViewById(R.id.note_name);
                        final EditText notedetail = (EditText)textEntryView.findViewById(R.id.note_detail);
                        SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
                        String   date   =   sDateFormat.format(new   java.util.Date());

                        String notenamestr = notename.getText().toString();
                        String notedetailstr = notedetail.getText().toString();

                        Toast.makeText(getApplicationContext(),date+" "+notenamestr+" "+notedetailstr ,Toast.LENGTH_LONG ).show();
                    }
                })
                .create();


        mDialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button createnote;
        Intent intent = getIntent();
        String meetnamestr = intent.getStringExtra("meet_name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        createnote =(Button)super.findViewById(R.id.createnotebotton);
        createnote.setOnClickListener(new createnoteOnClickListener());
        TextView meetname=(TextView)super.findViewById(R.id.textView);
        meetname.setText(meetnamestr);

        //生成适配器，数组-->>ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(
                this,
                getnoteData(),	//	数据来源
                R.layout.projectlist,	// ListItem的XML实现
                new String[] {"project_name","project_detail"},	// 动态数组与ListItem对应的子项
                new int[] {R.id.project_name,R.id.project_detail}	// ListItem的XML文件里面的两个TextView ID
        );
        setListAdapter(mSchedule);
    }

    //获取会议数据
    private List<HashMap<String, String>>  getnoteData() {
        ArrayList <HashMap<String,String>> list = new ArrayList <HashMap<String,String>>();

        int notenum=5;

        for(int i=1; i<=notenum; i++)
        {
            String notename ="note ";
            String notedetial ="detail ";



            HashMap<String,String> map = new HashMap<String,String>();
            map.put("project_name", notename+i);
            map.put("project_detail", notedetial+i);
            list.add(map);
        }
        return list;
    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        System.out.println("id **** " + id);
        System.out.println("position **** " + position);


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
