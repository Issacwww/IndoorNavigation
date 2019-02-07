package com.issac.indoor_navigation.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.issac.indoor_navigation.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Issac on 2017/7/17.
 */

public class SelectFragment extends Fragment {
    Button tourist,vip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select,container,false);
        tourist=(Button)view.findViewById(R.id.tourist);
        vip=(Button)view.findViewById(R.id.vip);
        tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.mipmap.ic_user_clk);
                vip.setBackgroundResource(R.mipmap.ic_vip);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userType",MODE_PRIVATE).edit();
                editor.putInt("type",1);
                editor.apply();
            }
        });
        vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.mipmap.ic_vip_clk);
                tourist.setBackgroundResource(R.mipmap.ic_user);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userType", MODE_PRIVATE).edit();
                editor.putInt("type",2);
                editor.apply();
            }
        });

        return  view;
    }
//    private void  save(String choice){
//        FileOutputStream out = null;
//        BufferedWriter writer = null;
//        try{
//            out = getActivity().openFileOutput("userType", Context.MODE_PRIVATE);
//            writer = new BufferedWriter(new OutputStreamWriter(out));
//            writer.write(choice);
//        }catch (IOException IOE){
//            IOE.printStackTrace();
//        }finally {
//            try{
//                if(writer != null)
//                    writer.close();
//            }catch (IOException IOE){
//                IOE.printStackTrace();
//            }
//        }
//    }
//
//    private String load(){
//        FileInputStream in = null;
//        BufferedReader reader = null;
//        StringBuilder content = new StringBuilder();
//        try{
//            in = getActivity().openFileInput("userType");
//            reader = new BufferedReader(new InputStreamReader(in));
//            String str = "";
//            while ((str = reader.readLine()) != null)
//                content.append(str);
//        }catch (IOException IOE){
//            IOE.printStackTrace();
//        }finally {
//            try{
//                if(reader != null)
//                    reader.close();
//            }catch (IOException IOE){
//                IOE.printStackTrace();
//            }
//        }
//        return content.toString();
//    }

}
