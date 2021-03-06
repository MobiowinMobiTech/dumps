package com.mobiowin.paalansocial.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiowin.paalansocial.db.DBAdapter;
import com.mobiowin.paalansocial.helper.PaalanGetterSetter;
import com.mobiowin.paalansocial.R;
import com.mobiowin.paalansocial.activity.ActivityFragmentPlatform;
import com.mobiowin.paalansocial.db.Attributes;
import com.mobiowin.paalansocial.utils.CommanUtils;
import com.mobiowin.paalansocial.utils.RoundedImageView;
import com.mobiowin.paalansocial.utils.TextViewOpenSansRegular;

/**
 * Created by Raheel on 2/2/17.
 */

public class FragmentGroupsProfile extends Fragment {

    private static final String TAG = FragmentGroupsProfile.class.getCanonicalName();

    private TextViewOpenSansRegular txtName,txtMobile,txtEmail,txtAddress,txtRole,txtNews,txtGovt,txtRegNo,
            txtFBLink,txtLinkedInLink,txtWebsite,txtTwitter,txtPresenceArea;

    private RoundedImageView dpImageView;

    private String strDp, strName,strMobile,strEmail,strAddress,strRole,strNews,strGovt,strRegNo,
            strFBLink,strLinkedInLink,strWebsite,strTwitter,strPresenceArea;

    private String orgID = "";

    private DBAdapter dbAdapter;
    private Cursor cursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_organization_profile, null, false);
        init(view);
        return view;
    }

    private void init(View view) {

        dpImageView = (RoundedImageView) view.findViewById(R.id.img_org_dp);
        txtName = (TextViewOpenSansRegular) view.findViewById(R.id.txtNameValue);
        txtMobile = (TextViewOpenSansRegular) view.findViewById(R.id.txtMobileValue);
        txtEmail = (TextViewOpenSansRegular) view.findViewById(R.id.txtEmailValue);
        txtAddress = (TextViewOpenSansRegular) view.findViewById(R.id.txtAddressValue);
        txtRole = (TextViewOpenSansRegular) view.findViewById(R.id.txtRoleValue);
        txtNews = (TextViewOpenSansRegular) view.findViewById(R.id.txtNewsValue);
        txtGovt = (TextViewOpenSansRegular) view.findViewById(R.id.txtAuthorisedValue);
        txtRegNo = (TextViewOpenSansRegular) view.findViewById(R.id.txtRegNoValue);
        txtFBLink = (TextViewOpenSansRegular) view.findViewById(R.id.txtFBValue);
        txtLinkedInLink = (TextViewOpenSansRegular) view.findViewById(R.id.txtLINKEDINValue);
        txtWebsite = (TextViewOpenSansRegular) view.findViewById(R.id.txtWebsiteValue);
        txtTwitter = (TextViewOpenSansRegular) view.findViewById(R.id.txtTwitterValue);
        txtPresenceArea = (TextViewOpenSansRegular) view.findViewById(R.id.txtPresenceValue);

        dbAdapter = new DBAdapter(getActivity());

    }


    private void getPoulatedData() {

        Bundle bundle = getArguments();

        if(bundle!=null){

            strDp = bundle.getString(Attributes.Database.GROUPS_PROFILE_DP_IMG);
            strName = bundle.getString(Attributes.Database.GROUPS_PROFILE_NAME);
            strMobile = bundle.getString(Attributes.Database.GROUPS_PROFILE_MOBILE_NO);
            strEmail = bundle.getString(Attributes.Database.GROUPS_PROFILE_EMAIL);
            strAddress = bundle.getString(Attributes.Database.GROUPS_PROFILE_ADDRESS);
            strRole = bundle.getString(Attributes.Database.GROUPS_PROFILE_ROLE);
            strNews = bundle.getString(Attributes.Database.GROUPS_PROFILE_IS_NEWS_LETTER);
            strGovt = bundle.getString(Attributes.Database.GROUPS_PROFILE_IS_GOVT_REGISTER);
            strRegNo = bundle.getString(Attributes.Database.GROUPS_PROFILE_REGISTER);
            strFBLink = bundle.getString(Attributes.Database.GROUPS_PROFILE_FB_LINK);
            strLinkedInLink = bundle.getString(Attributes.Database.GROUPS_PROFILE_LINKEDIN);
            strWebsite = bundle.getString(Attributes.Database.GROUPS_PROFILE_WEBSITE);
            strTwitter = bundle.getString(Attributes.Database.GROUPS_PROFILE_TWITTER);
            strPresenceArea = bundle.getString(Attributes.Database.GROUPS_PROFILE_PRESENCE_AREA);

        }else{

            if(PaalanGetterSetter.getOrgID()!=null) {
                orgID = PaalanGetterSetter.getOrgID();
            }

            dbAdapter.open();
            cursor = dbAdapter.getGroupsProfileById(orgID);
            if(cursor!=null){
                cursor.moveToFirst();
                if(cursor.moveToFirst()){
                    do{
                        strDp = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_DP_IMG));
                        strName = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_NAME));
                        strMobile = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_MOBILE_NO));
                        strEmail = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_EMAIL));
                        strAddress = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_ADDRESS));
                        strRole = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_ROLE));
                        strNews = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_IS_NEWS_LETTER));
                        strGovt = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_IS_GOVT_REGISTER));
                        strRegNo = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_REGISTER));
                        strFBLink = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_FB_LINK));
                        strLinkedInLink = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_LINKEDIN));
                        strWebsite = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_WEBSITE));
                        strTwitter = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_TWITTER));
                        strPresenceArea = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_PRESENCE_AREA));
                    }while (cursor.moveToNext());
                }
            }
            dbAdapter.close();

        }


        txtName.setText(strName);
        txtMobile.setText(strMobile);
        txtEmail.setText(strEmail);
        txtAddress.setText(strAddress);
        txtRole.setText(strRole);
        txtNews.setText(strNews);
        txtGovt.setText(strGovt);
        txtRegNo.setText(strRegNo);
        txtFBLink.setText(strFBLink);
        txtLinkedInLink.setText(strLinkedInLink);
        txtWebsite.setText(strWebsite);
        txtTwitter.setText(strTwitter);
        txtPresenceArea.setText(strPresenceArea);

        CommanUtils.updateImage(getActivity(),dpImageView,strDp,R.drawable.unknown);

    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.Organization_profile),
                R.drawable.ic_arrow_back_black_24dp);
           getPoulatedData();
    }
}