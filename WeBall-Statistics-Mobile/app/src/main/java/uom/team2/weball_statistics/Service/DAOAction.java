package uom.team2.weball_statistics.Service;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

import uom.team2.weball_statistics.Model.Actions.*;
import uom.team2.weball_statistics.Model.*;
import uom.team2.weball_statistics.UI_Controller.LiveController.Progress.LiveGameProgress;
import uom.team2.weball_statistics.UI_Controller.LiveController.Progress.LiveProgressUIController;

public class DAOAction implements DAOCRUDService <Action> {

    private DatabaseReference databaseReference;
    private LiveProgressUIController liveProgressUIController = LiveProgressUIController.getInstance();
    public static DAOAction instance = new DAOAction();

    //Implement singleton pattern
    private DAOAction() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Action.class.getSimpleName());
    }

    public static DAOAction getInstance(){
        if(instance == null){
            instance = new DAOAction();
        }
        return instance;
    }

    //Method to retrieve real time actions for a match.
    //Call this function when you move to action progress fragment for a specific match
    public void getRealTimeData(Match matchData, LiveGameProgress liveGameProgressFragment) {
        //Get data snapshot
<<<<<<< HEAD
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Action").child("Actions for match with id: " + matchData.getId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
=======
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Action").child("Actions for match with id: " + matchData.getId()).child("Actions");
        ref.addValueEventListener(new ValueEventListener() {
>>>>>>> main
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        liveGameProgressFragment.getBinding().actionsLayoutContainer.removeAllViews();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
<<<<<<< HEAD

                            Action action = data.getValue(Action.class);

=======
                            Action action = data.getValue(Action.class);
                            System.out.println(action.getActionDesc());
>>>>>>> main
                            if (action.getBelongsTo() == BelongsTo.HOME) {
                                liveProgressUIController.addActionForHomeTeam(liveGameProgressFragment, action);
                            } else if (action.getBelongsTo() == BelongsTo.GUEST) {
                                liveProgressUIController.addActionForGuestTeam(liveGameProgressFragment, action);
                            } else if (action.getBelongsTo() == BelongsTo.GENERAL){
                                liveProgressUIController.addActionForGeneral(liveGameProgressFragment, action);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                        System.out.println("onCancelled: Error: " + databaseError.getMessage());
                    }
                });
    }

    @Override
    public Task<Void> insert(Action data) {
        return null;
    }

    //Will called every time an action is added for a match (first insert and then get)
    public Task<Void> insert(Action actionData, Match matchData) {

<<<<<<< HEAD
        return databaseReference.child("Actions for match with id: " + matchData.getId()).child(actionData.getId() + "").setValue(actionData);
=======
        FirebaseDatabase.getInstance().getReference().child("Action").child("Actions for match with id: " + matchData.getId()).child("Actions").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int num = Math.toIntExact(dataSnapshot.getChildrenCount()); //The existing number of actions for a match
                matchData.setActionsCount(num);
                System.out.println(num);

                databaseReference.child("Actions for match with id: " + matchData.getId()).child("Actions").child(matchData.getActionsCount() + "").setValue(actionData);
            }
        });

        return null;
>>>>>>> main
    }

    //Method that count the existing action for a Match
    //We need this method to give dynamic id to match upcoming actions. Every id should be unique for each action of one match
    public void countMatchActions(Match matchObj) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Action").child("Actions for match with id: " + matchObj.getId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int num = Math.toIntExact(dataSnapshot.getChildrenCount()); //The existing number of actions for a match
                matchObj.setActionsCount(num);
                System.out.println(num);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                int num = 0; //means that child not created yet, so there no actions yet
                matchObj.setActionsCount(num);
                System.out.println(num + " from error");
            }
        });
    }

    @Override
    public Task<Void> update(HashMap<String, Action> data) {
        return null;
    }

    @Override
    public Task<Void> delete(Action data) {
        return null;
    }

    @Override
    public Action get() {
        return null;
    }

    @Override
    public Action get(Action data) {
        return null;
    }

    @Override
    public void update(Action data) {

    }
}
