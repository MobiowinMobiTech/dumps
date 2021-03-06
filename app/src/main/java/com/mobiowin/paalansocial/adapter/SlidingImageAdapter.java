package com.mobiowin.paalansocial.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobiowin.paalansocial.activity.RegisterUser;
import com.mobiowin.paalansocial.utils.CommanUtils;
import com.mobiowin.paalansocial.utils.PreferenceUtils;
import com.mobiowin.paalansocial.R;
import com.mobiowin.paalansocial.helper.Social;
import com.mobiowin.paalansocial.utils.ButtonOpenSansSemiBold;

/**
 * Created by Dharmedra Gupta
 * date 19/10/2016
 */
public class SlidingImageAdapter extends PagerAdapter {

    private final static String TAG=SlidingImageAdapter.class.getSimpleName();
    private String[] IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private ButtonOpenSansSemiBold btnRegistration;
    private ImageView imageView;
    private PreferenceUtils pref;

    public SlidingImageAdapter(Context context, String[] IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
        pref = new PreferenceUtils(context);

        for(int i=0;i<IMAGES.length;i++){
            Log.e(TAG,"url : "+IMAGES[i]);
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {

        return IMAGES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        btnRegistration = (ButtonOpenSansSemiBold)imageLayout.findViewById(R.id.btn_register);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegisterUser.class));
            }
        });
        CommanUtils.updateImage(context, imageView, IMAGES[position],R.drawable.paalan_logo);

        try {
            if(pref.getLoginType().equals(Social.IND_ENTITY)) {
                if (position == 2) {
                    btnRegistration.setVisibility(View.VISIBLE);
                } else {
                    btnRegistration.setVisibility(View.INVISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
