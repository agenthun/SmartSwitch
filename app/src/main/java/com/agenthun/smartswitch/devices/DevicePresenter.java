package com.agenthun.smartswitch.devices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.agenthun.smartswitch.data.Device;
import com.agenthun.smartswitch.data.DeviceCmdRsp;
import com.agenthun.smartswitch.data.bean.DevicesRepository;
import com.agenthun.smartswitch.helper.PreferencesHelper;
import com.agenthun.smartswitch.service.RetrofitManager;
import com.agenthun.smartswitch.util.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/23 14:10.
 */

public class DevicePresenter implements DeviceContract.Presenter {
    @NonNull
    private final DevicesRepository mDevicesRepository;

    @NonNull
    private final DeviceContract.View mDeviceView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private Context mContext;

    public DevicePresenter(Context context, @NonNull DevicesRepository mDevicesRepository, @NonNull DeviceContract.View mDeviceView, @NonNull BaseSchedulerProvider mSchedulerProvider) {
        mContext = context;
        this.mDevicesRepository = checkNotNull(mDevicesRepository, "devicesRepository cannot be null");
        this.mDeviceView = checkNotNull(mDeviceView, "deviceView cannot be null!");
        this.mSchedulerProvider = checkNotNull(mSchedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mDeviceView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadDevices(true, PreferencesHelper.getSID(mContext));
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadDevices(final boolean isShowLoadingIndicator, final int sid) {
        if (isShowLoadingIndicator) {
            mDeviceView.setLoadingIndicator(true);
        }
        mDevicesRepository.refreshDevices();

        mSubscriptions.clear();
        Subscription subscription = mDevicesRepository
                .getDevices(sid)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<List<Device>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mDeviceView.showLoadingDevicesError();
                        if (isShowLoadingIndicator) {
                            mDeviceView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onNext(List<Device> devices) {
                        processDevices(isShowLoadingIndicator, sid, devices);
                    }
                });
    }

    private void processDevices(final boolean isShowLoadingIndicator, int sid, final List<Device> devices) {
        if (devices.isEmpty()) {
            mDeviceView.showNoDevices();
        } else {
            final List<Device> res = new ArrayList<>();
            for (Device device :
                    devices) {
                Subscription subscription = mDevicesRepository
                        .refreshDevice(sid, device)
                        .subscribeOn(mSchedulerProvider.computation())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(new Observer<Device>() {
                            @Override
                            public void onCompleted() {
                                if (isShowLoadingIndicator) {
                                    mDeviceView.setLoadingIndicator(false);
                                }
                                mDeviceView.showDevices(res);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Device device) {
                                res.add(device);
                            }
                        });
            }
        }
    }

    @Override
    public void addNewDevice() {
        mDeviceView.showAddDevice();
    }

    @Override
    public void openDeviceDetails(@NonNull Device requestedDevice) {
        checkNotNull(requestedDevice, "requestedDevice cannot be null!");
        mDeviceView.showDeviceDetailsUi(requestedDevice.getMyId());
    }

    @Override
    public void toggleDevice(@NonNull Device requestedDevice, Boolean status) {
        checkNotNull(requestedDevice, "requestedDevice cannot be null!");
        Subscription subscription = mDevicesRepository
                .toggleDevice(PreferencesHelper.getSID(mContext), requestedDevice, status)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Action1<DeviceCmdRsp>() {
                    @Override
                    public void call(DeviceCmdRsp deviceCmdRsp) {
                        if (deviceCmdRsp.getResult() == 1) {
                            mDeviceView.showToggleDevice();
                        }
                    }
                });
    }

    @Override
    public void refreshDevice(int sid, @NonNull Device requestedDevice) {
        checkNotNull(requestedDevice, "requestedDevice cannot be null!");
        Subscription subscription = mDevicesRepository
                .refreshDevice(PreferencesHelper.getSID(mContext), requestedDevice)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        mDeviceView.showRefreshDevice();
                    }
                });
    }
}
