package com.txh;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.txh.admin.AdminActivity;
import com.txh.json.login.LoginForm;
import com.txh.json.Message;
import com.txh.json.login.LoginInfo;
import com.txh.teacher.TeacherActivity;
import com.txh.utils.ToastUtil;
import com.txh.utils.UrlUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity implements OnClickListener {

	private String[] idCard = new String[]{"管理员","教师"};
	private TextView mBtnLogin;
	private TextView main_username;
	private TextView main_password;

	private View progress;
	
	private View mInputLayout;

	private float mWidth, mHeight;

	private LinearLayout mName, mPsw;//用来隐藏输入框
	private Spinner main_idCard;
	Integer userType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();
	}

	private void initView() {
		main_username = findViewById(R.id.main_username);
		main_password = findViewById(R.id.main_password);
		mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
		progress = findViewById(R.id.layout_progress);
		mInputLayout = findViewById(R.id.input_layout);
		mName = (LinearLayout) findViewById(R.id.input_layout_name);
		mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
		main_idCard = findViewById(R.id.main_idCard);

		mBtnLogin.setOnClickListener(this);
		main_idCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				userType = i;
//				Toast.makeText(MainActivity.this, ""+idCard[i], Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
	}

	@Override
	public void onClick(View v) {

		mWidth = mBtnLogin.getMeasuredWidth();
		mHeight = mBtnLogin.getMeasuredHeight();

		if (userType == 0) {
			if (main_username.getText().toString().isEmpty() && main_password.getText().toString().isEmpty()) {
				showMessage("用户名不能为空,密码不能为空");
			} else if (main_username.getText().toString().isEmpty()) {
				showMessage("用户名不能为空");
			} else if (main_password.getText().toString().isEmpty()) {
				showMessage("密码不能为空");
			} else if (main_username.getText().toString().equals("admin") && main_password.getText().toString().equals("admin")) {
				//隐藏输入的用户名和密码
				mName.setVisibility(View.INVISIBLE);
				mPsw.setVisibility(View.INVISIBLE);
				MyApplication.realname = "管理员";
				showMessage("登录成功");
				//开始动画并跳转界面
				inputAnimator(mInputLayout, mWidth);
			} else {
				showMessage("用户名或密码错误");
			}
		} else {
			postLogin(main_username.getText().toString(), main_password.getText().toString());
		}
	}

	private void inputAnimator(final View view, float w) {

		AnimatorSet set = new AnimatorSet();

		ValueAnimator animator = ValueAnimator.ofFloat(0, w);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				MarginLayoutParams params = (MarginLayoutParams) view
						.getLayoutParams();
				params.leftMargin = (int) value;
				params.rightMargin = (int) value;
				view.setLayoutParams(params);
			}
		});

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
				"scaleX", 1f, 0.5f);
		set.setDuration(1000);
		set.setInterpolator(new AccelerateDecelerateInterpolator());
		set.playTogether(animator, animator2);
		set.start();
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ToastUtil.showShortToast(MainActivity.this,"正在登陆");
					}
				});
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
//						ToastUtil.showShortToast(MainActivity.this,"访问服务器成功");
					}
				});

				//显示进度条
				progress.setVisibility(View.VISIBLE);
				//转起来
				progressAnimator(progress);
				//隐藏装用户名和密码的大框
				mInputLayout.setVisibility(View.INVISIBLE);

				//加个计时器来延时1秒
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
//								ToastUtil.showShortToast(MainActivity.this,"登陆成功");
								if (userType == 0) {
									startActivity(new Intent(MainActivity.this, AdminActivity.class));
									//结束这个Activity
								} else {
									startActivity(new Intent(MainActivity.this, TeacherActivity.class));
									//结束这个Activity
								}
								MainActivity.super.finish();
							}
						});
					}
				}, 1000);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void progressAnimator(final View view) {
		PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
				0.5f, 1f);
		PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
				0.5f, 1f);
		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
				animator, animator2);
		animator3.setDuration(1000);
		animator3.setInterpolator(new JellyInterpolator());
		animator3.start();
	}

	private void postLogin(String username, String password) {
		LoginForm loginForm = new LoginForm();
		loginForm.setUsername(username);
		loginForm.setPassword(password);
		loginForm.setUserType(userType);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(loginForm);
		//String转请求体
		MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
		RequestBody requestBody = RequestBody.Companion.create(json,mediaType);
		//实例化OkHttp的请求器
		OkHttpClient okHttpClient = new OkHttpClient();
		//实例化请求对象
		String url = UrlUtil.url + "/user/login";
		Request request = new Request.Builder()
				.url(url)
				.post(requestBody)
				.build();
		//把request请求对象传递给okHttpClient请求器，就可以得到一个准备好进行请求的Call对象
		Call call = okHttpClient.newCall(request);
		//异步请求，调用enqueue方法
		call.enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {//请求失败
				showMessage("网络异常！");
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {//请求结束，代表跟服务器通信成功，但不一定跟url通信成功，可能404
				if (response.isSuccessful()) {//如果响应是成功的，才进行操作
					String result = response.body().string();
					LoginInfo loginInfo = gson.fromJson(result, LoginInfo.class);
					//登录成功
					if (loginInfo.getCode() == 0) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								//隐藏输入的用户名和密码
								mName.setVisibility(View.INVISIBLE);
								mPsw.setVisibility(View.INVISIBLE);
								//保存用户信息
								MyApplication.teacherId = loginInfo.getUser().getUserId();
								MyApplication.realname = loginInfo.getUser().getRealname();
								showMessage(loginInfo.getMsg());
								//开始动画并跳转界面
								inputAnimator(mInputLayout, mWidth);
							}
						});
					}
					showMessage(loginInfo.getMsg());
				}
			}
		});
	}

	/**
	 * Toast出服务器返回的msg
	 *
	 * @param msg
	 */
	private void showMessage(String msg) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtil.showShortToast(MainActivity.this, msg);
			}
		});
	}
}
