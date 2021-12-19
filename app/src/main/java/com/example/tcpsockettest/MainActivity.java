package com.example.tcpsockettest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    // declaring required variables
    private Socket client;
    private PrintWriter printwriter;
    private EditText editText;
    private TextView textView;
    private Button button;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference to the text field
        editText = findViewById(R.id.editText1);
        textView = findViewById(R.id.textView);

        // reference to the send button
        button = findViewById(R.id.button1);

        // Button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // get the text message on the text field
                message = editText.getText().toString();

                // start the Thread to connect to server
                Log.d(TAG, "onClick: reached here1");
                new Thread(new ClientThread(message)).start();
                //new Thread(new receive()).start();

            }
        });
    }

//     the ClientThread class performs
//     the networking operations
    class ClientThread implements Runnable {
        private final String message;

        ClientThread(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            try {
                // the IP and port should be correct to have a connection established
                // Creates a stream socket and connects it to the specified port number on the named host.
                Log.d(TAG, "onClick: reached here2");
                InetAddress serverAddr = InetAddress.getByName("192.168.1.39");
                Log.d(TAG, "onClick: reached here3");
                client = new Socket(serverAddr, 4444); // connect to server
                Log.d(TAG, "onClick: reached here4");
                printwriter = new PrintWriter(client.getOutputStream(),true);
                printwriter.write(message);
//                while(true){
//                    // write the message to output stream
//                    if(message == "Over"){
//                        break;
//                    }else{
//                        printwriter.write(message);
//                        printwriter = new PrintWriter(client.getOutputStream(),true);
//                        message=editText.getText().toString();
//                    }
//                }
                printwriter.flush();
                printwriter.close();

                // closing the connection
                //client.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //InputStreamReader input = null;

                }
            });
        }
    }

}
