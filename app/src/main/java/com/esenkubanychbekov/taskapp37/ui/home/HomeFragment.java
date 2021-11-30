package com.esenkubanychbekov.taskapp37.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.esenkubanychbekov.taskapp37.R;
import com.esenkubanychbekov.taskapp37.adapter.NewsAdapter;
import com.esenkubanychbekov.taskapp37.databinding.FragmentHomeBinding;
import com.esenkubanychbekov.taskapp37.interfaces.OnItemClickListener;
import com.esenkubanychbekov.taskapp37.model.News;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private NewsAdapter adapter;
    private ArrayList<News> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
        adapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                News news = adapter.getItem(pos);
                Toast.makeText(requireContext(),news.getTitle(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(int pos) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                dialog.setMessage("Удалить").setTitle("Delete");
                dialog.setPositiveButton("Отмена", (dialog1, which) -> dialog1.cancel());
                dialog.setNegativeButton("Да", (dialog12, which) -> {
                    Toast.makeText(requireContext(),"Удален",Toast.LENGTH_SHORT).show();
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        binding.fab.setOnClickListener(v -> {
            openFragment();
        });

        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), (requestKey, result) -> {
            News news = (News) result.getSerializable("text");
            adapter.addItem(news);
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
    }

    private void initList() {
        binding.recyclerView.setAdapter(adapter );
    }

    private void openFragment(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.newsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}