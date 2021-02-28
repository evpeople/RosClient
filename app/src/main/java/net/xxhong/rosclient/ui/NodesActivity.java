package net.xxhong.rosclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jilk.ros.rosbridge.ROSBridgeClient;
import net.xxhong.rosclient.R;
import net.xxhong.rosclient.RCApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class NodesActivity extends Activity {

    ROSBridgeClient client;
    //@Bind(R.id.node_list)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);

        ButterKnife.bind(this);
        final String[] nodeTypes = new String[] { "Node List", "Service List", "Topic List" };
        final String[][] nodeDataArray = new String[][] {{  },{ },{ }};
        client = ((RCApplication)getApplication()).getRosClient();

        try {
            //Get list data
            nodeDataArray[0] = client.getNodes();
            nodeDataArray[1] = client.getServices();
            nodeDataArray[2] = client.getTopics();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExpandableListAdapter nodeListView= new BaseExpandableListAdapter() {


            @Override
            public int getGroupCount() {
                return nodeTypes.length;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return nodeDataArray[groupPosition].length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return nodeTypes[groupPosition];
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return nodeDataArray[groupPosition][childPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent) {
                LinearLayout ll = new LinearLayout(NodesActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                TextView textView = getTextView();
                textView.setTextColor(Color.BLACK);
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);
                ll.setPadding(100, 10, 10, 10);
                return ll;
            }


            @Override
            public View getChildView(final int groupPosition,final int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
                LinearLayout ll = new LinearLayout(NodesActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition).toString());
                textView.setTextSize(18);
                ll.addView(textView);
                ll.setPadding(100, 0, 0, 0);

                //Set click listener for leaf node
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(NodesActivity.this,DetailActivity.class);
                        if(groupPosition == 1) {            //Service详细
                            intent.putExtra("type","Service");
                            intent.putExtra("name", getChild(groupPosition, childPosition).toString());
                            startActivity(intent);
                        }
                        else if(getChild(groupPosition, childPosition).toString().indexOf("/cmd_vel")!=-1)
                        {
                            Intent intent1=new Intent(NodesActivity.this,MoveActivity.class);
                            intent1.putExtra("name", getChild(groupPosition, childPosition).toString());

                            startActivity(intent1);
                        }
                        else if (groupPosition == 2) {    //Topic详细

                            intent.putExtra("type","Topic");
                            intent.putExtra("name", getChild(groupPosition, childPosition).toString());
                            startActivity(intent);
                        }
                    }
                });
                return ll;
            }

            private TextView getTextView(){
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getResources().getDimensionPixelSize(R.dimen.list_height));
                TextView textView = new TextView(NodesActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(36, 0, 0, 0);
                textView.setTextSize(25);
                textView.setTextColor(Color.BLACK);
                return textView;
            }


            @Override
            public boolean isChildSelectable(int groupPosition,
                                             int childPosition) {
                return true;
            }

            //表示孩子是否和组ID是跨基础数据的更改稳定
            public boolean hasStableIds() {
                return true;
            }

        };
        ExpandableListView expandListView = (ExpandableListView) this.findViewById(R.id.node_list);
        expandListView.setAdapter(nodeListView);

    }
}

//    class NodeListAdapter extends BaseExpandableListAdapter {
//        TextView getTextView() {
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
//                    getResources().getDimensionPixelSize(R.dimen.list_height));
//            TextView textView = new TextView(NodesActivity.this);
//            textView.setLayoutParams(lp);
//            textView.setGravity(Gravity.CENTER_VERTICAL);
//            textView.setPadding(36, 0, 0, 0);
//            textView.setTextSize(25);
//            textView.setTextColor(Color.BLACK);
//            return textView;
//        }
//
//
//        @Override
//        public int getGroupCount() {
//            return nodeTypes.length;
//        }
//
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return nodeDataArray[groupPosition].length;
//        }
//
//        @Override
//        public Object getGroup(int groupPosition) {
//            return nodeTypes[groupPosition];
//        }
//
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            return nodeDataArray[groupPosition][childPosition];
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//        @Override //Goup view
//        public View getGroupView(int groupPosition, boolean isExpanded,
//                                 View convertView, ViewGroup parent) {
//            LinearLayout ll = new LinearLayout(NodesActivity.this);
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//            TextView textView = getTextView();
//            textView.setTextColor(Color.BLACK);
//            textView.setText(getGroup(groupPosition).toString());
//            ll.addView(textView);
//            ll.setPadding(100, 10, 10, 10);
//            return ll;
//        }
//
//        @Override //Leaf node view
//        public View getChildView(final int groupPosition,final int childPosition,
//                                 boolean isLastChild, View convertView, ViewGroup parent) {
//            LinearLayout ll = new LinearLayout(NodesActivity.this);
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//            TextView textView = getTextView();
//            textView.setText(getChild(groupPosition, childPosition).toString());
//            textView.setTextSize(18);
//            ll.addView(textView);
//            ll.setPadding(100, 0, 0, 0);
//
//            //Set click listener for leaf node
//            ll.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(NodesActivity.this,DetailActivity.class);
//                    if(groupPosition == 1) {            //Service详细
//                        intent.putExtra("type","Service");
//                        intent.putExtra("name", getChild(groupPosition, childPosition).toString());
//                        startActivity(intent);
//                    } else if (groupPosition == 2) {    //Topic详细
//                        intent.putExtra("type","Topic");
//                        intent.putExtra("name", getChild(groupPosition, childPosition).toString());
//                        startActivity(intent);
//                    }
//                }
//            });
//            return ll;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return true;
//        }
//
//    }
//}
