package com.twopark1jo.lobster.translation;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PapagoAPI {
    @Value("${NAVER_CLIENT_ID}")
    public String NAVER_CLIENT_ID;
    @Value("${NAVER_CLIENT_SECRET}")
    public String NAVER_CLIENT_SECRET;

    @GetMapping("/translation")
    public ResponseEntity<String> translate(@RequestBody TranslationForm translationForm){
        System.out.println("RequestBody: " + translationForm.toString());
        System.out.println("key: " + NAVER_CLIENT_ID + "/" + NAVER_CLIENT_SECRET);
        String translatedText = run(translationForm);
        try {
            return new ResponseEntity<String>(translatedText, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<String>("오류가 발생했습니다", HttpStatus.BAD_REQUEST);
        }
    }

    public String run(TranslationForm translationForm){
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

        try {
            translationForm.setText(URLEncoder.encode(translationForm.getText(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", NAVER_CLIENT_ID);
        requestHeaders.put("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        String responseBody = post(apiURL, requestHeaders, translationForm);

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
            JSONObject jsonMessage = (JSONObject) jsonObject.get("message");
            JSONObject jsonResult = (JSONObject) jsonMessage.get("result");
            String translatedText = (String) jsonResult.get("translatedText");

            return translatedText;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String post(String apiUrl, Map<String, String> requestHeaders, TranslationForm translationForm){
        HttpURLConnection con = connect(apiUrl);
        Formatter formatter = new Formatter();

        formatter.format("source=%s&target=%s&text=%s",
                translationForm.getSource(), translationForm.getTarget(), translationForm.getText());

        String postParams = formatter.toString();

        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
