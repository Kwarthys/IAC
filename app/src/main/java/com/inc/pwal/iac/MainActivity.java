package com.inc.pwal.iac;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "IAC";

    public static Day monday;
    public static Day tuesday;
    public static Day wednesday;
    public static Day thursday;
    public static Day friday;
    public static Day saturday;
    public static Day sunday;
    private final String DAY_CLICKED = null;
    private static ArrayList<Day> week1 = new ArrayList<>();
    public static ArrayList<Rituel> listRituels = new ArrayList<>();

    private EDT edt = new EDT();
    //-----------------------------------------------------------------------BLUETOOTH--------------
    private String toLaunch ="";
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectionThread mBluetoothConnection = null;
    private String data;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int SELECT_SERVER = 1;
    public static final int DATA_RECEIVED = 3;
    public static final int SOCKET_CONNECTED = 4;
    public static final UUID APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SOCKET_CONNECTED: {
                    mBluetoothConnection = (ConnectionThread) msg.obj;
                    System.out.println(toLaunch);
                    mBluetoothConnection.write(toLaunch.getBytes());
                    break;
                }
                case DATA_RECEIVED: {
                    data = (String) msg.obj;
                    tost(data);
                }
                default:
                    break;
            }
        }
    };

    protected Runnable envoiBT = new Runnable() {
        @Override
        public void run() {
            mBluetoothConnection.write(toLaunch.getBytes());
            Log.d(TAG, "Envoi effectué" + toLaunch);
        }
    };
    //-------------------------------------------------------------------FIN-BLUETOOTH--------------



    private void CreateInterface(){
        Button buttonMonday = (Button) findViewById(R.id.buttonMonday);
        if (buttonMonday != null) {
            buttonMonday.setText(monday.getName() + " " + monday.getAlarmHour().getHours() + ":" + monday.getAlarmHour().getMinutes());
            buttonMonday.setOnClickListener(MainActivity.this);
        }

        Button buttonTuesday = (Button) findViewById(R.id.buttonTuesday);
        if (buttonTuesday != null) {
            buttonTuesday.setText(tuesday.getName() + " " + tuesday.getAlarmHour().getHours() + ":" + tuesday.getAlarmHour().getMinutes());
            buttonTuesday.setOnClickListener(MainActivity.this);
        }

        Button buttonWednesday = (Button) findViewById(R.id.buttonWednesday);
        if (buttonWednesday != null) {
            buttonWednesday.setText(wednesday.getName() + " " + wednesday.getAlarmHour().getHours() + ":" + wednesday.getAlarmHour().getMinutes());
            buttonWednesday.setOnClickListener(MainActivity.this);
        }

        Button buttonThursday = (Button) findViewById(R.id.buttonThursday);
        if (buttonThursday != null) {
            buttonThursday.setText(thursday.getName() + " " + thursday.getAlarmHour().getHours() + ":" + thursday.getAlarmHour().getMinutes());
            buttonThursday.setOnClickListener(MainActivity.this);
        }

        Button buttonFriday = (Button) findViewById(R.id.buttonFriday);
        if (buttonFriday != null) {
            buttonFriday.setText(friday.getName() + " " + friday.getAlarmHour().getHours() + ":" + friday.getAlarmHour().getMinutes());
            buttonFriday.setOnClickListener(MainActivity.this);
        }

        Button buttonSaturday = (Button) findViewById(R.id.buttonSaturday);
        if (buttonSaturday != null) {
            buttonSaturday.setText(saturday.getName() + " " + saturday.getAlarmHour().getHours() + ":" + saturday.getAlarmHour().getMinutes());
            buttonSaturday.setOnClickListener(MainActivity.this);
        }

        Button buttonSunday = (Button) findViewById(R.id.buttonSunday);
        if (buttonSunday != null) {
            buttonSunday.setText(sunday.getName() + " " + sunday.getAlarmHour().getHours() + ":" + sunday.getAlarmHour().getMinutes());
            buttonSunday.setOnClickListener(MainActivity.this);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setDefaultDay (){
        monday = new Day(1, "Lundi", new Hour(12, 0),(Button)findViewById(R.id.buttonMonday));
        tuesday = new Day(1, "Mardi", new Hour(12, 0),(Button)findViewById(R.id.buttonTuesday));
        wednesday = new Day(1, "Mercredi", new Hour(12, 0),(Button)findViewById(R.id.buttonWednesday));
        thursday = new Day(1, "Jeudi", new Hour(12, 0),(Button)findViewById(R.id.buttonThursday));
        friday = new Day(1, "Vendredi", new Hour(12, 0),(Button)findViewById(R.id.buttonFriday));
        saturday = new Day(1, "Samedi", new Hour(12, 0),(Button)findViewById(R.id.buttonSaturday));
        sunday = new Day(1, "Dimanche", new Hour(12, 0),(Button)findViewById(R.id.buttonSunday));

        week1.add(monday);
        week1.add(tuesday);
        week1.add(wednesday);
        week1.add(thursday);
        week1.add(friday);
        week1.add(saturday);
        week1.add(sunday);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // Rituel douche = new Rituel(1,"douche",15,0,"pwal");

        bluetooth();

        this.setDefaultDay();

        super.onCreate(savedInstanceState);

        int view = R.layout.activity_main;
        setContentView(view);

        this.CreateInterface();

        //launchEDT();//Download bypass'd


    }

    private void launchEDT()
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                edt = new EDT();
                try {
                    String lastring = edt.makeEDT();
                    System.out.println("Fin du Process EDT avec Signal : " + lastring);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static void updateInterface() {
        for (Day d : week1){
            d.calculateAlarmHour();
            d.getButton().setText(d.getName() + " " + d.getAlarmHour().getHours() + ":" + d.getAlarmHour().getMinutes());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DaySettingsActivity.class);
        if (v == findViewById(R.id.buttonMonday)) {

            toLaunch = "LUNDI";
            envoiBT.run();
            intent.putExtra(DAY_CLICKED, monday.getName());
        }
        else if (v == findViewById(R.id.buttonTuesday)) intent.putExtra(DAY_CLICKED, tuesday.getName());
        else if (v == findViewById(R.id.buttonWednesday)) intent.putExtra(DAY_CLICKED, wednesday.getName());
        else if (v == findViewById(R.id.buttonThursday)) intent.putExtra(DAY_CLICKED, thursday.getName());
        else if (v == findViewById(R.id.buttonFriday)) intent.putExtra(DAY_CLICKED, friday.getName());
        else if (v == findViewById(R.id.buttonSaturday)) intent.putExtra(DAY_CLICKED, saturday.getName());
        else if (v == findViewById(R.id.buttonSunday)) intent.putExtra(DAY_CLICKED, sunday.getName());
        startActivity(intent);
    }

    public static Day getDayPressed() {
        Day d = null;
        switch (DaySettingsActivity.getDayClicked()) {
            case "Lundi":
                d = monday;
                break;
            case "Mardi":
                d = tuesday;
                break;
            case "Mercredi":
                d = wednesday;
                break;
            case "Jeudi":
                d = thursday;
                break;
            case "Vendredi":
                d = friday;
                break;
            case "Samedi":
                d = saturday;
                break;
            case "Dimanche":
                d = sunday;
                break;
        }
        return d;
    }

    public void tost(String toToast)
    {
        Toast.makeText(MainActivity.this, toToast, Toast.LENGTH_SHORT).show();
    }

    //-------------------------------BLUETOOTH------------------------------------------------------

    private void bluetooth()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.i(TAG, "Bluetooth not supported");
            finish();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        }

        //connectToBluetoothServer(device.getAddress());
        // new ConnectThread(id, mHandler).start();
        selectServer();
    }

    private void selectServer() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList<String> pairedDeviceStrings = new ArrayList<String>();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDeviceStrings.add(device.getName() + "\n"
                        + device.getAddress());
            }
        }
        Intent showDevicesIntent = new Intent(this, ShowDevices.class);
        showDevicesIntent.putStringArrayListExtra("devices", pairedDeviceStrings);
        startActivityForResult(showDevicesIntent, SELECT_SERVER);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_SERVER && resultCode == RESULT_OK) {
            BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            connectToBluetoothServer(device.getAddress());
        }
    }
    private void connectToBluetoothServer(String id) {
        tost("Connecting to Server...");
        toLaunch = "PERDU";
        System.out.println("Starting Handler");
        new ConnectThread(id, mHandler).start();
    }

}
