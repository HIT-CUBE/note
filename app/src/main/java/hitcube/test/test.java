package hitcube.test;

import android.os.Bundle;
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






public class test extends ListActivity {
    private ListView lvNews=null;
    private Button createproject = null;
    public static final int PROJECTLMIT = 5;


    private class createprojectOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),"创建项目" ,Toast.LENGTH_SHORT ).show();
            ShowDialogofproject();
        }
    }

    //按照目录创建一个文件
    public void CreateFile(String filepath,String filename) throws IOException {
            File file = new File(filepath);
            if (!file.exists()) {
                try {
                    //按照指定的路径创建文件夹
                    file.mkdirs();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            File dir = new File(filename);
            if (!dir.exists()) {
                try {
                    //在指定的文件夹中创建文件
                    dir.createNewFile();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    //向已创建的文件中写入数据
    public void print(String str,String filename) {
            FileWriter fw = null;
            BufferedWriter bw = null;
            String datetime = "";
            try {
                SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " "
                        + "hh:mm:ss");
                datetime = tempDate.format(new java.util.Date()).toString();
                fw = new FileWriter(filename, true);//
                // 创建FileWriter对象，用来写入字符流
                bw = new BufferedWriter(fw); // 将缓冲对文件的输出
                String myreadline = datetime + "[]" + str;

                bw.write(myreadline + "\n"); // 写入文件
                bw.newLine();
                bw.flush(); // 刷新该流的缓冲
                bw.close();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                try {
                    bw.close();
                    fw.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                }
            }
        }

    //从以创建文件中读取内容
    /*public String read(String filename){

        StringBuffer info=new StringBuffer();
        File file=new File(filename);
        Scanner scan=null;
        try {

            scan=new Scanner(new FileInputStream(file));
              while(scan.hasNext())
              {
                  info.append(scan.next()).append("\n");
              }
            } catch (FileNotFoundException e) {
                        e.printStackTrace();
            }
        finally{
                        scan.close();
        }


        return info.toString();
    }
*/
    private void ShowDialogofproject()
    {
        LayoutInflater factory = LayoutInflater.from(test.this);
        final View textEntryView = factory.inflate(R.layout.projectcreate, null);
        AlertDialog mDialog = new AlertDialog.Builder(this)
                       //.setIcon(R.drawable.ic_launcher)
                        .setTitle("创建项目")
                        .setView(textEntryView)
                        .setPositiveButton("提交",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                final EditText projectname = (EditText)textEntryView.findViewById(R.id.project_name);
                                final EditText projectdetail = (EditText)textEntryView.findViewById(R.id.project_detail);
                                SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
                                String   date   =   sDateFormat.format(new   java.util.Date());

                                String projectnamestr = projectname.getText().toString();
                                String projectdetailstr = projectdetail.getText().toString();

                                Toast.makeText(getApplicationContext(),date+" "+projectnamestr+" "+projectdetailstr ,Toast.LENGTH_LONG ).show();
                            }
                        })
        .create();


        mDialog.show();


        }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        createproject =(Button)super.findViewById(R.id.createprojectbotton);
        createproject.setOnClickListener(new createprojectOnClickListener());


            //生成适配器，数组-->>ListItem
            SimpleAdapter mSchedule = new SimpleAdapter(
                    this,
                    getData(),	//	数据来源
                    R.layout.projectlist,	// ListItem的XML实现
                    new String[] {"project_name","project_detail"},	// 动态数组与ListItem对应的子项
                    new int[] {R.id.project_name,R.id.project_detail}	// ListItem的XML文件里面的两个TextView ID
            );
            setListAdapter(mSchedule);
        }

         protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        System.out.println("id ---------------- " + id);
        System.out.println("position ---------------- " + position);
    }

    private List<HashMap<String, String>>  getData() {
        ArrayList <HashMap<String,String>> list = new ArrayList <HashMap<String,String>>();

        int projectnum;

        for(int i=0; i<50; ++i)
        {
            String projectname ="p ";
            String projectdetial ="d ";



            HashMap<String,String> map = new HashMap<String,String>();
            map.put("project_name", projectname+i);
            map.put("project_detail", projectdetial+i);
            list.add(map);
        }
        return list;
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
