package net.xxhong.rosclient.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilk.ros.rosapi.message.TypeDef;
import com.jilk.ros.rosbridge.ROSBridgeClient;
import com.unnamed.b.atv.model.TreeNode;

import net.xxhong.rosclient.R;
import net.xxhong.rosclient.RCApplication;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MoveActivity extends Activity {

    private static final String TAG = "MoveActivity";
    ROSBridgeClient client;
    @Bind(R.id.tv_type_name)
    TextView tvTitle;
    @Bind(R.id.tv_log)
    TextView tvLog;

    @Bind(R.id.btn_topic_sub)
    Button btnSubTopic;
    @Bind(R.id.btn_call)
    Button btnCall;
    @Bind(R.id.ll_param_layout)
    LinearLayout paramContainer;

    String detailType;
    String detailName;
    TypeDef[] typeDef;

    private boolean isSubscribe = false;

    private Timer timer;
    public  boolean moving = true;
    public  float linearX = 0;
    public  float angularZ = 0;

    TreeNode root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        client = ((RCApplication)getApplication()).getRosClient();

        detailName= getIntent().getStringExtra("name");
    }


    /** Called when the user taps the Send button */
    public void move_forward(View view) {
        String data = "\"linear\":{\"x\":10,\"y\":10,\"z\":10},\"angular\":{\"x\":12,\"y\":32,\"z\":54}";
        String msg = "";

        msg = "{\"op\":\"publish\",\"topic\":\"" + detailName + "\",\"msg\":{"+data+"}}";
        //client.send("{\"op\":\"publish\",\"topic\":\"" + detailName + "\",\"msg\":{"+data+"}}");
        client.send(msg);
        Log.d(TAG, "onClick: "+msg);


    }
    public void move_back(View view) {
        // Do something in response to button
    }
    public void move_left(View view) {
        // Do something in response to button
    }
    public void move_right(View view) {
        // Do something in response to button
    }
}