package uom.team2.weball_statistics.Service;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import uom.team2.weball_statistics.Model.PlayerLiveStatistics;
import uom.team2.weball_statistics.UI_Controller.LiveController.Statistics.LiveGameStatistics;
import uom.team2.weball_statistics.UI_Controller.LiveController.Statistics.LiveStatisticsEnum;

/*
 * @author Leonard Pepa ics20033
 */
public class DAOLivePlayerStatistics implements DAOCRUDService<PlayerLiveStatistics>{

    private DatabaseReference databaseReference;
    public static DAOLivePlayerStatistics instance;

    private DAOLivePlayerStatistics() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(PlayerLiveStatistics.class.getSimpleName());
    }

    public static DAOLivePlayerStatistics getInstance(){
        if(instance == null){
            instance = new DAOLivePlayerStatistics();
        }
        return instance;
    }

    public void setDataChangeListener(LiveGameStatistics fragment, int matchId, int playerId) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.

                PlayerLiveStatistics playerLiveStatistics = snapshot.child("match_id: " + matchId).child("player_id: " + playerId).getValue(PlayerLiveStatistics.class);
                HashMap<String, View> mapof = fragment.getMapOfStatistics();

                for (LiveStatisticsEnum statistic : LiveStatisticsEnum.values()) {

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    @Override
    public Task<Void> insert(PlayerLiveStatistics data) {
        return databaseReference.child("match_id: " + data.getMatch_id()).child("player_id: " + data.getPlayer_id()).setValue(data);
    }

    @Override
    public Task<Void> update(HashMap<String, PlayerLiveStatistics> data) {
        return null;
    }

    @Override
    public Task<Void> delete(PlayerLiveStatistics data) {
        return null;
    }

    @Override
    public PlayerLiveStatistics get() {
        return null;
    }

    @Override
    public PlayerLiveStatistics get(PlayerLiveStatistics data) {
        return null;
    }

    @Override
    public void update(PlayerLiveStatistics data) {
        HashMap<String, Object> h = (HashMap<String, Object>) data.toMap();
        databaseReference.child("match_id: " + data.getMatch_id()).child("player_id: " + data.getPlayer_id()).updateChildren(h);
    }
}
