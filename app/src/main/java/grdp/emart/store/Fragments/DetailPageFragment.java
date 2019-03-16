package grdp.emart.store.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import grdp.emart.store.Activities.OptionalImageFullView;
import grdp.emart.store.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailPageFragment extends Fragment {
    public static DetailPageFragment newInstance(int position, ArrayList<String> imagesList) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putStringArrayList("imageList", imagesList);
        DetailPageFragment fragment = new DetailPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final int position = getArguments().getInt("position");
        final List<String> imagesList = getArguments().getStringArrayList("imageList");
        int layout = grdp.emart.store.R.layout.fragment_page_one;
        View root = inflater.inflate(layout, container, false);
        root.setTag(position);
        ImageView image_one = (ImageView) root.findViewById(grdp.emart.store.R.id.image_one);
        Picasso.with(getActivity())
                .load(imagesList.get(position))
                .placeholder(grdp.emart.store.R.drawable.defaultimage)
                .resize(Integer.parseInt(getResources().getString(grdp.emart.store.R.string.targetProductImageWidth)),Integer.parseInt(getResources().getString(grdp.emart.store.R.string.targetProductImageWidth)))
                .error(grdp.emart.store.R.drawable.defaultimage)
                .into(image_one);
        image_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullSize(position, imagesList);

            }
        });
        return root;
    }

    private void showFullSize(int i, List<String> imagesList) {
        OptionalImageFullView.imagesList = imagesList;
        OptionalImageFullView.currentPos = i;
        Intent intent = new Intent(getActivity(), OptionalImageFullView.class);
        startActivity(intent);
    }
}
