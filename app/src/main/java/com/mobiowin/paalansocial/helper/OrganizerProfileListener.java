package com.mobiowin.paalansocial.helper;


import com.mobiowin.paalansocial.payload.response.ResponseOrganizerProfile;

import retrofit2.Response;

public interface OrganizerProfileListener {
         void onSuccess(Response<ResponseOrganizerProfile> responseOrganizerProfile);
}