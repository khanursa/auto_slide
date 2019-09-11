package learn.kotlin.autoslide;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rd.PageIndicatorView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    PageIndicatorView pageIndicatorView;
    ViewPager pager;
    Timer timer;
    private Handler mHandler;
    int currentPage = 0;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.

    int[] blackPink = {R.drawable.bp1, R.drawable.bp2, R.drawable.bp3};

    Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.viewPagerBanner);
        pageIndicatorView = findViewById(R.id.indicator);
        pageIndicatorView.setCount(blackPink.length);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mHandler = new Handler();
        mRunnable = new Runnable()
        {

            @Override
            public void run()
            {
                //TODO: do something like mViewPager.setCurrentPage(mIterator);
                if (currentPage > blackPink.length) {
                    currentPage = 0;
                }
                pageIndicatorView.setSelection(currentPage++);
                pager.setCurrentItem(pageIndicatorView.getSelection(), true);
                currentPage = currentPage++;
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                mHandler.post(mRunnable);
            }
        }, DELAY_MS, PERIOD_MS);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                if(currentPage != position){
                    currentPage = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return FragmentViewPager.newInstance(blackPink[0]);
                case 1:
                    return FragmentViewPager.newInstance(blackPink[1]);
                case 2:
                    return FragmentViewPager.newInstance(blackPink[2]);
                default:
                    return FragmentViewPager.newInstance(blackPink[0]);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}