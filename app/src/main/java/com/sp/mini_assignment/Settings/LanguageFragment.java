package com.sp.mini_assignment.Settings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sp.mini_assignment.R;

import java.util.Locale;

public class LanguageFragment extends Fragment {
    Spinner spinner;
    TextView languageText, languageText1, languageText2, languageText3;

    public static final String[] languages = {"Select Language", "English", "Chinese", "Tamil", "Malay"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_language, container, false);
        spinner = rootView.findViewById(R.id.spinner);
        languageText = rootView.findViewById(R.id.language_text);
        languageText1 = rootView.findViewById(R.id.language_text1);
        languageText2 = rootView.findViewById(R.id.language_text2);
        languageText3 = rootView.findViewById(R.id.language_text3);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = parent.getItemAtPosition(position).toString();

                if (selectedLang.equals("English")) {
                    setLocal(getActivity(), "en");
                    updateLanguageText("en");
                } else if (selectedLang.equals("Chinese")) {
                    setLocal(getActivity(), "chi");
                    updateLanguageText("chi");
                } else if (selectedLang.equals("Tamil")) {
                    setLocal(getActivity(), "ta");
                    updateLanguageText("ta");
                } else if (selectedLang.equals("Malay")) {
                    setLocal(getActivity(), "ms");
                    updateLanguageText("ms");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    private void updateLanguageText(String langCode) {
        // Manually set the translated text based on the selected language
        switch (langCode) {
            case "en":
                languageText.setText("Language Setting");
                languageText1.setText("This is just a sample text to show this feature");
                languageText2.setText("Please do not think too much about these texts haha");
                languageText3.setText("Honestly, I do not know what I'm typing right now");
                break;
            case "chi":
                languageText.setText("语言设置");
                languageText1.setText("这只是一个展示此功能的示例文本");
                languageText2.setText("请不要对这些文本想得太多哈哈");
                languageText3.setText("老实说，我现在不知道在打什么");
                break;
            case "ta":
                languageText.setText("மொழி அமைப்பு");
                languageText1.setText("இந்த அம்சமைக்க ஒரு மாதிரி உரையாடல்");
                languageText2.setText("இந்த உரைகளை நினைவில் உள்ளதாக இருக்காதே பயன்படுத்துங்கள் ஹாஹா");
                languageText3.setText("பேசிச்சு என்ன எழுதிச்சு தெரியுது எனக்கு அப்போ இல்லை");
                break;
            case "ms":
                languageText.setText("Tetapan Bahasa");
                languageText1.setText("Ini hanyalah teks contoh untuk menunjukkan ciri ini");
                languageText2.setText("Sila jangan terlalu fikir tentang teks-teks ini haha");
                languageText3.setText("Jujur, saya tidak tahu apa yang saya taip sekarang");
                break;
            // Add more cases for other languages if needed
        }
    }

    public void setLocal(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
