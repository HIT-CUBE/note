package hitcube.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2014/12/12.
 */
public class meetlist extends Activity{

        public SQLiteHelper helper;
        private Cursor cursor;
        private EditText editmeeting;
        private EditText editdetail;
        private ListView lvBook;
        private int meetingID=0,projectID=0;
        private String meetingName;

        private class addRecOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
               addRec();
            }
        }
        private class editRecOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                editRec();
            }
        }
        private class queryRecOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
               queryRec();
            }
        }
        private class deleteRecOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                deleteRec();
            }
        }
        private class enterRecOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                //进入下一界面
                if (meetingID==0)
                {
                    Toast.makeText(getApplicationContext(), "您没有选中任何会议，请选择一个会议", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent =new Intent() ;
                intent.setClass(meetlist.this,notelist.class);
                intent.putExtra("meeting_ID", meetingID+"");
                intent.putExtra("meeting_NAME",meetingName.substring(11));//截取时间
                startActivity(intent);
            }
        }


    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetlist);
        projectID=Integer.valueOf(intent.getStringExtra("project_ID")) ;

        TextView projectname=(TextView)super.findViewById(R.id.projectName);
        projectname.setText(intent.getStringExtra("project_NAME"));

        Button addbutton = null;
        Button deletebutton2 = null;
        Button querybutton3 = null;
        Button editbutton4 = null;
        Button enterbutton5 = null;
        addbutton =(Button)super.findViewById(R.id.button);
        addbutton.setOnClickListener(new addRecOnClickListener());
        deletebutton2 =(Button)super.findViewById(R.id.button2);
        deletebutton2.setOnClickListener(new deleteRecOnClickListener());
        querybutton3 =(Button)super.findViewById(R.id.button3);
        querybutton3.setOnClickListener(new queryRecOnClickListener());
        editbutton4 =(Button)super.findViewById(R.id.button4);
        editbutton4.setOnClickListener(new editRecOnClickListener());
        enterbutton5 =(Button)super.findViewById(R.id.button5);
        enterbutton5.setOnClickListener(new enterRecOnClickListener());

        lvBook = (ListView) this.findViewById(R.id.listview);
        editmeeting = (EditText) this.findViewById(R.id.editnote);
        editdetail =(EditText) this.findViewById(R.id.editdetail);

        helper = new SQLiteHelper(this);

        editdetail.setText("地点：\n"+"人数：\n"+"人员名单：\n"+"备注：");
        cursor=helper.select_linktoproject("Meeting",projectID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"meeting","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        // lvBook设置OnItemClickListener监听事件
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                cursor.moveToPosition(arg2);			// 将cursor移到所点击的值
                meetingID = cursor.getInt(0);
                meetingName=cursor.getString(1);				// 取得字段_id的值
                editmeeting.setText(meetingName);	// 取得字段Rec_text的值
                int linktofather=cursor.getInt(3);
                editdetail.setText(cursor.getString(2));

            }
        });
    }

    //添加记录
    private void addRec()
    {
        if (editmeeting.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "就算是新加的会议也必须有名字！Q_Q", Toast.LENGTH_LONG).show();
            return;
        }
        SimpleDateFormat sDateFormat   =   new  SimpleDateFormat("yyyy-MM-dd");
        String   date   =   sDateFormat.format(new Date());
        helper.insertmeeting("Meeting",date+"\n"+editmeeting.getText().toString(),editdetail.getText().toString(),projectID);
        //重新加载数据
        cursor.requery();
        cursor=helper.select_linktoproject("Meeting",projectID);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"meeting","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        editmeeting.setText("");
        editdetail.setText("地点：\n"+"人数：\n"+"人员名单：\n"+"备注：");
    }

    private void editRec()
    {
        if (meetingID==0)
        {
            Toast.makeText(getApplicationContext(), "您没有选中任何会议，请选择一个会议！", Toast.LENGTH_LONG).show();
            return;
        }
        if (editmeeting.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "一个会议必须有名字！Q_Q", Toast.LENGTH_LONG).show();
            return;
        }
        helper.updatemeeting("Meeting",meetingID, editmeeting.getText().toString(),editdetail.getText().toString());
        //重新加载数据

        cursor=helper.select_linktoproject("Meeting",projectID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"meeting","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        editmeeting.setText("");
        editdetail.setText("地点：\n"+"人数：\n"+"人员名单：\n"+"备注：");
    }

    private void queryRec()
    {
        String et=editmeeting.getText().toString();
        String args[]=new String[]{"%"+et+"%",projectID+""};
        cursor=helper.queryMeeting("Meeting", args);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"meeting","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);
    }

    //删除记录
    private void deleteRec()
    {
        if (meetingID==0)
        {
            Toast.makeText(getApplicationContext(), "您没有选中任何会议，请选择一个会议", Toast.LENGTH_LONG).show();
            return;
        }
        helper.delete("Meeting",meetingID);

        cursor=helper.select_linktoproject("Meeting",projectID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"meeting","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        editmeeting.setText("");
        editdetail.setText("地点：\n"+"人数：\n"+"人员名单：\n"+"备注：");
    }

}
