package com.agenthun.smartswitch.devices;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.adapter.DeviceAdapter;
import com.agenthun.smartswitch.adddevice.AddDeviceActivity;
import com.agenthun.smartswitch.data.Device;
import com.agenthun.smartswitch.data.DeviceCmdReq;
import com.agenthun.smartswitch.data.DeviceCmdRsp;
import com.agenthun.smartswitch.data.UserOnlineQueryReq;
import com.agenthun.smartswitch.data.UserOnlineQueryRsp;
import com.agenthun.smartswitch.helper.PreferencesHelper;
import com.agenthun.smartswitch.service.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment implements DeviceContract.View {

    private static final String TAG = "DeviceFragment";

    private DeviceContract.Presenter mPresenter;

    private DeviceAdapter mAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.noDevices)
    View mNoDevicesView;
    @Bind(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout swipeRefreshLayout;

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

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        swipeRefreshLayout.setScrollUpChild(mRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadDevices(true, PreferencesHelper.getSID(getActivity()));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addNewDevice();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupDeviceList(mRecyclerView);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    private DeviceAdapter.OnItemClickListener mOnItemClickListener = new DeviceAdapter.OnItemClickListener() {
        @Override
        public void onSwitchChecked(int position, Device device, Boolean status) {
            Log.d(TAG, "onSwitchChecked() returned: " + device.toString());
            mPresenter.toggleDevice(device, status);
        }

        @Override
        public void onItemClick(View view, int position, Device device) {
            Log.d(TAG, "onItemClick() returned: " + device.toString());
//            mPresenter.refreshDevice(PreferencesHelper.getSID(getActivity()), device);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode);
    }

    private void setupDeviceList(RecyclerView recyclerView) {
        requestOnlineUser(getActivity());

        mAdapter = new DeviceAdapter(getContext(), new ArrayList<Device>(0));
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerView.setAdapter(mAdapter);
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<DeviceCmdRsp>() {
                    @Override
                    public void call(DeviceCmdRsp deviceCmdRsp) {
                        Log.d(TAG, "operateDeviceObservable() returned: " + deviceCmdRsp.toString());
                    }
                });
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showDevices(List<Device> devices) {
        mAdapter.updateAllDatas(devices);

        mRecyclerView.setVisibility(View.VISIBLE);
        mNoDevicesView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingDevicesError() {
        showMessage(getString(R.string.error_query_device_group));
    }

    @Override
    public void showNoDevices() {
        mRecyclerView.setVisibility(View.GONE);
        mNoDevicesView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddDevice() {
        Intent intent = new Intent(getContext(), AddDeviceActivity.class);
        startActivityForResult(intent, AddDeviceActivity.REQUEST_ADD_DEVICE);
    }

    @Override
    public void showDeviceDetailsUi(String deviceId) {

    }

    @Override
    public void showToggleDevice() {

    }

    @Override
    public void showRefreshDevice() {

    }

    @Override
    public void setPresenter(DeviceContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
