package com.ubpost.slidinguppaneldemo2;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
public class Btn02Activity extends AppCompatActivity {

    private static final String TAG = "mytag";

    private Btn02Activity activity;
    private SlidingUpPanelLayout slidingUpPanelLayout;//整个滑动面板
    private RelativeLayout rl_Btn02Activity_mainLayout;//主界面
    private Button btn_Btn02Activity_expanded;//展开按钮
    private Button btn_Btn02Activity_collapsed;//收缩按钮
    private Button btn_Btn02Activity_hidden;//隐藏按钮
    private Button btn_Btn02Activity_anchoredOpen;//打开锚点状态
    private Button btn_Btn02Activity_anchoredClose;//关闭锚点状态
    private Button btn_Btn02Activity_changeFadeColor;//改变暗淡颜色
    private Button btn_Btn02Activity_bottomGravity;//从底部滑出
    private Button btn_Btn02Activity_topGravity;//从顶部滑出
    private LinearLayout ll_Btn02Activity_slidingLayout;//滑动面板界面
    private TextView tv_Btn02Activity_head;//滑动面板头布局
    private ScrollView sv_Btn02Activity_content;//滑动面板的滑动View
    private TextView tv_Btn02Activity_content1;//滑动面板内容
    private TextView tv_Btn02Activity_content2;//滑动面板内容
    private TextView tv_Btn02Activity_content3;//滑动面板内容
    private TextView tv_Btn02Activity_content4;//滑动面板内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn02);
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
        btn_Btn02Activity_expanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }
        });
        //收缩状态
        btn_Btn02Activity_collapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });
        //隐藏状态
        btn_Btn02Activity_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            }
        });
        //打开锚点状态
        btn_Btn02Activity_anchoredOpen.setOnClickListener(new View.OnClickListener() {
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
                    slidingUpPanelLayout.setAnchorPoint(0.6f);
                    //进入锚点状态
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

                    //设置滑动View 的高度；否则在锚点状态，超出屏幕的范围不会显示
                    ViewGroup.LayoutParams layoutParams = sv_Btn02Activity_content.getLayoutParams();
                    //高度设置为：锚点状态的高度 - 头布局的高度
                    layoutParams.height =
                            (int) (ScreenUtil.getScreenHeight(activity) * slidingUpPanelLayout.getAnchorPoint())
                                    - tv_Btn02Activity_head.getHeight();
                    sv_Btn02Activity_content.setLayoutParams(layoutParams);
                    Log.i(TAG, "btn_Btn02Activity_anchoredOpen:锚点状态" + layoutParams.height);
                } else {
                    Toast.makeText(activity, "已经是锚点状态", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //关闭锚点状态
        btn_Btn02Activity_anchoredClose.setOnClickListener(new View.OnClickListener() {
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

                    //还原滑动View 的高度；否则在完全展示状态，滑动View 的高度不会占满屏幕
                    ViewGroup.LayoutParams layoutParams = sv_Btn02Activity_content.getLayoutParams();
                    //高度设置为：屏幕的高度 - 头布局的高度 - Toolbar的高度
                    layoutParams.height = ScreenUtil.getScreenHeight(activity)
                            - tv_Btn02Activity_head.getHeight() - ScreenUtil.dip2px(activity, 56);
                    sv_Btn02Activity_content.setLayoutParams(layoutParams);
                    Log.i(TAG, "btn_Btn02Activity_anchoredClose:展开状态" + layoutParams.height);
                } else {
                    Toast.makeText(activity, "已经不是锚点状态", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //修改暗淡内容的颜色
        btn_Btn02Activity_changeFadeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout.getCoveredFadeColor() ==
                        ContextCompat.getColor(activity, android.R.color.holo_orange_dark)) {
                    slidingUpPanelLayout.setCoveredFadeColor(
                            ContextCompat.getColor(activity, android.R.color.holo_blue_light));
                } else {
                    slidingUpPanelLayout.setCoveredFadeColor(
                            ContextCompat.getColor(activity, android.R.color.holo_orange_dark));
                }
            }
        });
        //从底部滑出
        btn_Btn02Activity_bottomGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setGravity(Gravity.BOTTOM);
            }
        });
        //从顶部滑出
        btn_Btn02Activity_topGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setGravity(Gravity.TOP);
            }
        });
        //滑动布局的头布局
        tv_Btn02Activity_head.setOnClickListener(new View.OnClickListener() {
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

        //设置从顶部滑出还是从底部滑出
        //slidingUpPanelLayout.setGravity(Gravity.BOTTOM);
        //面板收缩状态时候的预留的高度
        slidingUpPanelLayout.setPanelHeight(ScreenUtil.dip2px(activity, 60));
        //面板滑动时，主界面跟随滑动的位移距离
        slidingUpPanelLayout.setParallaxOffset(ScreenUtil.dip2px(activity, 100));
        //滑动面板与主界面间的海拔阴影高度
        slidingUpPanelLayout.setShadowHeight(ScreenUtil.dip2px(activity, 10));
        //滑动面板中，可执行滑动的View
        slidingUpPanelLayout.setScrollableView(sv_Btn02Activity_content);
        //决定哪个控件可以响应面板滑动事件
        slidingUpPanelLayout.setDragView(tv_Btn02Activity_head);
//        slidingUpPanelLayout.setDragView(ll_Btn02Activity_slidingLayout);
        //滑动面板是否覆盖主界面；
        //flase，则收缩状态时主界面会上移部分ParallaxOffset距离以不被覆盖；true，则收缩状态高度直接覆盖主界面
        slidingUpPanelLayout.setOverlayed(false);
        //设置主要内容是否是顶部面板的一部分；暂未发现其具体作用
        //slidingUpPanelLayout.setClipPanel(false);

        //滑动面板监听器
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //滑动时
                //Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel,
                                            SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                //状态变化时
                Log.i(TAG, "onPanelStateChanged " + newState);
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED
                        && slidingUpPanelLayout.getAnchorPoint() != 1.0f) {
                    //完全展开状态，但锚点值不为1时
                    //还原滑动View 的高度；否则在完全展示状态，滑动View 的高度不会占满屏幕
                    ViewGroup.LayoutParams layoutParams = sv_Btn02Activity_content.getLayoutParams();
                    //高度设置为：屏幕的高度 - 头布局的高度 - Toolbar的高度
                    layoutParams.height = ScreenUtil.getScreenHeight(activity)
                            - tv_Btn02Activity_head.getHeight() - ScreenUtil.dip2px(activity, 56);
                    sv_Btn02Activity_content.setLayoutParams(layoutParams);
                    Log.i(TAG, "onPanelStateChanged:展开状态" + layoutParams.height);
                } else if (newState == SlidingUpPanelLayout.PanelState.ANCHORED) {
                    //锚点状态时
                    //设置滑动View 的高度；否则在锚点状态，超出屏幕的范围不会显示
                    ViewGroup.LayoutParams layoutParams = sv_Btn02Activity_content.getLayoutParams();
                    //高度设置为：锚点状态的高度 - 头布局的高度
                    layoutParams.height =
                            (int) (ScreenUtil.getScreenHeight(activity) * slidingUpPanelLayout.getAnchorPoint())
                                    - tv_Btn02Activity_head.getHeight();
                    sv_Btn02Activity_content.setLayoutParams(layoutParams);
                    Log.i(TAG, "onPanelStateChanged:锚点状态" + layoutParams.height);
                }
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
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.supLayout_Btn02Activity_whole);
        rl_Btn02Activity_mainLayout = (RelativeLayout) findViewById(R.id.rl_Btn02Activity_mainLayout);
        btn_Btn02Activity_expanded = (Button) findViewById(R.id.btn_Btn02Activity_expanded);
        btn_Btn02Activity_collapsed = (Button) findViewById(R.id.btn_Btn02Activity_collapsed);
        btn_Btn02Activity_hidden = (Button) findViewById(R.id.btn_Btn02Activity_hidden);
        btn_Btn02Activity_anchoredOpen = (Button) findViewById(R.id.btn_Btn02Activity_anchoredOpen);
        btn_Btn02Activity_anchoredClose = (Button) findViewById(R.id.btn_Btn02Activity_anchoredClose);
        btn_Btn02Activity_changeFadeColor = (Button) findViewById(R.id.btn_Btn02Activity_changeFadeColor);
        btn_Btn02Activity_bottomGravity = (Button) findViewById(R.id.btn_Btn02Activity_bottomGravity);
        btn_Btn02Activity_topGravity = (Button) findViewById(R.id.btn_Btn02Activity_topGravity);
        ll_Btn02Activity_slidingLayout = (LinearLayout) findViewById(R.id.ll_Btn02Activity_slidingLayout);
        tv_Btn02Activity_head = (TextView) findViewById(R.id.tv_Btn02Activity_head);
        sv_Btn02Activity_content = (ScrollView) findViewById(R.id.sv_Btn02Activity_content);
        tv_Btn02Activity_content1 = (TextView) findViewById(R.id.tv_Btn02Activity_content1);
        tv_Btn02Activity_content2 = (TextView) findViewById(R.id.tv_Btn02Activity_content2);
        tv_Btn02Activity_content3 = (TextView) findViewById(R.id.tv_Btn02Activity_content3);
        tv_Btn02Activity_content4 = (TextView) findViewById(R.id.tv_Btn02Activity_content4);
    }
}
