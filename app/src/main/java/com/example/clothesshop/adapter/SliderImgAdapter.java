package com.example.clothesshop.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.clothesshop.R;
import com.example.clothesshop.model.ProductDetailsGalleryModel;
import com.example.clothesshop.model.SliderModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderImgAdapter extends PagerAdapter {
    private List<ProductDetailsGalleryModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
    private String product_image;


    public SliderImgAdapter(Context context, List<ProductDetailsGalleryModel> productDetailsGalleryModelList, String image) {

        this.context = context;
        this.imageModelArrayList = productDetailsGalleryModelList;
        inflater = LayoutInflater.from(context);
        this.product_image = image;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.image_item, view, false);

        ProductDetailsGalleryModel productDetailsGalleryModel  = imageModelArrayList.get(position);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        if (productDetailsGalleryModel.getId().equals("0"))
        {
            Picasso.with(context)
                    .load(context.getResources().getString(R.string.product_image_url) + imageModelArrayList.get(position).getImage())
                    .placeholder(R.drawable.coming)
                    .error(R.drawable.coming)
                    .into(imageView);
        }
        else
        {
            Picasso.with(context)
                    .load(context.getResources().getString(R.string.product_gallery_images) + imageModelArrayList.get(position).getImage())
                    .placeholder(R.drawable.coming)
                    .error(R.drawable.coming)
                    .into(imageView);
        }

     //   imageView.setImageResource(imageModelArrayList.get(position).getImageDrawable());

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
