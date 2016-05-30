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
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static com.inc.pwal.iac.R.id.buttonMonday;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "IAC";

    private Button buttonMonday;
    private Button buttonTuesday;
    private Button buttonWednesday;
    private Button buttonThursday;
    private Button buttonFriday;
    private Button buttonSaturday;
    private Button buttonSunday;

    public static Day monday;
    public static Day tuesday;
    public static Day wednesday;
    public static Day thursday;
    public static Day friday;
    public static Day saturday;
    public static Day sunday;
    private final String DAY_CLICKED = null;
    public static ArrayList<Day> week;
    public static ArrayList<Rituel> listRituels;

    private EDT edt = new EDT();
    //-----------------------------------------------------------------------BLUETOOTH--------------
    private boolean bTEnabled = false;
    private boolean busy = false;
    private byte[] toLaunch;

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
                    mBluetoothConnection.write(toLaunch);
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
    //-------------------------------------------------------------------FIN-BLUETOOTH--------------



    private void createInterface(){
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

    private void setDefaultDay ()
    {
        week = new ArrayList<>();

        String[] semaine = { "Dimanche","Lundi","Mardi","Mercredi","Jeudi", "Vendredi", "Samedi"};

        int jour = new Date().getDay();
        for(int i = 0; i<7;i++)
        {
            week.add(new Day(semaine[jour],new Hour(10+i,0), null));
            jour = ++jour % 7;
        }
        jour = new Date().getDay();
        int dim = 0; int lundi = 1;int mardi = 2; int merc = 3; int jeudi = 4; int vend = 5; int sam = 6;

        System.out.println("week : " + week.size() + ". jour today : " + jour);

        week.get(modNeg(dim - jour)).setButton(buttonSunday);
        sunday    = week.get(modNeg(dim - jour));
        week.get(modNeg(lundi - jour)).setButton(buttonMonday);
        monday    = week.get(modNeg(lundi - jour));
        week.get(modNeg(mardi - jour)).setButton(buttonTuesday);
        tuesday   = week.get(modNeg(mardi - jour));
        week.get(modNeg(merc - jour)).setButton(buttonWednesday);
        wednesday = week.get(modNeg(merc - jour));
        week.get(modNeg(jeudi - jour)).setButton(buttonThursday);
        thursday  = week.get(modNeg(jeudi - jour));
        week.get(modNeg(vend - jour)).setButton(buttonFriday);
        friday    = week.get(modNeg(vend - jour));
        week.get(modNeg(sam - jour)).setButton(buttonSaturday);
        saturday  = week.get(modNeg(sam - jour));
    }

            private int modNeg(int leModulo)
            {
                if(leModulo < 0)
                    return 7 + leModulo;
                return leModulo;

        }

    private void setDefaultRituels(){
        listRituels = new ArrayList<>();
        Rituel douche = new Rituel(1,"douche",15,0,"default");
        Rituel trajet = new Rituel(2, "trajet", 25, 0, "default");
        Rituel manger = new Rituel(3, "manger", 30, 0, "default");
        Rituel prepAffaires = new Rituel(4, "préparer affaires", 20, 0, "default");
        Rituel lavageDents = new Rituel(5, "lavage dents", 5, 0, "default");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        this.setDefaultDay();
        this.setDefaultRituels();

        super.onCreate(savedInstanceState);

        bTEnabled = false;
        //initBluetooth();

        int view = R.layout.activity_main;
        setContentView(view);

        this.createInterface();

        //launchEDT();//Download bypass'd


    }

    @Override
    protected void onResume(){
        super.onResume();
        updateInterface();
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

    protected void updateInterface() {
        for (Day d : week){
            d.calculateAlarmHour();
        }

        buttonMonday = (Button) findViewById(R.id.buttonMonday);
        buttonMonday.setText(monday.getName() + " " + monday.getAlarmHour().getHours() + ":" + monday.getAlarmHour().getMinutes());

        buttonTuesday = (Button) findViewById(R.id.buttonTuesday);
        buttonTuesday.setText(tuesday.getName() + " " + tuesday.getAlarmHour().getHours() + ":" + tuesday.getAlarmHour().getMinutes());

        buttonWednesday = (Button) findViewById(R.id.buttonWednesday);
        buttonWednesday.setText(wednesday.getName() + " " + wednesday.getAlarmHour().getHours() + ":" + wednesday.getAlarmHour().getMinutes());

        buttonThursday = (Button) findViewById(R.id.buttonThursday);
        buttonThursday.setText(thursday.getName() + " " + thursday.getAlarmHour().getHours() + ":" + thursday.getAlarmHour().getMinutes());

        buttonFriday = (Button) findViewById(R.id.buttonFriday);
        buttonFriday.setText(friday.getName() + " " + friday.getAlarmHour().getHours() + ":" + friday.getAlarmHour().getMinutes());

        buttonSaturday = (Button) findViewById(R.id.buttonSaturday);
        buttonSaturday.setText(saturday.getName() + " " + saturday.getAlarmHour().getHours() + ":" + saturday.getAlarmHour().getMinutes());

        buttonSunday = (Button) findViewById(R.id.buttonSunday);
        buttonSunday.setText(sunday.getName() + " " + sunday.getAlarmHour().getHours() + ":" + sunday.getAlarmHour().getMinutes());
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

        if (id == R.id.action_Sync) {
            tost("Perdu");
            //sendMsg(createBTMsg());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DaySettingsActivity.class);
        if (v == findViewById(R.id.buttonMonday)) {
            //sendMsg("Champignons au fromage de porc");
            intent.putExtra(DAY_CLICKED, monday.getName());
        }
        else if (v == findViewById(R.id.buttonTuesday)){
            //sendMsg("APWAL LES POMMES");
            intent.putExtra(DAY_CLICKED, tuesday.getName());
        }
        else if (v == findViewById(R.id.buttonWednesday)){
            sendMsg("5800 5700 9640 654123 158;");
            intent.putExtra(DAY_CLICKED, wednesday.getName());
        }
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

    /*private String createBTMsg()
    {
        Hour pwal = monday.getAlarmHour();
    }*/

    private void initBluetooth()
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
        bTEnabled = true;
        selectServer();
    }

    private void selectServer() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList<String> pairedDeviceStrings = new ArrayList<>();
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
        toLaunch = "Coucou".getBytes();
        System.out.println("Starting Handler");
        new ConnectThread(id, mHandler).start();
    }

    protected void sendMsg(String msg)
    {
        if(!bTEnabled)return;
        byte[] space = "        ".getBytes();
        byte[] tmp = msg.getBytes();
        int c = 0;
        while(tmp.length > 8*(c+1))
        {
            toLaunch = new byte[8];
            System.out.println("msg : " + msg.length() + " toLaunch : " + toLaunch.length);
            for (int i = 0; i < 8; i++) {
                toLaunch[i] = tmp[c * 8 + i];
            }
            c++;
            while (busy) ;
            new Runnable() {
                @Override
                public void run() {
                    busy = true;
                    mBluetoothConnection.write(toLaunch);
                    Log.d(TAG, "Envoi effectué" + toLaunch);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    busy=false;
                }
            }.run();
        }
        int lastToLaunch = msg.length() - (8*c);
        toLaunch = new byte[8];
        for(int i = 0; i <lastToLaunch; i++)
            toLaunch[i] = tmp[c*8 + i];
        for(int i = lastToLaunch; i<8;i++)
            toLaunch[i] = space[i];

        while(busy);
        new Runnable() {
            @Override
            public void run() {
                busy = true;
                mBluetoothConnection.write(toLaunch);
                Log.d(TAG, "Envoi effectué" + toLaunch);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                busy=false;
            }
        }.run();

    }

}
