package adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i162174.robot.R;

import java.util.ArrayList;

import activity.ScenarioActivity;

public class AdapterFichier extends BaseAdapter{

    private ArrayList<String> data;
    ScenarioActivity context;

    public AdapterFichier(ScenarioActivity pContext, ArrayList<String> pData) {
        this.data = pData;
        this.context= pContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item_listview_scenario, null);
        TextView txt = (TextView) v.findViewById(R.id.txt_list);
        txt.setText(data.get(i));

        ImageView imgTrash = (ImageView) v.findViewById(R.id.img_trash);
        imgTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteFile(data.get(i));
                Toast.makeText(context, "Scénario supprimé !", Toast.LENGTH_SHORT).show();
                data.remove(i);
                notifyDataSetChanged();
            }
        });
        v.setTag(i);
        return v;
    }

}