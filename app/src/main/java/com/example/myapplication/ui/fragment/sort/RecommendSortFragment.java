package com.example.myapplication.ui.fragment.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.bean.SortWindowBean;
import com.example.myapplication.databinding.FragmentRecommendSortBinding;
import com.example.myapplication.ui.activity.SortListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐排序
 */
public class RecommendSortFragment extends Fragment implements SortListActivity.OptionCallback {

    private FragmentRecommendSortBinding binding;

    private List<Object> selectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecommendSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void updateList(List<Object> params) {
        this.selectedItems = params;
        logSelected_2(params);
    }

    private void logSelected_2(List<Object> selectedItems) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortWindowBean.RecommendSortListDTO) {
                SortWindowBean.RecommendSortListDTO s = (SortWindowBean.RecommendSortListDTO) selectedItems.get(i);
                str.append("---" + s.getRecommendSortId() + "---" + s.getRecommendSortTitle() + "---");
            }
        }

        // Log.d("TAG", str.toString());
        binding.recommendSortTxt.setText(str.toString());
    }

}