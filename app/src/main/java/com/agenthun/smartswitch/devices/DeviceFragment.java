package com.agenthun.smartswitch.devices;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.adapter.DeviceAdapter;
import com.agenthun.smartswitch.data.Device;
import com.agenthun.smartswitch.data.DeviceCmdReq;
import com.agenthun.smartswitch.data.DeviceCmdRsp;
import com.agenthun.smartswitch.data.DeviceQueryByGroupReq;
import com.agenthun.smartswitch.data.DeviceQueryByGroupRsp;
import com.agenthun.smartswitch.data.LoginReq;
import com.agenthun.smartswitch.data.LoginRsp;
import com.agenthun.smartswitch.data.UserOnlineQueryReq;
import com.agenthun.smartswitch.data.UserOnlineQueryRsp;
import com.agenthun.smartswitch.helper.PreferencesHelper;
import com.agenthun.smartswitch.service.RetrofitManager;
import com.google.common.collect.Maps;

import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {

    private static final String TAG = "DeviceFragment";

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
        requestOnlineUser(getActivity());

        mDevices = new ArrayList<>();

        mAdapter = new DeviceAdapter(getContext(), mDevices);
        mAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onSwitchChecked(int position, Device device, Boolean status) {
                mDevices.get(position).setStatus(status);
                Log.d(TAG, "onSwitchChecked() returned: " + mDevices.get(position).toString());
                configDevice(getActivity(), device, status);
            }

            @Override
            public void onItemClick(View view, int position, Device device) {
                Log.d(TAG, "onItemClick() returned: " + mDevices.get(position).toString());
                queryDevice(getActivity(), position, device);
            }
        });
        recyclerView.setAdapter(mAdapter);

