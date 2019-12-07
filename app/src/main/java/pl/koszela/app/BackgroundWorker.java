package pl.koszela.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    private final MyCallback myCallback;

    public BackgroundWorker(Context ctx, MyCallback myCallback) {
        this.context = ctx;
        this.myCallback = myCallback;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://begginerwebsite.000webhostapp.com/login.php";
        String save_customer_url = "http://begginerwebsite.000webhostapp.com/add_recommend_customer.php";
        String register_user_url = "http://begginerwebsite.000webhostapp.com/register_user.php";
        if (type.equals("login")) {
            try {
                String user_name = params[1];
                String password = params[2];
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                return createSQLQuery(login_url, post_data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("save customer")) {
            try {
                String name = params[1];
                String phone = params[2];
                String selectOption = params[3];
                int checked1 = Boolean.parseBoolean(params[4]) ? 1 : 0;
                int checked2 = Boolean.parseBoolean(params[5]) ? 1 : 0;
                int checked3 = Boolean.parseBoolean(params[6]) ? 1 : 0;
                int checked4 = Boolean.parseBoolean(params[7]) ? 1 : 0;
                Integer number = Integer.valueOf(params[8]);
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("select_option", "UTF-8") + "=" + URLEncoder.encode(selectOption, "UTF-8") + "&"
                        + URLEncoder.encode("checked1", "UTF-8") + "=" + checked1 + "&"
                        + URLEncoder.encode("checked2", "UTF-8") + "=" + checked2 + "&"
                        + URLEncoder.encode("checked3", "UTF-8") + "=" + checked3 + "&"
                        + URLEncoder.encode("checked4", "UTF-8") + "=" + checked4 + "&"
                        + URLEncoder.encode("number", "UTF-8") + "=" + number;
                return createSQLQuery(save_customer_url, post_data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("register user")) {
            try {
                String name = params[1];
                String surname = params[2];
                String email = params[3];
                String phone = params[4];
                String username = params[5];
                String password = params[6];
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                return createSQLQuery(register_user_url, post_data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String createSQLQuery(String site_url, String post_data) throws IOException {
        URL url = new URL(site_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
        String result = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();

        return result;
    }

    @Override
    protected void onPreExecute() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.contains("login succes")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            myCallback.onResult(result);
//            context.startActivity(new Intent(context, SaveCustomer.class));
        } else if (result.equals("login failed")) {
            Toast.makeText(context, "Login failed. Please registered", Toast.LENGTH_LONG).show();
        } else if (result.contains("recommend customer")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        } else if (result.contains("register user")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
