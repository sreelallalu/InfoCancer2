package info.Fragment.Infocancer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.Fragment.Prevension.BackFragment;
import infocancer.nyesteventure.com.infocancer.R;


/**
 * Created by SLR on 9/19/2016.
 */
public class InfoDiagnosed extends Fragment implements BackFragment {


      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.info_diagnosed, container, false);
            return rootView;
            }

      @Override
      public boolean onBackPressed() {
            return true;
      }

      @Override
      public int getBackPriority() {
            return 0;
      }
}
