package com.agenthun.smartswitch.adddevice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agenthun.smartswitch.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddDeviceActivityFragment extends Fragment {

    public AddDeviceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_device, container, false);
    }
}
