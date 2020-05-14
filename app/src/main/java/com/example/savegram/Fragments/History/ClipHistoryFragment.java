package com.example.savegram.Fragments.History;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.savegram.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ClipHistoryFragment extends Fragment {

    private ClipAdapter clipAdapter;
    private List<Clip> clipList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cliphistory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clipAdapter = new ClipAdapter(getContext(),clipList);
        ListView clipListView = view.findViewById(R.id.clipList);
        clipListView.setAdapter(clipAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        retriveClipText();
    }

    private void retriveClipText(){
        final ClipboardManager clipBoard = (ClipboardManager)getActivity().getSystemService(CLIPBOARD_SERVICE);
        clipBoard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipBoard.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                String text = item.getText().toString();
                clipList.add(new Clip(text,""));
                clipAdapter.notifyDataSetChanged();
                Log.d("Clipboard",text+"<---");
            }
        });
    }
}
