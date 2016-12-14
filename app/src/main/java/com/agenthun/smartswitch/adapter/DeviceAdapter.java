package com.agenthun.smartswitch.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.data.Device;

import java.util.List;

/**
 * @project ESeal
 * @authors agenthun
 * @date 16/3/9 上午1:27.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private static final String TAG = "DeviceAdapter";
    private Context context;
    private List<Device> devices;

    public DeviceAdapter(Context context, List<Device> devices) {
        this.context = context;
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
        final Device device = getItem(position);
        holder.deviceImage.setImageResource(R.drawable.ic_default_device);
        holder.deviceName.setText(device.getName());
        holder.deviceConfigTime.setText(device.getConfigTime());
        holder.deviceConfigStatus.setText(device.getConfigStatus() ?
                context.getString(R.string.status_open) : context.getString(R.string.status_close)
        );
        holder.deviceConfigInterval.setText(device.getConfigInterval());
        holder.deviceStatus.setChecked(device.getStatus());
        holder.deviceStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onSwitchChecked(position, getItem(position), b);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position, getItem(position));
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
        private AppCompatTextView deviceConfigTime;
        private AppCompatTextView deviceConfigStatus;
        private AppCompatTextView deviceConfigInterval;
        private SwitchCompat deviceStatus;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            deviceImage = (ImageView) itemView.findViewById(R.id.device_image);
            deviceName = (AppCompatTextView) itemView.findViewById(R.id.device_name);
            deviceConfigTime = (AppCompatTextView) itemView.findViewById(R.id.device_config_time);
            deviceConfigStatus = (AppCompatTextView) itemView.findViewById(R.id.device_config_status);
            deviceConfigInterval = (AppCompatTextView) itemView.findViewById(R.id.device_config_interval);
            deviceStatus = (SwitchCompat) itemView.findViewById(R.id.device_status);
        }
    }

    //itemClick interface
    public interface OnItemClickListener {
        void onSwitchChecked(int position, Device device, Boolean status);

        void onItemClick(View view, int position, Device device);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
