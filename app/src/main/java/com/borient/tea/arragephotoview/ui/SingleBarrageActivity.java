package com.borient.tea.arragephotoview.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borient.tea.arragephotoview.R;
import com.borient.tea.arragephotoview.data.BarrageData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orient.tea.barragephoto.adapter.AdapterListener;
import com.orient.tea.barragephoto.adapter.BarrageAdapter;
import com.orient.tea.barragephoto.ui.BarrageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SingleBarrageActivity extends AppCompatActivity {

    private String SEED[] = {"景色还不错啊", "小姐姐真好看！，", "又去哪里玩了？我也要去！", "门票多少啊？", "厉害啦！"};
    private Random random = new Random();
    private final int ICON_RESOURCES[] = {R.drawable.cat, R.drawable.corgi, R.drawable.lovelycat, R.drawable.boy, R.drawable.girl,R.drawable.samoyed};

    private BarrageView barrageView;
    private BarrageAdapter<BarrageData> mAdapter;
    //private Button mAdd;
    private List<BarrageData> barrageDataList = new ArrayList<>();

    public static void show(Context context){
        Intent intent = new Intent(context,SingleBarrageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_barrage);

        //mAdd = findViewById(R.id.btn_add);
        barrageView = findViewById(R.id.barrage);
        barrageView.setInterval(50);
        barrageView.setSpeed(200, 20);
        barrageView.setModel(BarrageView.MODEL_COLLISION_DETECTION);
        barrageView.setInterceptTouchEvent(true
        );
        barrageView.setAdapter(mAdapter = new BarrageAdapter<BarrageData>(null, this) {
            @Override
            public BarrageViewHolder<BarrageData> onCreateViewHolder(View root, int type) {
                return new SingleBarrageActivity.ViewHolder(root);
            }

            @Override
            public int getItemLayout(BarrageData barrageData) {
                return R.layout.barrage_item_normal;
            }
        });
        mAdapter.setAdapterListener(new AdapterListener<BarrageData>() {
            @Override
            public void onItemClick(BarrageAdapter.BarrageViewHolder<BarrageData> holder, BarrageData item) {
                Toast.makeText(SingleBarrageActivity.this,item.getContent()+"点击了一次",Toast.LENGTH_SHORT).show();
            }
        });

        /*mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        initData();
    }

    private void initData() {
        int strLength = SEED.length;
        for (int i = 0; i < 50; i++) {
            mAdapter.add(new BarrageData(SEED[i%strLength], 0,i));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        barrageView.destroy();
    }

    class ViewHolder extends BarrageAdapter.BarrageViewHolder<BarrageData> {

        private ImageView mHeadView;
        private TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mHeadView = itemView.findViewById(R.id.image);
            mContent = itemView.findViewById(R.id.content);
        }

        @Override
        protected void onBind(BarrageData data) {
            Glide.with(SingleBarrageActivity.this).load(ICON_RESOURCES[data.getPos()%ICON_RESOURCES.length])
                    .apply(RequestOptions.circleCropTransform())
                    .into(mHeadView);
            mContent.setText(data.getContent());
        }
    }
}
