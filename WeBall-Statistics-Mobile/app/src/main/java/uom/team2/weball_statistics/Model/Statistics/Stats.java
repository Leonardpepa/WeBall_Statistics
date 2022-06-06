package uom.team2.weball_statistics.Model.Statistics;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import uom.team2.weball_statistics.Model.Config;

public abstract class Stats implements editFieldsFromDB {
    
    protected String fieldGoalsPercentage;
    protected String freeThrowsPercentage;
    protected int successful_effort;
    protected int total_effort;
    protected int successful_freethrow;
    protected int total_freethrow;
    protected String twoPointsPercentage;
    protected int successful_twopointer;
    protected int total_twopointer;
    protected String threePointsPercentage;
    protected int successful_threepointer;
    protected int total_threepointer;
    protected int steal;
    protected int rebound;
    protected int assist;
    protected int block;
    protected int foul;
    protected int turnover;

    private void formatStats(){
        calculateFieldGoalPercentageToString();
        calculateFreeThrowPercentageToString();
        calculateTwoPointPercentageToString();
        calculateThreePointPercentageToString();
    }


    /* This private method calculates the field goal percentage including two and three points attempts .
       Also returns this percentage in format of string.*/
    private void calculateFieldGoalPercentageToString(){
         fieldGoalsPercentage = Integer.toString(successful_effort).concat("/").concat(Integer.toString(total_effort));
    }

    private void calculateTwoPointPercentageToString(){
         twoPointsPercentage = Integer.toString(successful_twopointer).concat("/").concat(Integer.toString(total_twopointer));
    }

    private void calculateFreeThrowPercentageToString(){
         freeThrowsPercentage = Integer.toString(successful_freethrow).concat("/").concat(Integer.toString(total_freethrow));
    }

    public void calculateThreePointPercentageToString(){
         threePointsPercentage = Integer.toString(successful_threepointer).concat("/").concat(Integer.toString(total_threepointer));
    }

    protected double calculateFreeThrowsPercentageToNumber(){
       return total_freethrow > 0 ? (successful_freethrow * 100)/total_freethrow : 0;
    }

    public double calculateFieldGoalPercentageToNumber(){
        return total_effort > 0 ? (successful_effort * 100)/total_effort : 0;
    }

    public double calculateTwoPointsPercentageToNumber(){
        return total_twopointer > 0 ? (successful_twopointer * 100)/total_twopointer : 0;
    }

    public double calculateThreePointsPercentageToNumber(){
        return total_threepointer > 0 ? (successful_threepointer * 100)/total_threepointer : 0;
    }

    public int calculateTotalPoints(){
        return successful_twopointer * Config.COEFFICIENT_TWO_POINT + successful_freethrow * Config.COEFFICIENT_ONE_POINT + successful_threepointer * Config.COEFFICIENT_THREE_POINT;
    }

    public abstract double calculatePointsPercentage();

    public String getFieldGoalPercentage(){
        return fieldGoalsPercentage;
    }

    public String getFreeThrowPercentage(){
        return freeThrowsPercentage;
    }

    public int getSuccessfulFreeThrow(){
        return successful_freethrow;
    }

    public int getTotalFreeThrow(){
        return successful_freethrow;
    }

    public int getSuccessfulEffort(){
        return successful_effort;
    }

    public int getTotalEffort(){
        return total_effort;
    }

    public String getTwoPointsPercentage(){
        return twoPointsPercentage;
    }

    public int getTotalTwoPointer(){
        return total_twopointer;
    }

    public int getSuccessFulTwoPointer(){
        return successful_twopointer;
    }

    public String getThreePointsPercentage(){
        return threePointsPercentage;
    }

    public int getSuccessFulThreePointer(){
        return successful_threepointer;
    }

    public int getTotalThreePointer(){
        return total_threepointer;
    }

    public int getTotalSteels(){
       return steal;
    }

    public int getTotalRebounds(){
        return rebound;
    }

    public int getTotalAssists(){
       return assist;
    }

    public int getTotalBlocks(){
        return block;
    }

    public int getTotalFouls(){
      return  foul;
    }

    public int getTotalTurnovers(){
        return turnover;
    }

    protected void setTotalEffort(){total_effort++; }

    protected void setSuccessfulEffort(){successful_effort++; }

    protected void setTotalFreeThrow(){total_freethrow++; }

    protected void setSuccessfulFreeThrow(){successful_freethrow++; }

    protected void setTotalTwoPointer(){total_twopointer++; }

    protected void setSuccessfulTwoPointer(){successful_twopointer++; }

    protected void setTotalThreePointer(){total_threepointer++; }

    protected void setSuccessfulThreePointer(){successful_threepointer++; }

    protected void setTotalSteels(){steal++; }

    protected void setTotalRebounds(){ rebound++;}

    protected void setTotalAssists(){ assist++;}

    public void setTotalBlock(){ block++; }

    public void setTotalFouls(){ foul++;}

    protected void setTotalTurnovers(){ turnover++;}



    @Override
    public void editJON(String data) {

   System.out.println(data);
        try {
            JSONObject json = new JSONObject(data);
            Iterator<String> keys = json.keys();
            HashMap<String , String> hashMapData = new HashMap<String , String>();

            while(keys.hasNext()) {
                String key = keys.next();
                String dataFromKey = json.get(key).toString();
                hashMapData.put(key, dataFromKey);

                if(keys.equals("turnover")) break;

            }

            successful_effort = Integer.parseInt(hashMapData.get("successful_effort"));
            total_effort = Integer.parseInt(hashMapData.get("total_effort"));
            successful_freethrow = Integer.parseInt(hashMapData.get("successful_freethrow"));
            total_freethrow = Integer.parseInt(hashMapData.get("total_freethrow"));
            successful_twopointer = Integer.parseInt(hashMapData.get("successful_twopointer"));
            total_twopointer = Integer.parseInt(hashMapData.get("total_twopointer"));
            successful_threepointer = Integer.parseInt(hashMapData.get("successful_threepointer"));
            total_threepointer = Integer.parseInt(hashMapData.get("total_threepointer"));
            steal = Integer.parseInt(hashMapData.get("steal"));
            rebound = Integer.parseInt(hashMapData.get("rebound"));
            assist = Integer.parseInt(hashMapData.get("assist"));
            block = Integer.parseInt(hashMapData.get("block"));
            foul = Integer.parseInt(hashMapData.get("foul"));
            turnover = Integer.parseInt(hashMapData.get("turnover"));

            formatStats();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}