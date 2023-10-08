package com.example.myapplication.ui.fragment.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.bean.SortDownBean;
import com.example.myapplication.databinding.FragmentAllSortBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部分类
 */
public class AllSortFragment extends Fragment {

    private FragmentAllSortBinding binding;

    private List<Object> selectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        logSelected_1(selectedItems);
    }

    public void post(List<Object> params) {
        selectedItems = params;
        if (binding != null) {
            logSelected_1(selectedItems);
        }
    }

    private void logSelected_1(List<Object> selectedItems) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllSortListDTO) {
                SortDownBean.AllSortListDTO s = (SortDownBean.AllSortListDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllSortId() + "---" + s.getAllSortTitle() + "---");
            } else if (selectedItems.get(i) instanceof SortDownBean.AllSortListDTO.AllSortChildDTO) {
                SortDownBean.AllSortListDTO.AllSortChildDTO s = (SortDownBean.AllSortListDTO.AllSortChildDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllSortChildId() + "---" + s.getAllSortChildTitle() + "---");
            }
        }

        // Log.d("TAG", str.toString());
        binding.allSortTxt.setText(str.toString());
    }

}