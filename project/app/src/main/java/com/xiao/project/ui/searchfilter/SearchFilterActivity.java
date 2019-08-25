package com.xiao.project.ui.searchfilter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.xiao.project.MainActivity;
import com.xiao.project.R;
import com.xiao.project.adapter.RecyclerViewAdapter;
import com.xiao.project.utils.RxRecyclerViewDividerTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.SizeUtils.dp2px;

public class SearchFilterActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private Searchdapter searchdapter;
    Map<String, SearchBean> searchBeanMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        ButterKnife.bind(this);
        initData();
        initEvent();
    }

    private void initEvent() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initData() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RxRecyclerViewDividerTool(dp2px(5f)));
        searchdapter = new Searchdapter();
        rvList.setAdapter(searchdapter);

        initSearchData();

    }

    private void initSearchData() {
        searchBeanMap.put("1", new SearchBean("1", false, "请问请问器"));
        searchBeanMap.put("2", new SearchBean("2", false, "阿斯达四大"));
        searchBeanMap.put("3", new SearchBean("3", false, "中兄次之线程执行"));
        searchBeanMap.put("4", new SearchBean("4", false, "而突然他儿童"));
        searchBeanMap.put("5", new SearchBean("5", false, "水电费水电费"));
        searchBeanMap.put("6", new SearchBean("6", false, "许昌V型程序"));

        List<SearchBean> mList = new ArrayList<>();
        Iterator iter = searchBeanMap.entrySet().iterator(); // 获得map的Iterator
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            mList.add((SearchBean) entry.getValue());
        }

        searchdapter.notifyDataSetChanged(mList);
    }
}
