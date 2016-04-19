package com.fandexian.tongxue.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fandexian.tongxue.MyColects;
import com.fandexian.tongxue.MyReleasedGoods;
import com.fandexian.tongxue.R;
import com.fandexian.tongxue.Setting;
import com.fandexian.tongxue.UserDetail;
import com.fandexian.tongxue.Utils.MyApplication;
import com.fandexian.tongxue.Utils.PreferenceHelper;
import com.fandexian.tongxue.activitys.LoginActivity;
import com.fandexian.tongxue.activitys.RegisterActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dexian.fan on 2016/3/17.
 */
public class UserCenter extends Fragment implements View.OnClickListener{
    String url;
    //=======view
    private CircleImageView centerHead;
    private LinearLayout released,collects,personalInfo,setting;

    //=======variable
    private Context _this;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initImageLoader((Context) getActivity());

        View view = inflater.inflate(R.layout.fragment_user_center, container, false);

        initView(view);
        initVariable();




        int height = ((ImageView)view.findViewById(R.id.id_center_bg_img)).getMeasuredHeight();
        Log.e("=======",height+"");
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(height/2)) // 设置成圆角图片
                .build(); // 构建完成

            url ="http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg02.tooopen.com%2Fimages%2F20150524%2Ftooopen_sy_125970524468.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1880114067%2C757853807%26fm%3D21%26gp%3D0.jpg";


        ImageLoader.getInstance().displayImage(url, (ImageView) view.findViewById(R.id.id_center_bg_img), options);


        //ImageLoader.getInstance().displayImage(url,(ImageView)view.findViewById(R.id.id_center_head),options);
        return view;

    }

    private void initVariable() {
        _this = getActivity();
    }

    private void initView(View view) {
        centerHead= (CircleImageView) view.findViewById(R.id.id_center_img_head);
        centerHead.setOnClickListener(this);

        released = (LinearLayout) view.findViewById(R.id.id_center_ll_released);
        collects = (LinearLayout) view.findViewById(R.id.id_center_ll_collect);
        personalInfo = (LinearLayout) view.findViewById(R.id.id_center_ll_personal_info);
        setting = (LinearLayout) view.findViewById(R.id.id_center_ll_setting);

        released.setOnClickListener(this);
        collects.setOnClickListener(this);
        personalInfo.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    public static void initImageLoader(Context context) {
        //缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "universalimageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) //线程池内线程的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // SD卡缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // 由原先的discCache -> diskCache
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_center_img_head:
                if(!PreferenceHelper.from(_this).getBoolean(PreferenceHelper.isLogin)){
                    startActivityForResult(new Intent(_this, LoginActivity.class), 1);
                }else{
                    startActivity(new Intent(_this,UserDetail.class));
                }
                break;
            case R.id.id_center_ll_released:
                if(!PreferenceHelper.from(_this).getBoolean(PreferenceHelper.isLogin)){
                    startActivityForResult(new Intent(_this, LoginActivity.class), 1);
                }else{
                    startActivity(new Intent(_this,MyReleasedGoods.class));
                }
                break;
            case R.id.id_center_ll_collect:
                if(!PreferenceHelper.from(_this).getBoolean(PreferenceHelper.isLogin)){
                    startActivityForResult(new Intent(_this, LoginActivity.class), 1);
                }else{
                    startActivity(new Intent(_this,MyColects.class));
                }
                break;
            case R.id.id_center_ll_personal_info:
                if(!PreferenceHelper.from(_this).getBoolean(PreferenceHelper.isLogin)){
                    startActivityForResult(new Intent(_this, LoginActivity.class), 1);
                }else{
                    startActivity(new Intent(_this,UserDetail.class));
                }
                break;
            case R.id.id_center_ll_setting:
                if(!PreferenceHelper.from(_this).getBoolean(PreferenceHelper.isLogin)){
                    startActivityForResult(new Intent(_this,LoginActivity.class),1);
                }else{
                    startActivity(new Intent(_this,Setting.class));
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){

        }
    }
}

// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
//         DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//                .build(); // 构建完成
