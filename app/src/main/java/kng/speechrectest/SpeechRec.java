package kng.speechrectest;
import android.content.Context;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.ArrayList;

/**
 * Created by masashi on 16/07/05.
 */


public class SpeechRec implements RecognitionListener{
    private SpeechRecognizer mSpeechRec;
    private Context mContext;
    private SpeechRecCallbacks mSpeechRecCallbacks;

    public interface  SpeechRecCallbacks {
        void startedSpeechRec();
        void stoppedSpeechRec();
        void startedListening();
        void stoppedListening(boolean isSuccess, String resultText);
        void error(String errorStr);
    }

    //コンストラクタ
    public SpeechRec(Context con){
        mContext = con;
    }
    //音声認識を開始
    public void startRec(){
        startListening();
    }
    //音声認識を終了
    public void stopRec() {
        stopListening();
    }
    // 音声認識を開始する
    public void startListening() {
        try {
            if (mSpeechRec == null) {
                mSpeechRec = SpeechRecognizer.createSpeechRecognizer(mContext);
                if (!SpeechRecognizer.isRecognitionAvailable(mContext)) {
                    mSpeechRecCallbacks.error("failed to start");
                }
            }
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            mSpeechRec.startListening(intent);
        } catch (Exception ex) {
            //音声認識開始が失敗
            mSpeechRecCallbacks.error("failed to start");
        }
    }
    // 音声認識を終了する
    public void stopListening() {
        if (mSpeechRec != null) mSpeechRec.destroy();
        mSpeechRec = null;
    }
    // 音声認識を再開する
    public void restartListeningService() {
        stopListening();
        startListening();
    }

    public void onBeginningOfSpeech() {
    }
    public void onBufferReceived(byte[] buffer) {
    }
    public void onEndOfSpeech() {
    }
    public void onError(int error) {
        String reason = "";
        switch (error) {
            // Audio recording error
            case SpeechRecognizer.ERROR_AUDIO:
                reason = "ERROR_AUDIO";
                break;
            // Other client side errors
            case SpeechRecognizer.ERROR_CLIENT:
                reason = "ERROR_CLIENT";
                break;
            // Insufficient permissions
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                reason = "ERROR_INSUFFICIENT_PERMISSIONS";
                break;
            // 	Other network related errors
            case SpeechRecognizer.ERROR_NETWORK:
                reason = "ERROR_NETWORK";
                    /* ネットワーク接続をチェックする処理をここに入れる */
                break;
            // Network operation timed out
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                reason = "ERROR_NETWORK_TIMEOUT";
                break;
            // No recognition result matched
            case SpeechRecognizer.ERROR_NO_MATCH:
                reason = "ERROR_NO_MATCH";
                break;
            // RecognitionService busy
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                reason = "ERROR_RECOGNIZER_BUSY";
                break;
            // Server sends error status
            case SpeechRecognizer.ERROR_SERVER:
                reason = "ERROR_SERVER";
                    /* ネットワーク接続をチェックをする処理をここに入れる */
                break;
            // No speech input
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                reason = "ERROR_SPEECH_TIMEOUT";
                break;
        }
        mSpeechRecCallbacks.error(reason);
        restartListeningService();
    }
    public void onEvent(int eventType, Bundle params) {
    }
    public void onPartialResults(Bundle partialResults) {
    }
    public void onReadyForSpeech(Bundle params) {
        Toast.makeText(mContext, "onRedyForSpeech",
                Toast.LENGTH_SHORT).show();
    }
    public void onResults(Bundle results) {
        ArrayList results_array = results.getStringArrayList(
                SpeechRecognizer.RESULTS_RECOGNITION);
        String resultsString = "";
        for (int i = 0; i < results.size(); i++) {
            resultsString += results_array.get(i) + ";";
        }
        Toast.makeText(mContext, resultsString, Toast.LENGTH_LONG).show();
        restartListeningService();
        mSpeechRecCallbacks.stoppedListening(true, resultsString);
    }
    public void onRmsChanged(float rmsdB) {
    }
}
