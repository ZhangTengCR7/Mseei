package com.adu.main.mytimertask.base;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;

/**
 *
 * @ClassName: MyApplication
 * @Description:安卓中的全局类
 * @author: zt
 *  设定文件
 * @date 2016年4月13日 下午2:19:17
 *
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(this);
	}
	/**
	 *
	 * @Title: initImageLoader
	 * @Description: 配置开源框架ImageLoader，供整个应用使用
	 * @param @param context    设定文件
	 * @date 2016年4月13日 下午2:20:42
	 * @return void    返回类型
	 *
	 */
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().build();
		ImageLoader.getInstance().init(config);
	}
}
