package com.mobiowin.paalansocial.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;

import com.mobiowin.paalansocial.activity.ActivityFragmentPlatform;
import com.mobiowin.paalansocial.db.DBAdapter;
import com.mobiowin.paalansocial.payload.request.OrganizationReqCreateEvent;
import com.mobiowin.paalansocial.payload.response.OrgResCreateEvent;
import com.mobiowin.paalansocial.services.Device;
import com.mobiowin.paalansocial.services.PaalanServices;
import com.mobiowin.paalansocial.utils.ButtonOpenSansSemiBold;
import com.mobiowin.paalansocial.utils.CommanUtils;
import com.mobiowin.paalansocial.utils.EditTextOpenSansRegular;
import com.mobiowin.paalansocial.utils.NetworkUtil;
import com.mobiowin.paalansocial.utils.PreferenceUtils;
import com.mobiowin.paalansocial.utils.TextViewOpenSansRegular;
import com.mobiowin.paalansocial.R;
import com.mobiowin.paalansocial.helper.Social;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FragmentCreateEvent extends Fragment {

    private final static String TAG = FragmentCreateEvent.class.getCanonicalName();

    private Spinner spinnerOsversions;
    private EditTextOpenSansRegular edtName,edtTitle, edtSubtitle, edtDescription, edtOther,edtLocation;
    private ButtonOpenSansSemiBold btnCreateEvent;
    private TextViewOpenSansRegular edtStartDate,edtEndDate;
    private ImageView imgStartDate, imgEndDate;

    private String  strName,eventId, strTitle, strSubtitle, strDescription, strOther, strStartDate, strEndDate,strLocation,
            strCategory = "";
    private DBAdapter dbAdapter;
    private PreferenceUtils pref;
    private boolean shouldBeUpdated = false;
    private int mYear, mMonth, mDay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_event, null, false);

        dbAdapter = new DBAdapter(getContext());
        pref = new PreferenceUtils(getActivity());

        edtName = (EditTextOpenSansRegular) view.findViewById(R.id.edt_name);
        edtTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_title);
        edtSubtitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_subtitle);
        edtDescription = (EditTextOpenSansRegular) view.findViewById(R.id.edt_description);
        edtOther = (EditTextOpenSansRegular) view.findViewById(R.id.edt_other);
        edtLocation = (EditTextOpenSansRegular) view.findViewById(R.id.edt_location);
        edtStartDate = (TextViewOpenSansRegular) view.findViewById(R.id.txt_startdate);
        edtEndDate = (TextViewOpenSansRegular) view.findViewById(R.id.txt_enddate);
        imgStartDate = (ImageView) view.findViewById(R.id.img_start_date);
        imgEndDate = (ImageView) view.findViewById(R.id.img_enddate_calender);
        btnCreateEvent = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_create_event);

        strCategory = getResources().getStringArray(R.array.event_category)[0];


        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgStartDate.performClick();
            }
        });


        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgEndDate.performClick();
            }
        });


        spinnerOsversions = (Spinner) view.findViewById(R.id.osversions);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.event_category));
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);

        Bundle bundle = getArguments();

        if(bundle!=null) {

            shouldBeUpdated = bundle.getBoolean("OPERATION_STATUS");

            eventId = bundle.getString("ID");
            edtName.setText(bundle.getString("NAME"));
            edtTitle.setText(bundle.getString("TITLE"));
            edtSubtitle.setText(bundle.getString("SUB_TITLE"));
            edtDescription.setText(bundle.getString("DESCRIPTION"));
            edtOther.setText(bundle.getString("OTHER"));
            edtStartDate.setText(bundle.getString("STARTDATE"));
            edtEndDate.setText(bundle.getString("ENDDATE"));
            edtLocation.setText(bundle.getString("LOCATION"));
            String category = bundle.getString("CATEGORY");

            int pos = 0;
            for(int i=0;i<getResources().getStringArray(R.array.event_category).length;i++){
                if(getResources().getStringArray(R.array.event_category)[i].equals(category)){
                    pos=i;
                    break;
                }
            }
            spinnerOsversions.setSelection(pos);

        }

        btnCreateEvent.setText(shouldBeUpdated ? getResources().getString(R.string.Update) :
                getResources().getString(R.string.Create));

        spinnerOsversions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCategory = spinnerOsversions.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strTitle = edtTitle.getText().toString();
                strSubtitle = edtSubtitle.getText().toString();
                strDescription = edtDescription.getText().toString();
                strStartDate = edtStartDate.getText().toString();
                strOther = edtOther.getText().toString();
                strEndDate = edtEndDate.getText().toString();
                strLocation = edtLocation.getText().toString();
                strName = edtName.getText().toString();

                if(strName.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_name));
                }else if(strTitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_title));
                }else if(strSubtitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_sub_title));
                }else if(strDescription.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_description));
                }else if(strStartDate.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_startdate));
                }else if(strEndDate.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_enddate));
                }else if(strOther.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_others));
                }else if(strLocation.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_location));
                }else{
                    getRetrofitCall();
                }

            }
        });

        imgStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String day = ""+dayOfMonth;
                        day=day.length()<2 ? "0"+day : day;

                        String months = ""+(month+1);
                        months=months.length()<2 ? "0"+months : months;

                        edtStartDate.setText(day + "-" + months + "-" + year);
                        edtStartDate.setTextColor(getResources().getColor(R.color.primary_text));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        imgEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                 DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if(!edtStartDate.getText().toString().isEmpty()){

                            String day = ""+dayOfMonth;
                            day=day.length()<2 ? "0"+day : day;
                            String months = ""+(month+1);
                            months=months.length()<2 ? "0"+months : months;

                            try {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                Date startDate = formatter.parse(edtStartDate.getText().toString());
                                Date endDate = formatter.parse(day + "-" + months + "-" + year);

                                if(endDate.getTime()>startDate.getTime()){
                                    edtEndDate.setText(day + "-" + months + "-" + year);
                                    edtEndDate.setTextColor(getResources().getColor(R.color.primary_text));
                                }else{
                                    CommanUtils.showToast(getActivity(),getString(R.string.error_enddate));

                                }

                            }catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else{
                            CommanUtils.showToast(getActivity(),getString(R.string.error_startdate));
                        }
                    }
                 }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        return view;
    }


    public void getRetrofitCall() {

        if (NetworkUtil.isInternetConnected(getActivity())) {
            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());
            String action = shouldBeUpdated ? Social.UPDATE_ACTION : Social.EVENT_ACTION;
            OrganizationReqCreateEvent reqCreateEvent = OrganizationReqCreateEvent.get(pref.getOrgId(), eventId, strName,strTitle, strSubtitle,
                    strDescription, strStartDate, strEndDate, strOther,strCategory,strLocation,action,Device.getNotificationId(getActivity()));

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
            Call<OrgResCreateEvent> resProfileCall = mPaalanServices.orgCreateEvent(reqCreateEvent);
            resProfileCall.enqueue(new Callback<OrgResCreateEvent>() {
                @Override
                public void onResponse(Call<OrgResCreateEvent> call, Response<OrgResCreateEvent> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {

                            dbAdapter.open();

                            int status = dbAdapter.populatingEventsIntoDB(pref.getOrgId(),
                                    response.body().getData()[0].getEventid()
                                    ,eventId,strName,strTitle, strSubtitle, strDescription, strOther, strStartDate,
                                    strEndDate,"F",strCategory,strLocation,null,pref.getLoginType());
                            String message = status==0 ? getResources().getString(R.string.event_created) :
                                    getResources().getString(R.string.event_updated);
                            CommanUtils.showToast(getActivity(),message);
                            getActivity().getSupportFragmentManager().popBackStack();

                            dbAdapter.close();
                            getClearFields();

                        } else {
                            CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_went_wrong));
                        }
                    } else if (response.body() == null) {
                        CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_server));
                    }
                }


                @Override
                public void onFailure(Call<OrgResCreateEvent> call, Throwable t) {
                    CommanUtils.hideDialog();
                    CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_timeout));
                }
            });
        } else {
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }

    private void getClearFields() {

        eventId = "";
        strTitle = "";
        strSubtitle = "";
        strDescription = "";
        strOther = "";
        strStartDate = "";
        strEndDate = "";
        strLocation = "";
        strName = "";

        edtTitle.setText("");
        edtSubtitle.setText("");
        edtDescription.setText("");
        edtOther.setText("");
        edtStartDate.setText("");
        edtEndDate.setText("");
        edtLocation.setText("");
        edtName.setText("");

        spinnerOsversions.setSelection(0);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!shouldBeUpdated) {
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getStringArray(R.array.events_array)[0],
                    R.drawable.ic_arrow_back_black_24dp);
        }else{
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.update_event),
                    R.drawable.ic_arrow_back_black_24dp);
        }

    }

}

