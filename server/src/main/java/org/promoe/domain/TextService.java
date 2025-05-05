package org.promoe.domain;

import org.promoe.model.OpenAIResponse;
import org.promoe.model.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;

@Service
public class TextService {
    @Value("OPENAI_API_KEY")
    private final String API_KEY = "";
    @Value("MODEL")
    private final String MODEL = "";
    @Value("API_URL")
    private final String API_URL = "";

    OkHttpClient client = new OkHttpClient();

    Text text = new Text();

    public Result<Text> getText(String prompt) {
        Result<Text> result = validate(prompt);
        if (!result.isSuccess()) {
            return result;
        }

        String requestBodyJson = "{\"model\": \"" + MODEL + "\", \"prompt\": \"" + prompt + "\", \"max_tokens\": 100}";
        RequestBody body = RequestBody.create(MediaType.get("application/json"), requestBodyJson);
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .header("Authorization", "Bearer " + API_KEY)
                .build();
        OpenAIResponse openAIResponse;
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            Gson gson = new Gson();
            openAIResponse = gson.fromJson(response.body().string(), OpenAIResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        text.setText(openAIResponse.getChoices().get(0).getText());
        result.setPayload(text);
        return result;
    }

    private Result<Text> validate(String text) {
        Result<Text> result = new Result<>();
        if (text == null) {
            result.addMessage("Card can not be null", ResultType.INVALID);
            return result;
        }
        return result;
    }


}
