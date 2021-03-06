package com.mobiowin.paalansocial.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiowin.paalansocial.model.OrgAddressInfo;
import com.mobiowin.paalansocial.utils.EditTextOpenSansRegular;
import com.mobiowin.paalansocial.R;

/**
 * Created by yashika on 18/1/17.
 */
public class AddressInformation extends Fragment {

    EditTextOpenSansRegular edtAddress, edtCity, edtState, edtCountry, edtPincode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_address_info,null);
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));

        edtAddress = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgAddress);
        edtCity = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgCity);
        edtCountry = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgCountry);
        edtPincode = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgPincode);
        edtState = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgState);

        return view;
    }


    /**
     * Function to set organization info
     */
    public OrgAddressInfo setOrganisationInfo() {
        OrgAddressInfo orgAddressInfo = new OrgAddressInfo();
        orgAddressInfo.setAddress(edtAddress.getText().toString());
        orgAddressInfo.setCity(edtCity.getText().toString());
        orgAddressInfo.setCountry(edtCountry.getText().toString());
        orgAddressInfo.setPincode(edtPincode.getText().toString());
        orgAddressInfo.setState(edtState.getText().toString());

       return orgAddressInfo;

    }



}
