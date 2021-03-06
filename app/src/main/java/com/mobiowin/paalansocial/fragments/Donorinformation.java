package com.mobiowin.paalansocial.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiowin.paalansocial.payload.request.IndivitualReqRegistration;
import com.mobiowin.paalansocial.utils.EditTextOpenSansRegular;
import com.mobiowin.paalansocial.R;
import com.mobiowin.paalansocial.services.Device;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yashika on 7/2/17.
 */
public class Donorinformation extends Fragment {
    private EditTextOpenSansRegular edtName, edtEmail, edtContactNo;
    EditTextOpenSansRegular edtAddress, edtCity, edtState, edtCountry, edtPincode;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_donor_info,null);
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        edtName = (EditTextOpenSansRegular)view.findViewById(R.id.edtName);
        edtEmail = (EditTextOpenSansRegular)view.findViewById(R.id.edtEmail);
        edtContactNo = (EditTextOpenSansRegular)view.findViewById(R.id.edtContactNumber);

        edtAddress = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgAddress);
        edtCity = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgCity);
        edtCountry = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgCountry);
        edtPincode = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgPincode);
        edtState = (EditTextOpenSansRegular)view.findViewById(R.id.edtOrgState);


        return view;
    }



    /**
     * Function to check validation on email
     * @return : email validation result
     */
    public boolean isValidEmailId(String email){
        Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

        Matcher matcher1 = pattern1.matcher(email);
        Log.d("", "isBasicInfoFilled: valid email i/p:"+email);
        if (!matcher1.matches()) {
            //show your message if not matches with email pattern
            return false;
        }else
            return true;
    }

    /**
     * Function to save individual info
     * @return : individual info
     */
    public IndivitualReqRegistration setIndividualInfo(){

        IndivitualReqRegistration indivitualReqRegistration = IndivitualReqRegistration.get(
                edtName.getText().toString(),
                edtEmail.getText().toString(),
                edtContactNo.getText().toString(),
                "",
                Device.getDeviceId(getActivity()),"","");

        return indivitualReqRegistration;

    }

    public String getAddress() {
        return edtAddress.getText().toString() +", "+edtCity.getText().toString()+","+edtState.getText().toString()+
                ", "+edtPincode.getText().toString() +", "+ edtCountry.getText().toString();
    }


}
