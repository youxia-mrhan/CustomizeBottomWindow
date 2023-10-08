package com.example.myapplication.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.adapter.SortChildPageAdapter;
import com.example.myapplication.bean.SortDownBean;
import com.example.myapplication.databinding.ActivitySortListBinding;
import com.example.myapplication.ext.ViewPager2HelperEx;
import com.example.myapplication.ui.fragment.sort.AllShoppingFragment;
import com.example.myapplication.ui.fragment.sort.AllSortFragment;
import com.example.myapplication.ui.fragment.sort.RecommendSortFragment;
import com.example.myapplication.ui.widget.SortListDownBarHo;
import com.example.myapplication.util.ReadAssertJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortListActivity extends AppCompatActivity {

    private List<String> tabs = new ArrayList<String>(Arrays.asList("全部商区", "全部分类", "推荐排序"));

    private SortDownBean sortDownBean;

    private ActivitySortListBinding binding;

    private InputMethodManager imm;

    private SortListDownBarHo sortListDownBarHo;

    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySortListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        initData();
        initView();
    }

    private void initView() {
        initSearchBar();
        initSelectBar();
    }

    // 选择Bar
    private void initSelectBar() {
        sortListDownBarHo = (SortListDownBarHo) binding.sortListTab;
        sortListDownBarHo.setTabs(tabs);
        sortListDownBarHo.setSortBarItemOnClick(new SortListDownBarHo.SortBarItemOnClick() {
            @Override
            public void barItemOnClick(int index) {
                binding.sortListView.setCurrentItem(index);
            }

            @Override
            public void selectedItemCallback(List<Object> selectedItems, int index) {
                switch (index) {
                    case 0:
                        AllShoppingFragment allShoppingFragment = (AllShoppingFragment) fragments.get(0);
                        allShoppingFragment.post(selectedItems);
                        break;
                    case 1:
                        AllSortFragment allSortFragment = (AllSortFragment) fragments.get(1);
                        allSortFragment.post(selectedItems);
                        break;
                    case 2:
                        RecommendSortFragment recommendSortFragment = (RecommendSortFragment) fragments.get(2);
                        recommendSortFragment.post(selectedItems);
                        break;
                }
            }
        });

        sortListDownBarHo.setSortDownBean(sortDownBean);

        // 禁止滑动
        binding.sortListView.setUserInputEnabled(false);

        fragments = new ArrayList<>();
        fragments.add(new AllShoppingFragment());
        fragments.add(new AllSortFragment());
        fragments.add(new RecommendSortFragment());

        SortChildPageAdapter adapter = new SortChildPageAdapter(this, fragments);
        binding.sortListView.setAdapter(adapter);
        ViewPager2HelperEx.bind(sortListDownBarHo.getSortTabLayout(), binding.sortListView);
    }

    // 搜索Bar
    private void initSearchBar() {
        binding.searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.sortListTab.bottomWindowDismiss();
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //如果输入法在窗口上已经显示，则隐藏，反之则显示
            }
        });
    }

    private void initData() {
        String json = ReadAssertJson.redJson("home_sort_down_data.json", this);
        sortDownBean = new Gson().fromJson(json, SortDownBean.class);
    }

}