//package com.xiao.project.activity;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Toast;
//
//import com.xiao.project.R;
//import com.xiao.project.adapter.MainAdapter;
//import com.yanzhenjie.recyclerview.OnItemClickListener;
//import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
//import com.yanzhenjie.recyclerview.SwipeMenu;
//import com.yanzhenjie.recyclerview.SwipeMenuBridge;
//import com.yanzhenjie.recyclerview.SwipeMenuCreator;
//import com.yanzhenjie.recyclerview.SwipeMenuItem;
//import com.yanzhenjie.recyclerview.SwipeRecyclerView;
//import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class SwipeRecyclerViewActivity extends AppCompatActivity implements OnItemClickListener {
//
//    @BindView(R.id.recycler_view)
//    SwipeRecyclerView recyclerView;
//
//    protected RecyclerView.LayoutManager mLayoutManager;
//    protected RecyclerView.ItemDecoration mItemDecoration;
//
//    protected MainAdapter mAdapter;
//    protected List<String> mDataList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_swipe_recycler_view);
//        ButterKnife.bind(this);
//        initView();
//    }
//
//    private void initView() {
//        mLayoutManager = createLayoutManager();
//        mItemDecoration = createItemDecoration();
//        mDataList = createDataList();
//        mAdapter = createAdapter();
//
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(mItemDecoration);
//        recyclerView.setOnItemClickListener(this);
//
//        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
//        recyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
//
//        recyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged(mDataList);
//    }
//
//    /**
//     * 菜单创建器，在Item要创建菜单的时候调用。
//     */
//    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
//        @Override
//        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
//            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
//
//            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
//            // 2. 指定具体的高，比如80;
//            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//
//            // 添加左侧的，如果不添加，则左侧不会出现菜单。
//            {
//                SwipeMenuItem addItem = new SwipeMenuItem(SwipeRecyclerViewActivity.this).setBackground(R.drawable.selector_green)
//                        .setImage(R.drawable.ic_action_add)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。
//
//                SwipeMenuItem closeItem = new SwipeMenuItem(SwipeRecyclerViewActivity.this).setBackground(R.drawable.selector_red)
//                        .setImage(R.drawable.ic_action_close)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
//            }
//
//            // 添加右侧的，如果不添加，则右侧不会出现菜单。
//            {
//                SwipeMenuItem editItem = new SwipeMenuItem(SwipeRecyclerViewActivity.this).setBackground(R.drawable.selector_red)
//                        .setImage(R.drawable.ic_action_delete)
//                        .setText("修改")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(editItem);// 添加菜单到右侧。
//
//                SwipeMenuItem tagItem = new SwipeMenuItem(SwipeRecyclerViewActivity.this).setBackground(R.drawable.selector_green)
//                        .setText("标旗")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(tagItem); // 添加菜单到右侧。
//
//
//                SwipeMenuItem deleteItem = new SwipeMenuItem(SwipeRecyclerViewActivity.this).setBackground(R.drawable.selector_red)
//                        .setText("删除")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
//
//
//            }
//        }
//    };
//
//    /**
//     * RecyclerView的Item的Menu点击监听。
//     */
//    private OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
//        @Override
//        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
//            menuBridge.closeMenu();
//
//            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
//            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//
//            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
//                Toast.makeText(SwipeRecyclerViewActivity.this, "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
//                        .show();
//            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(SwipeRecyclerViewActivity.this, "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    };
//
//
//
//    protected RecyclerView.LayoutManager createLayoutManager() {
//        return new LinearLayoutManager(this);
//    }
//
//    protected RecyclerView.ItemDecoration createItemDecoration() {
//        return new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
//    }
//
//    protected List<String> createDataList() {
//        List<String> dataList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            dataList.add("第" + i + "个Item");
//        }
//        return dataList;
//    }
//
//    protected MainAdapter createAdapter() {
//        return new MainAdapter(this);
//    }
//
//    @Override
//    public void onItemClick(View itemView, int position) {
//        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
//    }
//}
