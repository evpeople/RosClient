package net.xxhong.rosclient.ui;

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

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MoveActivity extends AppCompatActivity {

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
        setContentView(net.xxhong.rosclient.R.layout.activity_move);
    }


    /** Called when the user taps the Send button */
    public void move_forward(View view) {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                if (moving) {
                    client.send("{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":" + linearX + ",\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + angularZ + "}}}");
                    Log.d(TAG,"send cmd_vel msg:x:" + linearX + " z:" + angularZ);
                }
            }
        };
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