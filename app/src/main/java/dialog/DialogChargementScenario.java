package dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.i162174.robot.R;

import java.io.File;

import activity.ScenarioActivity;

public class DialogChargementScenario extends Dialog {

    public DialogChargementScenario(@NonNull final ScenarioActivity context, File[] pFiles) {
        super(context);

        setContentView(R.layout.dialog_chargement_scenario);
        setTitle("Listes des sauvegardes");

        ListView listViewSauvegarde = (ListView) findViewById(R.id.listViewSauvegarde);
        File[] files = pFiles;
        final String[] filesName = new String[files.length];
        for(int i = 0; i < files.length; i++)
            filesName[i] = files[i].getName();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, filesName);
        listViewSauvegarde.setAdapter(adapter);

        listViewSauvegarde.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                context.load(filesName[i]);
                dismiss();
            }
        });
    }
}
