package grdp.emart.store.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import grdp.emart.store.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionalImageFullView extends AppCompatActivity {
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    public static int currentPos = 0;
    public static List<String> imagesList;
    ImageView imageView;
    @BindView(grdp.emart.store.R.id.back)
    ImageView back;
    @BindView(grdp.emart.store.R.id.imageText)
    TextView imageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(grdp.emart.store.R.layout.activity_optional_image_full_view);
        ButterKnife.bind(this);
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(grdp.emart.store.R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(currentPos);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageText.setText("Image " + (position + 1) + " of " + imagesList.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageText.setText("Image " + (currentPos + 1) + " of " + imagesList.size());
    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagesList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(grdp.emart.store.R.layout.pager_item, container, false);

            imageView = (ImageView) itemView.findViewById(grdp.emart.store.R.id.imageView);
            Picasso.with(mContext)
                    .load(imagesList.get(position))
                    .placeholder(grdp.emart.store.R.drawable.defaultimage)
                    .resize(Integer.parseInt(getResources().getString(grdp.emart.store.R.string.fullImageWidth)),Integer.parseInt(getResources().getString(grdp.emart.store.R.string.fullImageWidth)))
                    .error(grdp.emart.store.R.drawable.defaultimage)
                    .into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

}
