package hitcube.test;

import android.app.Activity;
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
import   java.text.SimpleDateFormat;





public class test extends Activity {

    private Button createproject = null;



    private class createprojectOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),"创建项目" ,Toast.LENGTH_SHORT ).show();
            ShowDialog();
        }
    }



    private void ShowDialog()
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
