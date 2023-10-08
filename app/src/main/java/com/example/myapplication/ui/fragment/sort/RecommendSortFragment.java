package com.example.myapplication.ui.fragment.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.bean.SortDownBean;
import com.example.myapplication.databinding.FragmentRecommendSortBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐排序
 */
public class RecommendSortFragment extends Fragment {

    private FragmentRecommendSortBinding binding;

    private List<Object> selectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecommendSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        logSelected_2(selectedItems);
    }

    public void post(List<Object> params) {
        selectedItems = params;
        if (binding != null) {
            logSelected_2(selectedItems);
        }
    }

    private void logSelected_2(List<Object> selectedItems) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.RecommendSortListDTO) {
                SortDownBean.RecommendSortListDTO s = (SortDownBean.RecommendSortListDTO) selectedItems.get(i);
                str.append("---" + s.getRecommendSortId() + "---" + s.getRecommendSortTitle() + "---");
            }
        }

        // Log.d("TAG", str.toString());
        binding.recommendSortTxt.setText(str.toString());
    }

}