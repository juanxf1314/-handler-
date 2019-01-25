package movie.bw.com.mymovie;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
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

    /**
     * LruCache的使用方法如下：
     */
    public class BitmapLruCache extends LruCache<String, Bitmap> {

        // 设置缓存大小，建议当前应用可用最大内存的八分之一，
        // 即(int)(Runtime.getRuntime().maxMemory()/1024/8)
        public BitmapLruCache(int maxSize) {
            super(maxSize);
        }

        // 计算的方法，需当前节点的内存大小，这要重写，不然返回1
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return super.sizeOf(key, value);
        }

        // 当节点移除时该方法会回调，可根据需求组来决定是否重写该方法
        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
        }
    }
}
