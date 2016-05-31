package com.inc.pwal.iac;

import android.annotation.SuppressLint;
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
import java.util.Date;
import java.util.Set;
import java.util.UUID;



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

    private boolean UI_INIT = false;
    private boolean EDT_SYNC = false;

    private Intent intent;

    private EDT edt = new EDT();
    //-----------------------------------------------------------------------BLUETOOTH--------------
    private boolean bTEnabled = false;
    private boolean bTEnabling = false;
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
    @SuppressWarnings("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SOCKET_CONNECTED: {
                    mBluetoothConnection = (ConnectionThread) msg.obj;
                    mBluetoothConnection.write(toLaunch);
                    System.out.println("first ToLaunch Launch'd");
                    bTEnabled = true;
                    sendMsg(createBTMsg());
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

        for(Day d : week)
            d.calculateAlarmHour();

        buttonMonday = (Button) findViewById(R.id.buttonMondayIU);
        if(buttonMonday == null)
            System.out.println("ABANDON SHIP");

        buttonTuesday = (Button) findViewById(R.id.buttonTuesdayIU);
        if(buttonTuesday == null)
            System.out.println("ABANDON SHIP");

        buttonWednesday = (Button) findViewById(R.id.buttonWednesdayIU);
        if(buttonWednesday == null)
            System.out.println("ABANDON SHIP");

        buttonThursday = (Button) findViewById(R.id.buttonThursdayIU);
        if(buttonThursday == null)
            System.out.println("ABANDON SHIP");

        buttonFriday = (Button) findViewById(R.id.buttonFridayIU);
        if(buttonFriday == null)
            System.out.println("ABANDON SHIP");

        buttonSaturday = (Button) findViewById(R.id.buttonSaturdayIU);
        if(buttonSaturday == null)
            System.out.println("ABANDON SHIP");

        buttonSunday = (Button) findViewById(R.id.buttonSundayIU);
        if(buttonSunday == null)
            System.out.println("ABANDON SHIP");

        buttonMonday.setOnClickListener(this);
        buttonTuesday.setOnClickListener(this);
        buttonWednesday.setOnClickListener(this);
        buttonThursday.setOnClickListener(this);
        buttonFriday.setOnClickListener(this);
        buttonSaturday.setOnClickListener(this);
        buttonSunday.setOnClickListener(this);

        updateInterface();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UI_INIT = true;



    }

    private void initDays ()
    {
        launchEDT();//Download bypass'd
        System.out.println("Initiating dayz");

        week = new ArrayList<>();

        String[] semaine = {"Dimanche","Lundi","Mardi","Mercredi","Jeudi", "Vendredi", "Samedi"};
        int dim = 0; int lundi = 1;int mardi = 2; int merc = 3; int jeudi = 4; int vend = 5; int sam = 6;

        int jour = new Date().getDay();
        for(int i = 0; i<7;i++)
        {
            week.add(new Day(semaine[jour],null, null));
            jour = ++jour % 7;
        }
        System.out.println("Week fill'd");

        tost("Waiting EDT to process");
        while(!EDT_SYNC);

        ArrayList<SchoolClass> ls = edt.getSchedule();
        System.out.println("Taille de l'EDT récup' : " + ls.size());

        for(int j = 0; j < ls.size();j++)
        {
            boolean found = false;
            SchoolClass s = ls.get(j);
            for(int i = 0; i < week.size() && !found ; i++ )
            {

                //System.out.println(s.getDate() + " " + week.get(i).getName());

                if(semaine[s.getDate().getDay()].equals(week.get(i).getName()))
                {
                    week.get(i).setClassHour(new Hour(s.getDate().getHours(),s.getDate().getMinutes()));
                    found = true;
                }
            }
        }
        System.out.println("EDT merg'd");
        jour = new Date().getDay();

        //System.out.println("week : " + week.size() + ". jour today : " + jour);
        sunday    = week.get(modNeg(dim - jour));
        monday    = week.get(modNeg(lundi - jour));
        tuesday   = week.get(modNeg(mardi - jour));
        wednesday = week.get(modNeg(merc - jour));
        thursday  = week.get(modNeg(jeudi - jour));
        friday    = week.get(modNeg(vend - jour));
        saturday  = week.get(modNeg(sam - jour));
        /*
        System.out.println("Debug Link Buttons : " + sunday.getName());
        System.out.println("Debug Link Buttons : " + monday.getName());
        System.out.println("Debug Link Buttons : " + tuesday.getName());
        System.out.println("Debug Link Buttons : " + wednesday.getName());
        System.out.println("Debug Link Buttons : " + thursday.getName());
        System.out.println("Debug Link Buttons : " + friday.getName());
        System.out.println("Debug Link Buttons : " + saturday.getName());
        */

        createInterface();

        week.get(modNeg(dim - jour)).setButton(buttonSunday);
        week.get(modNeg(lundi - jour)).setButton(buttonMonday);
        week.get(modNeg(mardi - jour)).setButton(buttonTuesday);
        week.get(modNeg(merc - jour)).setButton(buttonWednesday);
        week.get(modNeg(jeudi - jour)).setButton(buttonThursday);
        week.get(modNeg(vend - jour)).setButton(buttonFriday);
        week.get(modNeg(sam - jour)).setButton(buttonSaturday);
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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        int view = R.layout.activity_main;

        setContentView(view);

        initDays();

        this.setDefaultRituels();


        bTEnabled = false; bTEnabling = false; UI_INIT = false;


    }

    @Override
    protected void onResume(){
        super.onResume();
        if(UI_INIT)updateInterface();

        //for (Rituel r : MainActivity.listRituels)System.out.println(r.getName());
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
                    EDT_SYNC = true;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @SuppressLint("SetTextI18n")
    protected void updateInterface() {
        for (Day d : week){
            d.calculateAlarmHour();
        }

        if(monday.getClassHour() != null)
            buttonMonday.setText(monday.getName() + " " + monday.getAlarmHour().getHours() + ":" + monday.getAlarmHour().getMinutes());
        else
            buttonMonday.setText(monday.getName() + " Pas de Reveil");


        if(tuesday.getClassHour() != null)
            buttonTuesday.setText(tuesday.getName() + " " + tuesday.getAlarmHour().getHours() + ":" + tuesday.getAlarmHour().getMinutes());
        else
            buttonTuesday.setText(tuesday.getName() + " Pas de Reveil");


        if(wednesday.getClassHour() != null)
            buttonWednesday.setText(wednesday.getName() + " " + wednesday.getAlarmHour().getHours() + ":" + wednesday.getAlarmHour().getMinutes());
        else
            buttonWednesday.setText(wednesday.getName() + " Pas de Reveil");


        if(thursday.getClassHour() != null)
            buttonThursday.setText(thursday.getName() + " " + thursday.getAlarmHour().getHours() + ":" + thursday.getAlarmHour().getMinutes());
        else
            buttonThursday.setText(thursday.getName() + " Pas de Reveil");


        if(friday.getClassHour() != null)
            buttonFriday.setText(friday.getName() + " " + friday.getAlarmHour().getHours() + ":" + friday.getAlarmHour().getMinutes());
        else
            buttonFriday.setText(friday.getName() + " Pas de Reveil");


        if(saturday.getClassHour() != null)
            buttonSaturday.setText(saturday.getName() + " " + saturday.getAlarmHour().getHours() + ":" + saturday.getAlarmHour().getMinutes());
        else
            buttonSaturday.setText(saturday.getName() + " Pas de Reveil");


        if(sunday.getClassHour() != null)
            buttonSunday.setText(sunday.getName() + " " + sunday.getAlarmHour().getHours() + ":" + sunday.getAlarmHour().getMinutes());
        else
            buttonSunday.setText(sunday.getName() + " Pas de Reveil");
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

        if (id == R.id.action_Sync)
        {
            System.out.println("Enabling BT");
            bTInitSend();
            System.out.println("Tryin' to send da bottl'o'message");
            System.out.println("Bottl' sent to da seaz");
            return true;
        }

        if (id == R.id.action_CreateRituel){
            tost("Création Rituel");
            intent = new Intent(MainActivity.this,CreateRituelsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        System.out.println("CLICK " + v);

        intent = new Intent(MainActivity.this, DaySettingsActivity.class);
        if (v == findViewById(R.id.buttonMondayIU)) {
            intent.putExtra(DAY_CLICKED, monday.getName());
        }
        else if (v == findViewById(R.id.buttonTuesdayIU)){
            intent.putExtra(DAY_CLICKED, tuesday.getName());
        }
        else if (v == findViewById(R.id.buttonWednesdayIU)){
            intent.putExtra(DAY_CLICKED, wednesday.getName());
        }
        else if (v == findViewById(R.id.buttonThursdayIU)) intent.putExtra(DAY_CLICKED, thursday.getName());
        else if (v == findViewById(R.id.buttonFridayIU)) intent.putExtra(DAY_CLICKED, friday.getName());
        else if (v == findViewById(R.id.buttonSaturdayIU)) intent.putExtra(DAY_CLICKED, saturday.getName());
        else if (v == findViewById(R.id.buttonSundayIU)) intent.putExtra(DAY_CLICKED, sunday.getName());
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

    private String createBTMsg()
    {
        long MILLISEC_A_DAY = 86400000L;
        Date today = new Date();
        long diff;
        long oldDiff = 0;
        updateInterface();
        String toSend = "s";
        for(int i =0; i < week.size(); i++)
        {
            Day d = week.get(i);
            Date tmp = new Date();
            tmp.setHours(d.getAlarmHour().getHours());
            tmp.setMinutes(d.getAlarmHour().getMinutes());
            diff = tmp.getTime() - today.getTime();
            diff += i*MILLISEC_A_DAY;
            if(diff > 0)
            {
                diff /= 1000; //On passe en seconde
                diff -= oldDiff;
                oldDiff += diff;
                toSend += diff + " 2 ";
            }
        }
        System.out.println(toSend + ";");
        return toSend + ";";
    }

    private void bTInitSend()
    {
        if(bTEnabled)
            sendMsg(createBTMsg());
        else
            initBluetooth();
    }

    private void initBluetooth()
    {
        bTEnabling = true;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Log.i(TAG, "Bluetooth not supported");
            finish();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            System.out.println("Enfoiré");
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        }else{
            selectServer();
        }

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

        System.out.println("Creating da bloody Intent");
        Intent showDevicesIntent = new Intent(this, ShowDevices.class);
        showDevicesIntent.putStringArrayListExtra("devices", pairedDeviceStrings);
        startActivityForResult(showDevicesIntent, SELECT_SERVER);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {System.out.println("truc");selectServer();}
        else if (requestCode == SELECT_SERVER && resultCode == RESULT_OK)
        {
            BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            connectToBluetoothServer(device.getAddress());
            System.out.println("Serverrr hav' just been select'd");
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
        if(!bTEnabled && !bTEnabling)return;
        while(!bTEnabled)System.out.print(".");
        byte[] space = "        ".getBytes();
        byte[] tmp = msg.getBytes();
        int c = 0;
        while(tmp.length > 8*(c+1))
        {
            toLaunch = new byte[8];
            //System.out.println("msg : " + msg.length() + " toLaunch : " + toLaunch.length);
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
