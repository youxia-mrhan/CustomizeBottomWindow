package com.example.myapplication.ui.fragment.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.bean.SortWindowBean;
import com.example.myapplication.databinding.FragmentAllSortBinding;
import com.example.myapplication.ui.activity.SortListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部分类
 */
public class AllSortFragment extends Fragment implements SortListActivity.OptionCallback {

    private FragmentAllSortBinding binding;

    private List<Object> selectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void updateList(List<Object> params) {
        this.selectedItems = params;
        logSelected_1(params);
    }

    private void logSelected_1(List<Object> selectedItems) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortWindowBean.AllSortListDTO) {
                SortWindowBean.AllSortListDTO s = (SortWindowBean.AllSortListDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllSortId() + "---" + s.getAllSortTitle() + "---");
            } else if (selectedItems.get(i) instanceof SortWindowBean.AllSortListDTO.AllSortChildDTO) {
                SortWindowBean.AllSortListDTO.AllSortChildDTO s = (SortWindowBean.AllSortListDTO.AllSortChildDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllSortChildId() + "---" + s.getAllSortChildTitle() + "---");
            }
        }
        // Log.d("TAG", str.toString());
        binding.allSortTxt.setText(str.toString());
    }

}