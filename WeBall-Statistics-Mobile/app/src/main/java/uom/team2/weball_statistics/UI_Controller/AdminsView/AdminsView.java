package uom.team2.weball_statistics.UI_Controller.AdminsView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Stack;

import uom.team2.weball_statistics.Model.Match;
import uom.team2.weball_statistics.Model.Player;
import uom.team2.weball_statistics.Model.Team;

import java.io.IOException;

import uom.team2.weball_statistics.configuration.Config;
import uom.team2.weball_statistics.Model.Statistics.DBDataRecovery;
import uom.team2.weball_statistics.Model.Statistics.Stats;

import uom.team2.weball_statistics.R;
import uom.team2.weball_statistics.databinding.FragmentAdminsViewBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminsView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminsView extends Fragment {
    private FragmentAdminsViewBinding binding;
    private Chronometer chronometer;
    private boolean running=false;
    private Button start_end_button;
    private long pauseOffset;
    private boolean started=false;
    private boolean teamSelected =false;
    private int playerChecked=1;

    private Stack<Team> undoTeamStack=new Stack<Team>();
    private Stack<Player> undoPlayerStack=new Stack<Player>();
    private Stack<Integer>  undoButtonStack =new Stack<Integer>();
    private Match match;
    private Team landLord;
    private Team guest;
    private ArrayList<Player> keyPlayersLandlord=new ArrayList<Player>();
    private ArrayList<Player> subPlayersLandlord=new ArrayList<Player>();
    private ArrayList<Player> keyPlayersGuest=new ArrayList<Player>();
    private ArrayList<Player> subPlayersGuest=new ArrayList<Player>();




    private TextView reboundBtn;
    private TextView assistBtn;
    private TextView stealBtn;
    private TextView blockBtn;
    private TextView foulBtn;
    private TextView turnoverBtn;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminsView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminsViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminsView newInstance(String param1, String param2) {
        AdminsView fragment = new AdminsView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);



        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAdminsViewBinding.inflate(inflater,container,false);

        return binding.getRoot();

    }





    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);







//Banner Buttons -When the first team is selected -> variable "teamSelected"=false. Else, true.
    // Banner1
        binding.team1Banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamSelected=false;

                //put background color to the banner so the admin knows which team is selected
                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius( 75 );
                shape.setColor(getResources().getColor(R.color.alt_grey));
                view.setBackground(shape);

                //remove the background color from the other banner
                binding.team2Banner.setBackgroundColor(0x00000000);


                //Load data for this team
                landLord=match.getTeamLandlord();

                //Load the data of the first team players.

            }
        });

    // Banner2
        binding.team2Banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                teamSelected=true;
                //put background color to the banner so the admin knows which team is selected
                GradientDrawable shape = new GradientDrawable();
                shape.setCornerRadius(75);
                shape.setColor(getResources().getColor(R.color.alt_grey));
                view.setBackground(shape);

                //remove the background color from the other banner
                binding.team1Banner.setBackgroundColor(0x00000000);

                //Load data for this team
                guest=match.getGuest();
                //Load the data of the second team players.

            }
        });

//Start Button
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!started) {
                   binding.clock.setBase(SystemClock.elapsedRealtime());
                   binding.clock.start();
                    running = true;
                    started=true;
                    binding.startButton.setText("End");
                    binding.pauseButton.setEnabled(true);

                }
                else{
                    binding.clock.stop();
                    binding.startButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
                    binding.startButton.setText("Start/End");
                    binding.startButton.setEnabled(false);
                    binding.pauseButton.setEnabled(false);
                    binding.pauseButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
                    binding.pauseButton.setText("Pause/Continue");
                }
            }

        });

//Pause Button
        binding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    binding.clock.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    binding.clock.start();
                    binding.pauseButton.setText("Pause");
                    running = true;

                } else {
                    binding.pauseButton.setText("Continue");
                    binding.clock.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - binding.clock.getBase();
                    running = false;
                }
            }
        });

//Undo Button
        binding.undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//Freethrow Button
        binding.freethrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupViewOnePoint ppv=new popupViewOnePoint(getActivity(),1);
                ppv.show();

                //for undo button
                undoButtonStack.push(0);
                if(!teamSelected){
                    undoTeamStack.push(landLord);
                }else{
                    undoTeamStack.push(guest);
                }



            }

        });

//Two Points Button
        binding.twoPointerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupViewTwoPoints ppv=new popupViewTwoPoints(getActivity(),2);
                ppv.show();
            }
        });

//Three Points Button
        binding.threePointerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupViewThreePoints ppv=new popupViewThreePoints(getActivity(),3);
                ppv.show();
            }
        });





