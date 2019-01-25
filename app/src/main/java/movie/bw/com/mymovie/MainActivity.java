package movie.bw.com.mymovie;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mTextView;

    private WeakReference<MainActivity> activityWeakReference;
    private MyHandler myHandler;

    static class MyHandler extends Handler {

        // 定义一个activity的引用变量
        private MainActivity activity;

        // 传入一个弱引用的activity对象
        public MyHandler(WeakReference<MainActivity> activity) {
            // 使用弱引用对象的get()方法可以获得activity对象，赋值给activity
            this.activity = activity.get();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // 需要做判空操作
                    if (activity != null) {
                        activity.mTextView.setText("new Value");
                    }
                    break;
                default:
                    Log.i(TAG, "handleMessage: default");
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityWeakReference = new WeakReference<MainActivity>(this);
        myHandler = new MyHandler(activityWeakReference);

        myHandler.sendEmptyMessage(1);
        mTextView = findViewById(R.id.tv_test);
    }
}
