package com.tremend.testtremend.UI;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tremend.testtremend.Adapters.BrowserListAdapter;
import com.tremend.testtremend.Services.BrowseService;
import com.tremend.testtremend.Models.ModelBrowse;
import com.tremend.testtremend.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    public final static String PATH = "pathToView";
    ListView listView;
    private BrowseService mService;
    private boolean mBound;
    private String mPath;


    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mPath = new String();
        if (args != null) {
            mPath = args.getString(PATH, "");
        }
        if (mPath.isEmpty()) {
            mPath = Environment.getRootDirectory().toString();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        listView = (ListView) view.findViewById(R.id.item_list);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(getActivity(), BrowseService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BrowseService.MyBinder binder = (BrowseService.MyBinder) service;
            mService = binder.getService();
            File file = new File(mPath);
            mService.getFiles(mPath, file.getTotalSpace() ,new BrowseService.Results() {
            @Override
            public void onResults(final List<ModelBrowse> files) {
                if(getActivity()==null){
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Display display = getActivity().getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int height = size.y;
                        final BrowserListAdapter adapter = new BrowserListAdapter(getContext(), R.layout.browse_list_item_layout, (ArrayList<ModelBrowse>) files,height);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String filePath = adapter.getItem(position).completeFilePath;
                                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                                Fragment fragment = new BrowseFragment();
                                Bundle args = new Bundle();
                                args.putString(PATH, filePath);
                                fragment.setArguments(args);
                                fm.beginTransaction()
                                        .replace(R.id.main_container, fragment, BrowseFragment.class.getCanonicalName()).addToBackStack("")
                                        .commit();
                            }
                        });
                    }
                });
            }

                @Override
                public void onStarted() {
                    //show some kind of visual info
                }
            });
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


}
