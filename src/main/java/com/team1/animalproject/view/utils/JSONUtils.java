package com.team1.animalproject.view.utils;

import com.team1.animalproject.auth.Constants;
import lombok.Data;
import org.primefaces.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Data
public class JSONUtils {

    private Map<String, String> cities;

    private Map<String, Map<String, String>> data = new HashMap<>();

    public void jsonParse() {

        cities = new HashMap<>();
        data = new HashMap<>();

        String cityJsonStr = loadJSON();

        org.primefaces.json.JSONArray jsonArray;

        JSONObject jsonObject = new JSONObject(cityJsonStr);
        jsonArray = jsonObject.getJSONArray("cities");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = (JSONObject) jsonArray.get(i);
            String sehiradi = o.getString("il");
            cities.put(sehiradi, sehiradi);
            org.primefaces.json.JSONArray ilceler = o.getJSONArray("ilceleri");
            Map<String, String> map = new HashMap<>();
            for (int k = 0; k < ilceler.length(); k++) {
                map.put(ilceler.getString(k), ilceler.getString(k));
            }
            data.put(sehiradi, map);

        }
    }

    public String loadJSON() {
        String json;
        try {
            InputStream is = new FileInputStream(new File(Constants.FILE_PATH + "cities.json"));

            int size = is.available();

            byte[] buffer = new byte[size];

            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);

            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
