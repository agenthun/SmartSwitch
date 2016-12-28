package com.agenthun.smartswitch.devices;

import android.support.annotation.NonNull;

import com.agenthun.smartswitch.BasePresenter;
import com.agenthun.smartswitch.BaseView;
import com.agenthun.smartswitch.data.Device;

import java.util.List;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/23 13:43.
 */

public interface DeviceContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showDevices(List<Device> devices);

        void showLoadingDevicesError();

        void showNoDevices();

        void showAddDevice();

        void showDeviceDetailsUi(String deviceId);

        void showToggleDevice();

        void showRefreshDevice();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);

        void loadDevices(boolean isShowLoadingIndicator, int sid);

        void addNewDevice();

        void openDeviceDetails(@NonNull Device requestedDevice);

        void toggleDevice(@NonNull Device requestedDevice, Boolean status);

        void refreshDevice(int sid, @NonNull Device requestedDevice);
    }
}
