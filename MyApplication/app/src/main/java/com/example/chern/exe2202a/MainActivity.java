package com.example.chern.exe2202a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText dir_1 = (EditText)findViewById(R.id.dir_1);
        final EditText dir_2 = (EditText)findViewById(R.id.dir_2);
        final EditText dir_3 = (EditText)findViewById(R.id.dir_3);

        final Button act_read = (Button) findViewById(R.id.act_read);
        final Button act_save = (Button) findViewById(R.id.act_save);


        act_read.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView output_text = (TextView)findViewById(R.id.output_box);
                output_text.setText("");

                Map<String, Map<String, String>> info = new HashMap<String, Map<String, String>>();
                info.put("files", generateInfo(getFilesDir()));
                info.put("cache", generateInfo(getCacheDir()));

                for(Map.Entry<String, Map<String, String>> entry : info.entrySet()){
                    String entry_key = entry.getKey();
                    Map<String, String> entry_value = entry.getValue();
                    output_text.setText(output_text.getText() +  ">:" + entry_key + "\n");

                    for(Map.Entry<String, String> type : entry_value.entrySet()) {
                        String key = type.getKey();
                        String value = type.getValue();
                        output_text.setText(output_text.getText() +  "   [" + key + "]:\n   " + value + "\n\n");
                    }

                    List<String> current_content;
                    switch (entry_key) {
                        case "files":
                            current_content = getFolderContent(getFilesDir());
                            break;
                        case "cache":
                            current_content = getFolderContent(getCacheDir());
                            break;
                        default:
                            current_content = null;
                            break;
                    }
                    if(current_content != null){
                        for(String content_name : current_content){
                            output_text.setText(output_text.getText() + content_name + "\n");
                        }
                    }
                }
            }
        });

        act_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView hint_warning = (TextView)findViewById(R.id.hint_warning);
                List list_text = new LinkedList();
                list_text.add(dir_1);
                list_text.add(dir_2);
                list_text.add(dir_3);
                for(Object field : list_text){
                    if (!((EditText) field).getText().toString().isEmpty()) {
                        String file_name = ((EditText) field).getText().toString();
                        // if has any restricted chars - remove them or replace
                        if(isRestrictedName(file_name)){
                            // Has restricted chars<>
                            Log.e("<<Form_reading>>", "Restricted name of the folder!");
                            hint_warning.setTextSize(getResources().getInteger(R.integer.default_font_size));
                            hint_warning.setText(getText(R.string.warning_restricted_name));
                            hint_warning.setTextColor(getColor(R.color.colorHintWarning));
                            ((EditText) field).setHintTextColor(getColor(R.color.colorHintWarning));
                            ((EditText) field).setTextColor(getColor(R.color.colorHintWarning));
                            ((EditText) field).requestFocus();
                            break;
                        }

                        File new_dir = new File(getFilesDir().getPath() + File.separator + file_name);
                        // if not exist
                        if (!new_dir.exists()){
                            // create folder
                            new_dir.mkdirs();
                        }else{
                            if(((EditText) field).getId() == R.id.dir_1) {
                                // Remove file
                                new_dir.delete();
                            }
                        }

                        // Clear textview
                        // Printout content
                        TextView output_box = (TextView)findViewById(R.id.output_box);
                        output_box.setText("");
                        List<String> current_content =  getFolderContent(getFilesDir());
                        for(String content_name : current_content){
                            output_box.setText(output_box.getText() + content_name + "\n");
                        }
                    } else {
                        Log.e("<<Form_reading>>", "Field cannot be empty!");
                        hint_warning.setTextSize(getResources().getInteger(R.integer.default_font_size));
                        hint_warning.setText(getText(R.string.warning_empty_field));
                        hint_warning.setTextColor(getColor(R.color.colorHintWarning));
                        ((EditText) field).setHintTextColor(getColor(R.color.colorHintWarning));
                        ((EditText) field).requestFocus();
                        break;
                    }
                }
            }

        });


        dir_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextView hint_warning = (TextView)findViewById(R.id.hint_warning);

                hint_warning.setTextSize(getResources().getInteger(R.integer.none_font_size));
                hint_warning.setText("");
                dir_1.setHintTextColor(getColor(R.color.colorDefaultFont));
                dir_1.setTextColor(getColor(R.color.colorDefaultFont));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dir_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextView hint_warning = (TextView)findViewById(R.id.hint_warning);

                hint_warning.setTextSize(getResources().getInteger(R.integer.none_font_size));
                hint_warning.setText("");
                dir_2.setHintTextColor(getColor(R.color.colorDefaultFont));
                dir_2.setTextColor(getColor(R.color.colorDefaultFont));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dir_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextView hint_warning = (TextView)findViewById(R.id.hint_warning);

                hint_warning.setTextSize(getResources().getInteger(R.integer.none_font_size));
                hint_warning.setText("");
                dir_3.setHintTextColor(getColor(R.color.colorDefaultFont));
                dir_3.setTextColor(getColor(R.color.colorDefaultFont));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private static boolean isRestrictedName(String s) {
        String restrictedCharacters = "/\\:*?\"<>|";
        for (char c : restrictedCharacters.toCharArray()) {
            if (s.indexOf(c) != -1) return true;
        }
        return false;
    }

    private List<String> getFolderContent(File dir){
        File[] files = dir.listFiles();
        if(files.length == 0){
            return null;
        }
        List<String> content = new LinkedList<>();
        for(File file : files){
            content.add(file.getName());
        }
        return content;
    }

    private Map<String, String> generateInfo(File dir){
        Map<String, String> temp = new HashMap<String, String>();

        try {
            temp.put("name",                dir.getName());
            temp.put("path",                dir.getPath());
            temp.put("absolute_path",       dir.getAbsolutePath());
            temp.put("canonical_path",      dir.getCanonicalPath());
            temp.put("parent_directory",    dir.getParentFile().getName());
            temp.put("total_space",         Long.toString(dir.getTotalSpace()));
            temp.put("free_space",          Long.toString(dir.getFreeSpace()));
            temp.put("usable_space",        Long.toString(dir.getUsableSpace()));
        } catch (IOException e) {
            Log.w("<generateInfo()> :", e.toString());
            return null;
        }

        return temp;
    }
}