//        requestDeviceGroup(getActivity());
        requestDeviceGroupAndQueryDevice(getActivity());
    }

    private void requestOnlineUser(Activity activity) {
        UserOnlineQueryReq request = new UserOnlineQueryReq(
                PreferencesHelper.getSID(activity),
                15,
                5000,
                "UserOnlineQueryReq",
                10251,
                PreferencesHelper.getUserId(activity)
        );

        RetrofitManager.builder().queryUserOnlineObservable(request)
                .subscribe(new Subscriber<UserOnlineQueryRsp>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mRecyclerView, getString(R.string.error_query_online_user), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onNext(UserOnlineQueryRsp userOnlineQueryRsp) {
                        Log.d(TAG, "onNext() returned: " + userOnlineQueryRsp.getResult());
                    }
                });
    }

    private void requestDeviceGroup(Activity activity) {
        DeviceQueryByGroupReq request = new DeviceQueryByGroupReq(
                PreferencesHelper.getSID(activity),
                17,
                new ArrayList<DeviceQueryByGroupReq.DevicePageListReq>(Arrays.asList(new DeviceQueryByGroupReq.DevicePageListReq(100, -1, 1))),
                5000,
                "DeviceQueryByGroupReq",
                30081
        );

        RetrofitManager.builder().queryDeviceGroupObservable(request)
                .subscribe(new Subscriber<DeviceQueryByGroupRsp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mRecyclerView, getString(R.string.error_query_device_group), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onNext(DeviceQueryByGroupRsp deviceQueryByGroupRsp) {
                        if (deviceQueryByGroupRsp.getResult() != 1) {
                            return;
                        }

                        Map<String, DeviceQueryByGroupRsp.DevicePageListRsp> map = deviceQueryByGroupRsp.getSubRspMap();
                        for (DeviceQueryByGroupRsp.DevicePageListRsp pageListRsp : map.values()) {
                            int positionStart = mDevices.size();
                            mDevices.addAll(pageListRsp.getItemList());
                            mAdapter.notifyItemRangeInserted(positionStart, pageListRsp.getItemList().size());
                        }
                    }
                });
    }

    private void requestDeviceGroupAndQueryDevice(final Activity activity) {
        DeviceQueryByGroupReq requestDeviceGroup = new DeviceQueryByGroupReq(
                PreferencesHelper.getSID(activity),
                17,
                new ArrayList<DeviceQueryByGroupReq.DevicePageListReq>(Arrays.asList(new DeviceQueryByGroupReq.DevicePageListReq(100, -1, 1))),
                5000,
                "DeviceQueryByGroupReq",
                30081
        );

        Observable<DeviceQueryByGroupRsp> deviceGroup = RetrofitManager.builder().queryDeviceGroupObservable(requestDeviceGroup);

        deviceGroup.subscribe(new Subscriber<DeviceQueryByGroupRsp>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(mRecyclerView, getString(R.string.error_query_device_group), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }

            @Override
            public void onNext(DeviceQueryByGroupRsp deviceQueryByGroupRsp) {
                if (deviceQueryByGroupRsp.getResult() != 1) {
                    return;
                }

                Map<String, DeviceQueryByGroupRsp.DevicePageListRsp> map = deviceQueryByGroupRsp.getSubRspMap();
                for (DeviceQueryByGroupRsp.DevicePageListRsp pageListRsp : map.values()) {

                    for (Device device :
                            pageListRsp.getItemList()) {
                        RetrofitManager.builder().updateDeviceObservable(PreferencesHelper.getSID(activity), device)
                                .subscribe(new Action1<Device>() {
                                    @Override
                                    public void call(Device device) {
                                        Log.d(TAG, "updateDeviceObservable() returned: " + device.toString());
                                        mDevices.add(device);
                                        mAdapter.notifyItemInserted(mDevices.size());
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                    }

                }
            }
        });
    }

    private void queryDevice(Activity activity, final int position, final Device device) {
        RetrofitManager.builder().updateDeviceObservable(PreferencesHelper.getSID(activity), device)
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        Log.d(TAG, "updateDeviceObservable() returned: " + device.toString());

                        mDevices.get(position).setConfigTime(device.getConfigTime());
                        mDevices.get(position).setConfigStatus(device.getConfigStatus());
                        mDevices.get(position).setConfigInterval(device.getConfigInterval());
                        mDevices.get(position).setStatus(device.getStatus());

                        mAdapter.notifyItemChanged(position);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void configDevice(Activity activity, Device device, Boolean status) {
        Map<String, String> map = new HashMap<>(1);
        map.put(device.getMac(), status ? DeviceCmdReq.CMD_SET_STATUS_OPEN : DeviceCmdReq.CMD_SET_STATUS_CLOSE);
        DeviceCmdReq request = new DeviceCmdReq(
                PreferencesHelper.getSID(activity),
                map,
                DeviceCmdReq.FUNCTION_SET,
                DeviceCmdReq.FUNCTION_SET_CID
        );

        RetrofitManager.builder().operateDeviceObservable(request)
                .subscribe(new Action1<DeviceCmdRsp>() {
                    @Override
                    public void call(DeviceCmdRsp deviceCmdRsp) {
                        Log.d(TAG, "operateDeviceObservable() returned: " + deviceCmdRsp.toString());
                    }
                });
    }

    private void configDevice(Activity activity, Device device, String cmd) {
        Map<String, String> map = new HashMap<>(1);
        map.put(device.getMac(), cmd);
        DeviceCmdReq request = new DeviceCmdReq(
                PreferencesHelper.getSID(activity),
                map,
                DeviceCmdReq.FUNCTION_SET,
                DeviceCmdReq.FUNCTION_SET_CID
        );

        RetrofitManager.builder().operateDeviceObservable(request)
                .subscribe(new Action1<DeviceCmdRsp>() {
                    @Override
                    public void call(DeviceCmdRsp deviceCmdRsp) {
                        Log.d(TAG, "operateDeviceObservable() returned: " + deviceCmdRsp.toString());
                    }
                });
    }
}