//Player Buttons-
        binding.player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius( 75 );
                shape.setColor(getResources().getColor(R.color.alt_grey));
                view.setBackground(shape);

                if(!(playerChecked==1)){
                    deleteThePreviousBackground();
                    playerChecked=1;
                }

            }
        });
        binding.player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius( 75 );
                shape.setColor(getResources().getColor(R.color.alt_grey));
                view.setBackground(shape);

                if(!(playerChecked==2)){
                    deleteThePreviousBackground();
                    playerChecked=2;
                }

            }
        });
        binding.player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius( 75 );
                shape.setColor(getResources().getColor(R.color.alt_grey));
                view.setBackground(shape);

                if(!(playerChecked==3)){
                    deleteThePreviousBackground();
                    playerChecked=3;
                }

            }
        });
        binding.player4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius( 75 );
                shape.setColor(getResources().getColor(R.color.alt_grey));
                view.setBackground(shape);

                if(!(playerChecked==4)){
                    deleteThePreviousBackground();
                    playerChecked=4;
                }

            }
        });
        binding.player5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius( 75 );
                shape.setColor(getResources().getColor(R.color.alt_grey));
                view.setBackground(shape);

                if(!(playerChecked==5)){
                    deleteThePreviousBackground();
                    playerChecked=5;
                }


            }
        });

//Substitution Button
        binding.substitutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //steile ton paikti

                //emfanise to popup
                SubstitutionPopupView ppv=new SubstitutionPopupView(getActivity(),"panais");
                ppv.show();
            }
        });


        reboundBtn = binding.reboundButton;
       assistBtn = binding.assistButton;
       stealBtn = binding.stealButton;
       blockBtn =  binding.blockButton;
       foulBtn = binding.foulButton;
       turnoverBtn = binding.turnoverButton;

        DBDataRecovery dataRecovery = new DBDataRecovery();
        try {
            Stats playerStats = dataRecovery.readData(Config.API_PLAYER_STATISTICS_COMPLETED, "8");
            reboundBtn.setOnClickListener(e -> updateRebound(playerStats,dataRecovery));
            assistBtn.setOnClickListener(e -> updateAssist(playerStats,dataRecovery));
            stealBtn.setOnClickListener(e -> updateSteal(playerStats,dataRecovery));
            blockBtn.setOnClickListener(e -> updateBlock(playerStats,dataRecovery));
            foulBtn.setOnClickListener(e -> updateFoul(playerStats,dataRecovery));
            turnoverBtn.setOnClickListener(e -> updateTurnover(playerStats,dataRecovery));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateAssist(Stats playerStats,DBDataRecovery dbDataRecovery){
        playerStats.setTotalAssists();
       // PlayerLiveStatistics p = new PlayerLiveStatistics(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
       // p.setAssist(playerStats.getTotalAssists());
        try {
            dbDataRecovery.updateDataDB(Config.API_PLAYER_STATISTICS_COMPLETED, playerStats);
           // DAOLivePlayerStatistics.getInstace().update(p);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateRebound(Stats playerStats,DBDataRecovery dbDataRecovery){
        playerStats.setTotalRebounds();
        try {
            dbDataRecovery.updateDataDB(Config.API_PLAYER_STATISTICS_COMPLETED, playerStats);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateSteal(Stats playerStats,DBDataRecovery dbDataRecovery){
        playerStats.setTotalSteels();
        try {
            dbDataRecovery.updateDataDB(Config.API_PLAYER_STATISTICS_COMPLETED, playerStats);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateBlock(Stats playerStats,DBDataRecovery dataRecovery){
        playerStats.setTotalBlock();
        try {
            dataRecovery.updateDataDB(Config.API_PLAYER_STATISTICS_COMPLETED, playerStats);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateFoul(Stats playerStats,DBDataRecovery dbDataRecovery){
        playerStats.setTotalFouls();
        try {
            dbDataRecovery.updateDataDB(Config.API_PLAYER_STATISTICS_COMPLETED, playerStats);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateTurnover(Stats playerStats,DBDataRecovery dbDataRecovery){
        playerStats.setTotalTurnovers();
        try {
            dbDataRecovery.updateDataDB(Config.API_PLAYER_STATISTICS_COMPLETED, playerStats);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



//you call this function when you want to change player
    public void deleteThePreviousBackground(){
        if(playerChecked==1){
            binding.player1.setBackgroundColor(0x00000000);
        }
        else if(playerChecked==2){
            binding.player2.setBackgroundColor(0x00000000);
        }
        else if(playerChecked==3){
            binding.player3.setBackgroundColor(0x00000000);
        }
        else if(playerChecked==4){
            binding.player4.setBackgroundColor(0x00000000);
        }
        else if (playerChecked==5){
            binding.player5.setBackgroundColor(0x00000000);
        }
    }



}