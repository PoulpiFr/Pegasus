package fr.poulpi.pegasus.fragments;



import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.poulpi.pegasus.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MetroMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MetroMapFragment extends Fragment {
    private Bitmap planBitmap;
    private Matrix imageMatrix;
    private ImageViewTouch mImage;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MetroMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MetroMapFragment newInstance() {
        MetroMapFragment fragment = new MetroMapFragment();
        return fragment;
    }
    public MetroMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_metro_map, container, false);

        planBitmap = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.plan_metro);
        //planBitmap =Bitmap.createScaledBitmap(planBitmap, 2048, 2048, true);
        imageMatrix = new Matrix();
        //mImage = new ImageViewTouch(context);
        mImage = (ImageViewTouch) view.findViewById(R.id.planMetro);

        mImage.setImageBitmap( planBitmap, imageMatrix, 1, 6);



        // Inflate the layout for this fragment
        return view;
    }


}
