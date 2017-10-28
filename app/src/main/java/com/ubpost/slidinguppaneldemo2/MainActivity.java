package com.ubpost.slidinguppaneldemo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
public class MainActivity extends AppCompatActivity {

    private MainActivity activity;
    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        btn01 = (Button) findViewById(R.id.btn_main_btn01);
        btn02 = (Button) findViewById(R.id.btn_main_btn02);
        btn03 = (Button) findViewById(R.id.btn_main_btn03);
        btn04 = (Button) findViewById(R.id.btn_main_btn04);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, Btn01Activity.class);
                startActivity(intent);
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, Btn02Activity.class);
                startActivity(intent);
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, Btn03Activity.class);
                startActivity(intent);
            }
        });
        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, Btn04Activity.class);
                startActivity(intent);
            }
        });
    }
}
