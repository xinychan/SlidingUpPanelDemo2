package com.ubpost.slidinguppaneldemo2;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/*
 * 使用注意事项：
 * （1） 将com.sothree.slidinguppanel.SlidingUpPanelLayout 作为布局文件的根元素
 * （2） 必须设置其属性android:gravity=”bottom”或android:gravity=”top”
 * （3） 必须保证该布局必须有两个子元素，第一个子元素是主界面，第二个子元素是sliding panel
 * （4） 第一个子元素宽度必须设置为match_parent，高度设置为match_parent或wrap_content，
 * 或者设置android:maxWidth。如果想让主界面和sliding panel以某种比例分配屏幕，
 * 可以将主界面高度设置为match_parent，并采用layout_weight为上滑面板分配比例。
 * （5） 默认情况下，整个sliding panel的区域都会响应上滑面板事件，
 * 可以通过setDragView将上滑事件限制在指定的view上，或者使用umanoDragView属性。
 */

/*
 * SlidingUpPanelLayout 的五种状态
 * 1-SlidingUpPanelLayout.PanelState.EXPANDED 完全展开状态
 * 2-SlidingUpPanelLayout.PanelState.COLLAPSED 收缩状态
 * 3-SlidingUpPanelLayout.PanelState.ANCHORED 固定状态/锚点状态
 * 4-SlidingUpPanelLayout.PanelState.HIDDEN 隐藏状态
 * 5-SlidingUpPanelLayout.PanelState.DRAGGING 拖拽状态/滑动状态
 */

/**
 * AndroidSlidingUpPanel 的使用
 * 1-引入依赖
 * 2-结合使用注意事项使用
 */
public class Btn03Activity extends AppCompatActivity {

    private static final String TAG = "mytag";

    private Btn03Activity activity;
    private SlidingUpPanelLayout slidingUpPanelLayout;//整个滑动面板
    private RelativeLayout rl_Btn03Activity_mainLayout;//主界面
    private Button btn_Btn03Activity_expanded;//展开按钮
    private Button btn_Btn03Activity_collapsed;//收缩按钮
    private Button btn_Btn03Activity_hidden;//隐藏按钮
    private Button btn_Btn03Activity_anchoredOpen;//打开锚点状态
    private Button btn_Btn03Activity_anchoredClose;//关闭锚点状态
    private Button btn_Btn03Activity_changeFadeColor;//改变暗淡颜色
    private Button btn_Btn03Activity_bottomGravity;//从底部滑出
    private Button btn_Btn03Activity_topGravity;//从顶部滑出
    private LinearLayout ll_Btn03Activity_slidingLayout;//滑动面板界面
    private TextView tv_Btn03Activity_head;//滑动面板头布局
    private TextView tv_Btn03Activity_content1;//滑动面板内容
    private TextView tv_Btn03Activity_content2;//滑动面板内容
    private TextView tv_Btn03Activity_content3;//滑动面板内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn03);
        activity = this;
        initView();
        setSlidingUpPanel();
        setOnClick();
    }

    /**
     * 点击事件
     */
    private void setOnClick() {
        //展开状态
        btn_Btn03Activity_expanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }
        });
        //收缩状态
        btn_Btn03Activity_collapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });
        //隐藏状态
        btn_Btn03Activity_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            }
        });
        //打开锚点状态
        btn_Btn03Activity_anchoredOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //若是隐藏状态，则先要激活，比如进入收缩状态，才能进入锚点状态；无法从隐藏状态直接进入锚点状态
                if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                //若是隐藏状态，下面的代码也会执行，但不会执行状态变化动画，故上面做判断执行状态变化的动画
                //初始化的锚点值默认为1
                if (slidingUpPanelLayout.getAnchorPoint() == 1.0f) {
                    //修改锚点值，锚点值显示为占屏幕的百分比
                    slidingUpPanelLayout.setAnchorPoint(0.7f);
                    //进入锚点状态
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                } else {
                    Toast.makeText(activity, "已经是锚点状态", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //关闭锚点状态
        btn_Btn03Activity_anchoredClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //若是隐藏状态，则先要激活，比如进入收缩状态，才能进入锚点状态；无法从隐藏状态直接进入锚点状态
                if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                //若是隐藏状态，下面的代码也会执行，但不会执行状态变化动画，故上面做判断执行状态变化的动画
                //初始化的锚点值默认为1
                if (slidingUpPanelLayout.getAnchorPoint() != 1.0f) {
                    //修改锚点值，锚点值显示为占屏幕的百分比
                    slidingUpPanelLayout.setAnchorPoint(1f);
                    //进入收缩状态
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    Toast.makeText(activity, "已经不是锚点状态", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //修改暗淡内容的颜色
        btn_Btn03Activity_changeFadeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getCoveredFadeColor() ==
                        ContextCompat.getColor(activity, R.color.colorAccent)) {
                    slidingUpPanelLayout.setCoveredFadeColor(
                            ContextCompat.getColor(activity, R.color.colorPrimary));
                } else {
                    slidingUpPanelLayout.setCoveredFadeColor(
                            ContextCompat.getColor(activity, R.color.colorAccent));
                }
            }
        });
        //从底部滑出
        btn_Btn03Activity_bottomGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setGravity(Gravity.BOTTOM);
            }
        });
        //从顶部滑出
        btn_Btn03Activity_topGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setGravity(Gravity.TOP);
            }
        });
        //滑动布局的头布局
        tv_Btn03Activity_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }
        });
    }

    /**
     * 设置滑动面板相关属性
     */
    private void setSlidingUpPanel() {

        //滑动面板监听器
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //滑动时
                //Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                //状态变化时
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        //暗淡内容点击监听器
        slidingUpPanelLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当处于锚点状态，会有部分暗淡内容，点击暗淡内容执行如下操作
                Toast.makeText(activity, "点击了暗淡内容", Toast.LENGTH_SHORT).show();
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    private void initView() {
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.supLayout_Btn03Activity_whole);
        rl_Btn03Activity_mainLayout = (RelativeLayout) findViewById(R.id.rl_Btn03Activity_mainLayout);
        btn_Btn03Activity_expanded = (Button) findViewById(R.id.btn_Btn03Activity_expanded);
        btn_Btn03Activity_collapsed = (Button) findViewById(R.id.btn_Btn03Activity_collapsed);
        btn_Btn03Activity_hidden = (Button) findViewById(R.id.btn_Btn03Activity_hidden);
        btn_Btn03Activity_anchoredOpen = (Button) findViewById(R.id.btn_Btn03Activity_anchoredOpen);
        btn_Btn03Activity_anchoredClose = (Button) findViewById(R.id.btn_Btn03Activity_anchoredClose);
        btn_Btn03Activity_changeFadeColor = (Button) findViewById(R.id.btn_Btn03Activity_changeFadeColor);
        btn_Btn03Activity_bottomGravity = (Button) findViewById(R.id.btn_Btn03Activity_bottomGravity);
        btn_Btn03Activity_topGravity = (Button) findViewById(R.id.btn_Btn03Activity_topGravity);
        ll_Btn03Activity_slidingLayout = (LinearLayout) findViewById(R.id.ll_Btn03Activity_slidingLayout);
        tv_Btn03Activity_head = (TextView) findViewById(R.id.tv_Btn03Activity_head);
        tv_Btn03Activity_content1 = (TextView) findViewById(R.id.tv_Btn03Activity_content1);
        tv_Btn03Activity_content2 = (TextView) findViewById(R.id.tv_Btn03Activity_content2);
        tv_Btn03Activity_content3 = (TextView) findViewById(R.id.tv_Btn03Activity_content3);
    }
}
