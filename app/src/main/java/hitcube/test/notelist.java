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

/**
 * Created by Administrator on 2014/12/12.
 */
public class notelist extends Activity{

    public SQLiteHelper helper;
    private Cursor cursor;
    private EditText editnote;
    private EditText editdetail;
    private ListView lvBook;
    private int noteID=0,meetingID=0;
    private String noteName;

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
            //queryRec();
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
            if (meetingID==0)
            {
                Toast.makeText(getApplicationContext(), "您没有选中任何笔记，请选择一个笔记", Toast.LENGTH_LONG).show();
                return;
            }
            //进入下一界面
            // System.out.println("id ---------------- " + noteID);
            //Intent intent =new Intent() ;
            //intent.setClass(meetlist.this,meetlist.class);
            //intent.putExtra("meet_ID","meet"+" "+noteID);
            //startActivity(intent);
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        meetingID=Integer.valueOf(intent.getStringExtra("meeting_ID")) ;

        TextView meetingname=(TextView)super.findViewById(R.id.meetingName);
        meetingname.setText(intent.getStringExtra("meeting_NAME"));

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
        editnote = (EditText) this.findViewById(R.id.editnote);
        editdetail =(EditText) this.findViewById(R.id.editdetail);

        helper = new SQLiteHelper(this);
        editnote.setText("属性：\n"+"重要性：");
        editdetail.setText("备注：");
        cursor=helper.select_linktomeeting("Note",meetingID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"note","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        // lvBook设置OnItemClickListener监听事件
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                cursor.moveToPosition(arg2);			// 将cursor移到所点击的值
                noteID = cursor.getInt(0);
                noteName=cursor.getString(1);				// 取得字段_id的值
                editnote.setText(noteName);	// 取得字段Rec_text的值
                int linktofather=cursor.getInt(3);
                editdetail.setText(cursor.getString(2));

            }
        });
    }

    //添加记录
    private void addRec()
    {
        SimpleDateFormat sDateFormat   =   new  SimpleDateFormat("hh:mm:ss");
        String   time   =   sDateFormat.format(new java.util.Date());
        helper.insertnote("Note",time+"\n"+editnote.getText().toString(),editdetail.getText().toString(),meetingID);

        //重新加载数据
        cursor.requery();
        cursor=helper.select_linktomeeting("Note",meetingID);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"note","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        editnote.setText("属性：\n"+"重要性：");
        editdetail.setText("备注：");
    }

    private void editRec()
    {
        if (noteID==0)
        {
            Toast.makeText(getApplicationContext(), "您没有选中任何笔记，请选择一个笔记", Toast.LENGTH_LONG).show();
            return;
        }
        if (editnote.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "你把时间、标签都删了，不用这么绝吧！>_<", Toast.LENGTH_LONG).show();
            return;
        }

        helper.updatenote("Note",noteID, editnote.getText().toString(),editdetail.getText().toString());
        //重新加载数据

        cursor=helper.select_linktomeeting("Note",meetingID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"note","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        editnote.setText("属性：\n"+"重要性：");
        editdetail.setText("备注：");
    }

    private void queryRec()
    {
        String et=editnote.getText().toString();
        String args[]=new String[]{"%"+et+"%"};
        //cursor=helper.queryNote("Note",args);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"note","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);
    }

    //删除记录
    private void deleteRec()
    {
        if (noteID==0)
        {
            Toast.makeText(getApplicationContext(), "您没有选中任何笔记，请选择一个笔记", Toast.LENGTH_LONG).show();
            return;
        }
        helper.delete("Note",noteID);

        cursor=helper.select_linktomeeting("Note",meetingID);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list,
                cursor,
                new String[] {"note","detail"},
                new int[] { R.id.textelement,R.id.textdetail}
        );
        lvBook.setAdapter(adapter);

        editnote.setText("属性：\n"+"重要性：");
        editdetail.setText("备注：");
    }

}
