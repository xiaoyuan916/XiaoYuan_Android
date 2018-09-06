package com.sgcc.pda.hardware.frame.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BlueDeviceEntity implements Parcelable{
	public String name;
	public String mac;
	public String state;
	public boolean isSelete;
	public BluetoothDevice device;

	public BlueDeviceEntity(){

	}

	protected BlueDeviceEntity(Parcel in) {
		name = in.readString();
		mac = in.readString();
		state = in.readString();
		isSelete = in.readByte() != 0;
		device = in.readParcelable(BluetoothDevice.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(mac);
		dest.writeString(state);
		dest.writeByte((byte) (isSelete ? 1 : 0));
		dest.writeParcelable(device, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<BlueDeviceEntity> CREATOR = new Creator<BlueDeviceEntity>() {
		@Override
		public BlueDeviceEntity createFromParcel(Parcel in) {
			return new BlueDeviceEntity(in);
		}

		@Override
		public BlueDeviceEntity[] newArray(int size) {
			return new BlueDeviceEntity[size];
		}
	};
}
