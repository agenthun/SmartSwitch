package com.agenthun.smartswitch.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.adapter.DeviceAdapter;
import com.agenthun.smartswitch.model.Device;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {

    private DeviceAdapter mAdapter;
    private List<Device> mDevices;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupDeviceList(mRecyclerView);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupDeviceList(RecyclerView recyclerView) {
        mDevices = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Device device = new Device(
                    "ACCF23218698",
                    1,
                    257,
                    "v1.0",
                    "1.0.0.4a.gn13_0917",
                    "公牛智能插座" + i,
                    "周一",
                    false
            );
            mDevices.add(device);
        }

        mAdapter = new DeviceAdapter(mDevices);
        recyclerView.setAdapter(mAdapter);
    }
}
