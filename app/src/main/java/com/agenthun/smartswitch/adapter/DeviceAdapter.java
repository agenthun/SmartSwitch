package com.agenthun.smartswitch.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.model.Device;

import java.util.List;

/**
 * @project ESeal
 * @authors agenthun
 * @date 16/3/9 上午1:27.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private static final String TAG = "DeviceAdapter";
    private List<Device> devices;

    public DeviceAdapter(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_device, null);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, final int position) {
        Device device = getItem(position);
//        holder.deviceImage.setImageBitmap(device.getImage());
        holder.deviceName.setText(device.getName());
        holder.deviceTimeSetting.setText(device.getTimeSetting());
//        holder.deviceStatus.setImageBitmap(device.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getItem(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public Device getItem(int position) {
        return devices.get(position);
    }

    public void clear() {
        devices.clear();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        private ImageView deviceImage;
        private AppCompatTextView deviceName;
        private AppCompatTextView deviceTimeSetting;
        private SwitchCompat deviceStatus;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            deviceImage = (ImageView) itemView.findViewById(R.id.device_image);
            deviceName = (AppCompatTextView) itemView.findViewById(R.id.device_name);
            deviceTimeSetting = (AppCompatTextView) itemView.findViewById(R.id.device_time_setting);
            deviceStatus = (SwitchCompat) itemView.findViewById(R.id.device_status);
        }
    }

    //itemClick interface
    public interface OnItemClickListener {
        void onItemClick(View view, Device device);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
